package controller;

/* Controller / validation
Her fortæller vi java - hvad skal vi give videre til services?
HUSK - Her tjekker vi om input er rigtigt.

Controller = risky code.
Service antager at alt er i orden, fordi controlleren har godkendt det.

Low coupling
 */

import repositories.TransactionRepository;
import services.*;
import structure.*;

import java.util.*;

public class PortfolioController {

    private final PortfolioService portfolioService;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final CurrencyService currencyService;

    public PortfolioController(PortfolioService portfolioService,
                               TransactionService transactionService,
                               TransactionRepository transactionRepository,
                               CurrencyService currencyService) {

        this.portfolioService = portfolioService;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.currencyService = currencyService;
    }


    /*
    Henter hele portfolio for en bruger, inkl. kontantbeholdning og aktier.
    Returnerer hashmap -> "cash" = double, "holdings" = map<String, integer>
     */
    public Map<String, Object> getPortfolio(User user){
        if (user == null){
            throw new IllegalArgumentException("User can't be null");
        }

        Portfolio portfolio = portfolioService.buildPortfolio(user);
        List<Transaction> transactions = transactionRepository.getTransactionByUserId(user.getId());

        double cash = portfolio.getCashBalance(transactions, currencyService);
        Map<String, Integer> holdings = portfolio.getAllHoldings(transactions);

        Map<String, Object> portfolioData = new HashMap<>();
        portfolioData.put("cash", cash);
        portfolioData.put("holdings", holdings);
        return portfolioData;
    }

    /*
    Henter beholdningen af en specifik ticker for en bruger
     */

    public int getHolding(User user, String ticker){
        if (user == null){
            throw new IllegalArgumentException("User can't be null");
        }
        if (ticker == null || ticker.isEmpty()){
            throw new IllegalArgumentException("Ticker can't be null or empty");
        }

        Portfolio portfolio = portfolioService.buildPortfolio(user);
        List<Transaction> transactions = transactionRepository.getTransactionByUserId(user.getId());

        Map<String, Integer> holdings = portfolio.getAllHoldings(transactions);
        return holdings.getOrDefault(ticker.toUpperCase(), 0);
    }

    /*
    Køber Asset til bruger -> uddelegerer til TransactionService
     */

    public void buyAsset(User user, Asset asset, int quantity, int date){
        if (user == null || asset == null){
            throw new IllegalArgumentException("User or asset can't be null");
        }
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity can't be negative");
        }
        transactionService.buyAsset(user, asset, quantity, date);
    }

    /*
    Sælger Asset fra bruger -> uddelegerer til TransactionService
     */

    public void sellAsset(User user, Asset asset, int quantity, int date){
        if (user == null || asset == null){
            throw new IllegalArgumentException("User or asset can't be null");
        }
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity can't be negative");
        }
        transactionService.sellAsset(user, asset, quantity, date);
    }
}
