package services;
import structure.*;
import java.util.*;
import repositories.*;

/* MATEMATIK / LOGIK
Her fortæller vi java - hvordan regner vi et portfolio ud?
 */

public class PortfolioService {

    private TransactionRepository transactionRepository;
    private BondRepository bondRepository;
    private StockmarketRepository stockmarketRepository;
    private CurrencyService currencyService;

    public PortfolioService(TransactionRepository transactionRepository,
                            BondRepository bondRepository,
                            StockmarketRepository stockmarketRepository,
                            CurrencyService currencyService) {
        this.transactionRepository = transactionRepository;
        this.bondRepository = bondRepository;
        this.stockmarketRepository = stockmarketRepository;
        this.currencyService = currencyService;
    }

    public Portfolio buildPortfolio(User user) {
        Portfolio portfolio = new Portfolio(user);
        List<Transaction> userTransactions =
                transactionRepository.getTransactionByUserId(user.getId());

        for (Transaction t : userTransactions) {

            double valueDKK = currencyService.convertToDKK(
                    t.getPrice() * t.getQuantity(),
                    t.getCurrency()
            );

            if (t.getOrderType().equalsIgnoreCase("buy")) {

                portfolio.addToHolding(t.getTicker(), t.getQuantity());
                portfolio.setCashBalance(portfolio.getCashBalance() - valueDKK);

            } else if (t.getOrderType().equalsIgnoreCase("sell")) {

                portfolio.addToHolding(t.getTicker(), -t.getQuantity());
                portfolio.setCashBalance(portfolio.getCashBalance() + valueDKK);
            }
        }

        return portfolio;

    }
    /**
     * Finds current price for a ticker (stock or bond), returns price in DKK.
     */
    public double getCurrentPriceDKK(String ticker) {

        // søger aktier
        List<Stock> stocks = stockmarketRepository.loadStockmarket();
        for (Stock s : stocks) {
            if (s.getTicker().equalsIgnoreCase(ticker)) {
                return currencyService.convertToDKK(s.getPrice(), s.getCurrency());
            }
        }

        // søger obligationer
        List<Bond> bonds = bondRepository.loadBonds();
        for (Bond b : bonds) {
            if (b.getTicker().equalsIgnoreCase(ticker)) {
                return currencyService.convertToDKK(b   .getPrice(), b.getCurrency());
            }
        }

        // intet fundet
        return -1;
    }

    public List<Stock> getAllStocks() {
        return stockmarketRepository.loadStockmarket();
    }


    // portfolio værdi
    public double getTotalValue(Portfolio portfolio) {
        double total = portfolio.getCashBalance();
        Map<String, Integer> holdings = portfolio.getHolding();

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String ticker = entry.getKey();
            int quantity = entry.getValue();
            double priceDKK = getCurrentPriceDKK(ticker);

            if (priceDKK > 0) {
                total += priceDKK * quantity;
            }
        }

        return total;
    }

    // aktiefordeling for en bruger

    public Map<String, Integer> getAssetDistribution(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // bygger portfolio
        Portfolio p = buildPortfolio(user);

        // indlæser alle transaktioner + holdings
        List<Transaction> transactions =
                transactionRepository.getTransactionByUserId(user.getId());

        Map<String, Integer> holdings = p.getAllHoldings(transactions);

        // henter markedsdata
        List<Stock> allStocks = stockmarketRepository.loadStockmarket();
        List<Bond> allBonds = bondRepository.loadBonds();

        Map<String, Integer> dist = new HashMap<>();
        int stockCount = 0;
        int bondCount = 0;

        // identificer hver ticker
        for (String ticker : holdings.keySet()) {
            int qty = holdings.get(ticker);

            boolean isStock = allStocks.stream()
                    .anyMatch(s -> s.getTicker().equalsIgnoreCase(ticker));

            boolean isBond = allBonds.stream()
                    .anyMatch(b -> b.getTicker().equalsIgnoreCase(ticker));

            if (isStock) {
                stockCount += qty;
            } else if (isBond) {
                bondCount += qty;
            }
        }

        dist.put("stocks", stockCount);
        dist.put("bonds", bondCount);

        return dist;
    }

    public Map<String, Double> getGainLoss(User user) {

        List<Transaction> transactions =
                transactionRepository.getTransactionByUserId(user.getId());

        // mapper ticker → første købs pris DKK
        Map<String, Double> firstBuy = new HashMap<>();
        // mapper ticker → nuværende beholdning
        Map<String, Integer> quantity = new HashMap<>();

        for (Transaction t : transactions) {
            String ticker = t.getTicker();

            // finder første købspris
            if (t.getOrderType().equalsIgnoreCase("buy")) {

                if (!firstBuy.containsKey(ticker)) {
                    double buyPriceDKK = currencyService.convertToDKK(
                            t.getPrice(), t.getCurrency()
                    );
                    firstBuy.put(ticker, buyPriceDKK);
                }

                quantity.put(ticker,
                        quantity.getOrDefault(ticker, 0) + t.getQuantity());
            }

            if (t.getOrderType().equalsIgnoreCase("sell")) {
                quantity.put(ticker,
                        quantity.getOrDefault(ticker, 0) - t.getQuantity());
            }
        }

        // beregner gevinst / tab
        Map<String, Double> result = new LinkedHashMap<>();

        for (String ticker : quantity.keySet()) {
            int qty = quantity.get(ticker);
            if (qty <= 0) continue; // bruger har ikke flere

            double firstPrice = firstBuy.get(ticker);
            double currentPrice = getCurrentPriceDKK(ticker);

            double gainLoss = (currentPrice - firstPrice) * qty;

            result.put(ticker, gainLoss);
        }

        return result;
    }


}
