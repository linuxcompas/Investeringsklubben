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
}
