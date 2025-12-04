package services;

import structure.User;
import structure.Portfolio;
import java.util.*;

public class RankingService {

    private final PortfolioService portfolioService;

    public RankingService(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    /**
     * Ranger brugere efter samlet porteføljeværdi (DKK)
     */
    public List<User> rankUsersByCash(List<User> users) {
        if (users == null) return Collections.emptyList();


        List<User> sortedUsers = new ArrayList<>(users);

        sortedUsers.sort((u1, u2) -> {
            Portfolio p1 = portfolioService.buildPortfolio(u1);
            Portfolio p2 = portfolioService.buildPortfolio(u2);

            return Double.compare(
                    p2.getCashBalance(),
                    p1.getCashBalance()
            );
        });

        return sortedUsers;
    }

    // rangliste over total værdi af portfolio
    public List<User> rankUsersByTotalWealth(List<User> users) {
        if (users == null) return Collections.emptyList();

        List<User> sorted = new ArrayList<>(users);

        sorted.sort((u1, u2) -> {
            Portfolio p1 = portfolioService.buildPortfolio(u1);
            Portfolio p2 = portfolioService.buildPortfolio(u2);

            double v1 = portfolioService.getTotalValue(p1);
            double v2 = portfolioService.getTotalValue(p2);

            return Double.compare(v2, v1); // descending
        });

        return sorted;
    }
}
