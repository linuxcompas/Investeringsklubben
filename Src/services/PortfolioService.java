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
    private double getCurrentPriceDKK(String ticker) {

        // 1) search stocks
        List<Stock> stocks = stockmarketRepository.loadStockmarket();
        for (Stock s : stocks) {
            if (s.getTicker().equalsIgnoreCase(ticker)) {
                return currencyService.convertToDKK(s.getPrice(), s.getCurrency());
            }
        }

        // 2) search bonds
        BondRepository bondRepo = new BondRepository();
        List<Bond> bonds = bondRepo.loadBonds();
        for (Bond b : bonds) {
            if (b.getTicker().equalsIgnoreCase(ticker)) {
                return currencyService.convertToDKK(b.getPrice(), b.getCurrency());
            }
        }

        // nothing found
        return -1;
    }

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


}
