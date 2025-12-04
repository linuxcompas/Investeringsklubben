package controller;

/* Controller / validation
Her fortæller vi java - hvad skal vi give videre til services?
HUSK - Her tjekker vi om input er rigtigt.

Controller = risky code.
Service antager at alt er i orden, fordi controlleren har godkendt det.

Low coupling
 */

import repositories.TransactionRepository;
import repositories.UserRepository;
import services.*;
import structure.*;

import java.util.*;

public class PortfolioController {

    private final PortfolioService portfolioService;
    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final CurrencyService currencyService;
    private final StockmarketService stockmarketService;

    public PortfolioController(PortfolioService portfolioService,
                               UserRepository userRepository,
                               TransactionService transactionService,
                               TransactionRepository transactionRepository,
                               CurrencyService currencyService,
                               StockmarketService stockmarketService) {

        this.portfolioService = portfolioService;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.currencyService = currencyService;
        this.stockmarketService = stockmarketService;
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
    public double getPortfolioValue(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");

        Portfolio p = portfolioService.buildPortfolio(user);
        return portfolioService.getTotalValue(p);
    }

    // henter gevinst / tab
    public Map<String, Double> getUserGainLoss(int userId) {
        User user = userRepository.getUserById(userId);
        return portfolioService.getGainLoss(user);
    }

    // henter nuværende pris
    public double getCurrentPriceDKK(String ticker) {
        return portfolioService.getCurrentPriceDKK(ticker);
    }



}
