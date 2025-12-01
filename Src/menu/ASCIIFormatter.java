package menu;
import structure.*;
import repositories.*;
import services.*;
import controller.*;

import java.util.*;

public class ASCIIFormatter {

    public void formatStocks(){
    }
    public void formatPortfolio(Portfolio portfolio, List<Transaction> transactions, CurrencyService currencyService) {
        System.out.println("=== Portfolio Summary ===");
        double cashBalance = portfolio.getCashBalance(transactions, currencyService);
        System.out.printf("Cash Balance: %.2f DKK%n", cashBalance);

        Map<String, Integer> holdings = portfolio.getAllHoldings(transactions);
        if (holdings.isEmpty()) {
            System.out.println("No assets in portfolio.");
        } else {
            System.out.println("\nAssets:");
            System.out.println("+-------+----------+");
            System.out.println("| Ticker| Quantity |");
            System.out.println("+-------+----------+");
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                System.out.printf("| %-6s | %8d |%n", entry.getKey(), entry.getValue());
            }
            System.out.println("+-------+----------+");
        }
    }

    public void formatLoginError(){
    }
    public void formatUserMenu(){
    }

}
