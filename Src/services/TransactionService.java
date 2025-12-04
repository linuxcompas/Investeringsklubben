package services;

import repositories.*;
import structure.*;
import utility.DateTime;

import java.util.*;


public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CurrencyService currencyService;
    private final PortfolioService portfolioService;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              PortfolioService portfolioService,
                              CurrencyService currencyService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.portfolioService = portfolioService;
        this.currencyService = currencyService;
    }

    /**
     * Køb et asset til en bruger:
     * - Tjekker om brugeren har nok penge (i DKK)
     * - Opdaterer kontantbeholdning
     * - Opretter og gemmer en Transaction i CSV
     */

    public void buyAsset(User user, Asset asset, int quantity) {
        int date = DateTime.todayAsInt();
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        Portfolio currentPortfolio = portfolioService.buildPortfolio(user);

        double pricePerUnit = asset.getPrice();
        double totalPrice = pricePerUnit * quantity;
        double totalPriceDKK = currencyService.convertToDKK(totalPrice, asset.getCurrency());

        if (currentPortfolio.getCashBalance() < totalPriceDKK) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        Transaction t = createTransaction(
                user.getId(),
                date,
                asset.getTicker(),
                pricePerUnit,
                asset.getCurrency(),
                "buy",
                quantity
        );

        saveTransaction(t);
    }

    /**
     * Sælg et asset:
     * - Tjekker om brugeren har nok af det pågældende asset (kvantitet)
     * - Opdaterer kontantbeholdning
     * - Opretter og gemmer en Transaction i CSV
     */
    public void sellAsset(User user, Asset asset, int quantity) {
        int date = DateTime.todayAsInt();
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        int currentHolding = getHoldingForUserAndTicker(user.getId(), asset.getTicker());
        if (currentHolding < quantity) {
            throw new IllegalArgumentException("Insufficient asset quantity.");
        }

        double pricePerUnit = asset.getPrice();

        Transaction t = createTransaction(
                user.getId(),
                date,
                asset.getTicker(),
                pricePerUnit,
                asset.getCurrency(),
                "sell",
                quantity
        );
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
                                          int date,
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

    public List<Transaction> getTransactionsByUser(int userId) {
        return transactionRepository.getTransactionByUserId(userId);
    }
}
