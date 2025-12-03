package controller;

import repositories.*;
import services.*;
import structure.*;
import java.util.*;


public class AdminController {

    private final UserRepository userRepository;
    private final RankingService rankingService;
    private final PortfolioService portfolioService;
    private final StockController stockController;
    private final PortfolioController portfolioController;

    public AdminController(UserRepository userRepository,
                           RankingService rankingService,
                           PortfolioService portfolioService,
                           StockController stockController,
                           PortfolioController portfolioController) {
        this.userRepository = userRepository;
        this.rankingService = rankingService;
        this.portfolioService = portfolioService;
        this.stockController = stockController;
        this.portfolioController = portfolioController;
    }

    //Henter alle brugere
    public List<User> viewAllUsers() {
        return userRepository.loadUsers();
    }


    public List<User> viewRankings() {
        List<User> users = userRepository.loadUsers();
        return rankingService.rankUsersByTotalWealth(users);
    }

    public Map<String, Integer> viewSectorDistribution(User user) {
        return stockController.getSectorDistribution(user, portfolioController);
    }

    public Portfolio viewUserDetails(int userId) {

        User user = userRepository.getUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        return portfolioService.buildPortfolio(user);
    }
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }


    public Map<User, Double> getPortfolioValues() {
        Map<User, Double> result = new LinkedHashMap<>();

        for (User u : userRepository.loadUsers()) {
            Portfolio portfolio = portfolioService.buildPortfolio(u);
            double value = portfolioService.getTotalValue(portfolio);
            result.put(u, value);
        }
        return result;
    }

    public Map<String, Integer> getAssetDistribution(int userId) {
        User user = userRepository.getUserById(userId);
        Portfolio p = portfolioService.buildPortfolio(user);

        Map<String, Integer> holdings = p.getHolding();
        Map<String, Integer> dist = new HashMap<>();

        int stocks = 0;
        int bonds = 0;

        for (String ticker : holdings.keySet()) {
            if (ticker.startsWith("STK")) stocks += holdings.get(ticker);
            else if (ticker.startsWith("BND")) bonds += holdings.get(ticker);
        }



        dist.put("stocks", stocks);
        dist.put("bonds", bonds);

        return dist;
    }


    public boolean adminLogin(String username, String password) {
        return "admin".equals(username) && "admin".equals(password);
    }



}
