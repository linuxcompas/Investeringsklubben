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


    public static void formatBuyTransaction(Transaction t, CurrencyService currencyService) {
        double totalInAssetCurrency = t.getPrice() * t.getQuantity();
        double totalInDKK = currencyService.convertToDKK(totalInAssetCurrency, t.getCurrency());

        System.out.println("=== BUY TRANSACTION ===");
        System.out.println("+-------------------------------+");
        System.out.printf ("| Transaction ID : %-12d |%n", t.getId());
        System.out.printf ("| User ID        : %-12d |%n", t.getUserId());
        System.out.printf ("| Date           : %-12d |%n", t.getDate());
        System.out.printf ("| Ticker         : %-12s |%n", t.getTicker());
        System.out.printf ("| Quantity       : %-12d |%n", t.getQuantity());
        System.out.printf ("| Price / unit   : %-12.2f |%n", t.getPrice());
        System.out.printf ("| Currency       : %-12s |%n", t.getCurrency());
        System.out.printf ("| Total (DKK)    : %-12.2f |%n", totalInDKK);
        System.out.println("+-------------------------------+");
        System.out.println("Status: BUY completed successfully");
        System.out.println();
    }


    public static void formatSellTransaction(Transaction t, CurrencyService currencyService) {
        double totalInAssetCurrency = t.getPrice() * t.getQuantity();
        double totalInDKK = currencyService.convertToDKK(totalInAssetCurrency, t.getCurrency());

        System.out.println("=== SELL TRANSACTION ===");
        System.out.println("+-------------------------------+");
        System.out.printf ("| Transaction ID : %-12d |%n", t.getId());
        System.out.printf ("| User ID        : %-12d |%n", t.getUserId());
        System.out.printf ("| Date           : %-12d |%n", t.getDate());
        System.out.printf ("| Ticker         : %-12s |%n", t.getTicker());
        System.out.printf ("| Quantity       : %-12d |%n", t.getQuantity());
        System.out.printf ("| Price / unit   : %-12.2f |%n", t.getPrice());
        System.out.printf ("| Currency       : %-12s |%n", t.getCurrency());
        System.out.printf ("| Total (DKK)    : %-12.2f |%n", totalInDKK);
        System.out.println("+-------------------------------+");
        System.out.println("Status: SELL completed successfully");
        System.out.println();
    }

    public static Object formatLoginScreen(UserController userController,
                                           AdminController adminController) {

        Scanner sc = new Scanner(System.in);

        System.out.println("====================================");
        System.out.println("           VELKOMMEN TIL KLUBBEN       ");
        System.out.println("====================================");
        System.out.println();
        System.out.println("              LOGIN MENU            ");
        System.out.println("------------------------------------");
        System.out.println("  Indtast dit email/username under  ");
        System.out.println();
        System.out.println("------------------------------------");
        System.out.println();

        System.out.print("Username/Email: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.println();
        System.out.println("------------------------------------");

        // 1. Check admin login:
        if (adminController.adminLogin(username, password)) {
            System.out.println("ADMIN LOGIN SUCCESSFUL");
            System.out.println("------------------------------------");
            return "admin";
        }

        // 2. Otherwise attempt regular user login:
        try {
            User u = userController.login(username, password);
            System.out.println("USER LOGIN SUCCESSFUL");
            System.out.println("------------------------------------");
            return u;
        } catch (Exception e) {
            System.out.println("LOGIN FAILED: " + e.getMessage());
            System.out.println("------------------------------------");
            return null;
        }
    }
    public void formatUserMenu(){
    }

}
