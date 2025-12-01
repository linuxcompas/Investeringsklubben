package services;

import structure.User;
import java.util.*;

public class RankingService {

    // Ranger brugere efter samlet formue
    public List<User> rankUsersByTotalWealth(List<User> users) {
        if (users == null) return Collections.emptyList();

        // sorter efter kontantbeholdning, størst først
        users.sort(Comparator.comparingDouble(User::getCashBalance).reversed());
        return users;
    }
}
