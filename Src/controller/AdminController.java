package controller;

import repositories.UserRepository;
import services.RankingService;
import services.PortfolioService;
import structure.User;
import structure.Portfolio;

import java.util.List;


public class AdminController {

    private final UserRepository userRepository;
    private final RankingService rankingService;
    private final PortfolioService portfolioService;

    public AdminController(UserRepository userRepository,
                           RankingService rankingService,
                           PortfolioService portfolioService) {
        this.userRepository = userRepository;
        this.rankingService = rankingService;
        this.portfolioService = portfolioService;
    }

    //Henter alle brugere
    public List<User> viewAllUsers() {
        return userRepository.loadUsers();
    }


    public List<User> viewRankings() {
        List<User> users = userRepository.loadUsers();
        return rankingService.rankUsersByTotalWealth(users);
    }


    public Portfolio viewUserDetails(int userId) {

        User user = userRepository.getUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        return portfolioService.getPortfolio(user);
    }
}
