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
    private final TransactionRepository transactionRepository;

    public AdminController(UserRepository userRepository,
                           RankingService rankingService,
                           PortfolioService portfolioService,
                           StockController stockController,
                           PortfolioController portfolioController,
                           TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.rankingService = rankingService;
        this.portfolioService = portfolioService;
        this.stockController = stockController;
        this.portfolioController = portfolioController;
        this.transactionRepository = transactionRepository;
    }

    //Henter alle brugere
    public List<User> viewAllUsers() {
        return userRepository.loadUsers();
    }


    // henter rangliste
    public List<User> viewRankings() {
        List<User> users = userRepository.loadUsers();
        return rankingService.rankUsersByTotalWealth(users);
    }

    // se fordeling af sektorer
    public Map<String, Integer> viewSectorDistribution(User user) {
        return stockController.getSectorDistribution(user, portfolioController);
    }

    // henter aktiefordeling
    public Map<String, Integer> getAssetDistribution(int userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        return portfolioService.getAssetDistribution(user);
    }


    // se bruger detaljer
    public Portfolio viewUserDetails(int userId) {

        User user = userRepository.getUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        return portfolioService.buildPortfolio(user);
    }

    // hent bruger efter ID
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

// hent portfolio værdi
    public Map<User, Double> getPortfolioValues() {
        Map<User, Double> result = new LinkedHashMap<>();

        for (User u : userRepository.loadUsers()) {
            Portfolio portfolio = portfolioService.buildPortfolio(u);
            double value = portfolioService.getTotalValue(portfolio);
            result.put(u, value);
        }
        return result;
    }


    public boolean adminLogin(String username, String password) {
        return "admin".equals(username) && "admin".equals(password);
    }



}
