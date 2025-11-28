package services;

import repositories.TransactionRepository;
import repositories.UserRepository;
import structure.Asset;
import structure.Transaction;
import structure.User;

import java.util.List;


public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CurrencyService currencyService;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              CurrencyService currencyService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.currencyService = currencyService;
    }

    /**
     * Køb et asset til en bruger:
     * - Tjekker om brugeren har nok penge (i DKK)
     * - Opdaterer kontantbeholdning
     * - Opretter og gemmer en Transaction i CSV
     */
    public void buyAsset(User user, Asset asset, int quantity, String date) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        double pricePerUnit = asset.getValue();
        String currency = asset.getCurrency();

        // total pris i assetets valuta
        double totalPrice = pricePerUnit * quantity;

        // konverter til DKK
        double totalPriceDKK = currencyService.convertToDKK(totalPrice, currency);

        // tjek om brugeren har nok penge
        if (user.getCashBalance() < totalPriceDKK) {
            throw new IllegalArgumentException("Insufficient funds for this purchase.");
        }

        // opdater kontantbeholdning
        double newBalance = user.getCashBalance() - totalPriceDKK;
        user.setCashBalance(newBalance);
        userRepository.updateBalance(user.getId(), newBalance);

        // opret transaktion og gem
        Transaction t = createTransaction(user.getId(), date, asset.getTicker(),
                pricePerUnit, currency, "buy", quantity);

        saveTransaction(t);
    }

    /**
     * Sælg et asset:
     * - Tjekker om brugeren har nok af det pågældende asset (kvantitet)
     * - Opdaterer kontantbeholdning
     * - Opretter og gemmer en Transaction i CSV
     */
    public void sellAsset(User user, Asset asset, int quantity, String date) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        // tjek hvor mange brugeren har af dette asset baseret på tidligere handler
        int currentHolding = getHoldingForUserAndTicker(user.getId(), asset.getTicker());
        if (currentHolding < quantity) {
            throw new IllegalArgumentException("Insufficient holdings to sell.");
        }

        double pricePerUnit = asset.getValue();
        String currency = asset.getCurrency();
        double totalPrice = pricePerUnit * quantity;
        double totalPriceDKK = currencyService.convertToDKK(totalPrice, currency);

        // opdater kontantbeholdning
        double newBalance = user.getCashBalance() + totalPriceDKK;
        user.setCashBalance(newBalance);
        userRepository.updateBalance(user.getId(), newBalance);

        // opret transaktion og gem
        Transaction t = createTransaction(user.getId(), date, asset.getTicker(),
                pricePerUnit, currency, "sell", quantity);

        saveTransaction(t);
    }

    /**
     * Hjælper med at udregne hvor mange aktier/units brugeren har
     * af et givet ticker baseret på transactions.csv
     */
    private int getHoldingForUserAndTicker(int userId, String ticker) {
        List<Transaction> all = transactionRepository.loadTransactions();
        int holding = 0;

        for (Transaction t : all) {
            if (t.getUserId() == userId && t.getTicker().equalsIgnoreCase(ticker)) {
                if ("buy".equalsIgnoreCase(t.getOrderType())) {
                    holding += t.getQuantity();
                } else if ("sell".equalsIgnoreCase(t.getOrderType())) {
                    holding -= t.getQuantity();
                }
            }
        }

        return holding;
    }

    /**
     * Opretter Transaction-objekt med et automatisk ID.
     */
    private Transaction createTransaction(int userId,
                                          String date,
                                          String ticker,
                                          double price,
                                          String currency,
                                          String orderType,
                                          int quantity) {

        int nextId = getNextTransactionId();

        return new Transaction(
                nextId,
                userId,
                date,
                ticker,
                price,
                currency,
                orderType,
                quantity
        );
    }

    /**
     * Finder næste ledige transaction ID ud fra eksisterende CSV.
     */
    private int getNextTransactionId() {
        List<Transaction> all = transactionRepository.loadTransactions();
        int maxId = 0;
        for (Transaction t : all) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Tilføjer en transaction til listen og gemmer hele filen igen.
     */
    private void saveTransaction(Transaction t) {
        List<Transaction> all = transactionRepository.loadTransactions();
        all.add(t);
        transactionRepository.saveTransactions(all);
    }
}
