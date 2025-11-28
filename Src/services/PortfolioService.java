package services;
import structure.*;
import java.util.*;
import repositories.*;

/* MATEMATIK / LOGIK
Her fortæller vi java - hvordan regner vi et portfolio ud?
 */

public class PortfolioService {

    private TransactionRepository transactionRepository;
    private StockmarketRepository stockmarketRepository;
    private CurrencyService currencyService;

    public PortfolioService(TransactionRepository transactionRepository,
                            StockmarketRepository stockmarketRepository,
                            CurrencyService currencyService) {
        this.transactionRepository = transactionRepository;
        this.stockmarketRepository = stockmarketRepository;
        this.currencyService = currencyService;
    }

    public Portfolio buildPortfolio(User user) {
        Portfolio portfolio = new Portfolio(user);

        List<Transaction> userTransactions = transactionRepository.getTransactionByUserId(user.getId());

        for (Transaction t : userTransactions) {

            if (t.getOrderType().equalsIgnoreCase("BUY")) {
                portfolio.addToHolding(t.getTicker(), t.getQuantity());

                double costInDKK = convertToDKK(t.getPrice() * t.getQuantity(), t.getCurrency());
                portfolio.setCashBalance(portfolio.getCashBalance() + costInDKK);

            } else if (t.getOrderType().equalsIgnoreCase("SELL")) {
                portfolio.addToHolding(t.getTicker(), -t.getQuantity());

                double revenueInDKK = convertToDKK(t.getPrice() * t.getQuantity(), t.getCurrency());
                portfolio.setCashBalance(portfolio.getCashBalance() + revenueInDKK);
            }
        }
        return portfolio;
    }

    private double convertToDKK(double value, String currency) {
        return currencyService.convertToDKK(value, currency);
    }
}
