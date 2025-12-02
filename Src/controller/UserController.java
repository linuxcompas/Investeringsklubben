package controller;
import repositories.*;
import services.*;
import structure.*;

import java.util.List;


/* Controller / validation
Her fortæller vi java - hvad skal vi give videre til services?
HUSK - Her tjekker vi om input er rigtigt.

Controller = risky code.
Service antager at alt er i orden, fordi controlleren har godkendt det.

Low coupling
 */




public class UserController {
    private final UserRepository userRepository;
    private final RankingService rankingService;

    public UserController(UserRepository userRepository,
                          RankingService rankingService){
        this.userRepository = new UserRepository("Database/users.csv");
        this.rankingService = rankingService;

    }
    public User login (String email, String password) {

        if (email == null || email.isEmpty()){
            throw new IllegalArgumentException("Email kan ikke være tomt.");
        }
        User user = userRepository.getUserByEmail(email);

        if (user == null){
            throw new IllegalArgumentException("Email findes ikke: " + email);
        }

        String expectedPassword = "pw" + user.getId();

        if (!password.equals(expectedPassword)){
            throw new IllegalArgumentException("Forkert password.");
        }
        return user;
    }

    public List<User> viewRankings() {
        List<User> users = userRepository.loadUsers();
        return rankingService.rankUsersByTotalWealth(users);
    }
}