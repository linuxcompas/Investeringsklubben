package menu;
import structure.*;
import repositories.*;
import services.*;
import controller.*;
import java.util.*;

public class ASCIIFormatter {

    private final AdminController adminController;
    private final UserController userController;

    public ASCIIFormatter(AdminController adminController, UserController userController) {
        this.adminController = adminController;
        this.userController = userController;
    }

    private static final Scanner sc = new Scanner(System.in);

    public static void formatStockmarketMenu(
            StockmarketService stockService,
            List<Stock> stocks,
            List<Bond> bonds,
            CurrencyService currencyService
    ) {

        boolean exit = false;

        while (!exit) {
            System.out.println("=====================================================");
            System.out.println("                  STOCK MARKET MENU");
            System.out.println("=====================================================");
            System.out.println("[1] View Stocks (by value descending)");
            System.out.println("[2] View Stocks (sort by price ascending)");
            System.out.println("[3] View Stocks (sort by price descending)");
            System.out.println("[4] View Bonds (sort by maturity date ascending)");
            System.out.println("[5] View Bonds (sort by coupon descending)");
            System.out.println("[6] Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    displayStocks(stocks, stockService, currencyService, Comparator.comparingDouble(stockService::getValueInDKK).reversed());
                    break;

                case "2":
                    displayStocks(stocks, stockService, currencyService, Stock.sortByPriceAsc);
                    break;

                case "3":
                    displayStocks(stocks, stockService, currencyService, Stock.sortByPriceDesc);
                    break;

                case "4":
                    displayBonds(bonds, currencyService, Bond.sortByMaturityAsc);
                    break;

                case "5":
                    displayBonds(bonds, currencyService, Bond.sortByYieldDesc);
                    break;

                case "6":
                    exit = true;
                    System.out.println("Returning to main menu...\n");
                    break;

                default:
                    System.out.println("Invalid option, try again.\n");
            }
        }
    }

    private static void displayStocks(List<Stock> stocks, StockmarketService stockService,
                                      CurrencyService currencyService,
                                      Comparator<Stock> comparator) {

        List<Stock> sorted = new ArrayList<>(stocks);
        sorted.sort(comparator);

        System.out.println("\n---------------------- STOCKS -----------------------");
        System.out.printf("%-8s %-20s %-10s %-12s %-8s %-8s %-10s%n",
                "Ticker", "Name", "Sector", "Price (DKK)", "Yield", "Rating", "Market");

        for (Stock s : sorted) {
            double valueDKK = stockService.getValueInDKK(s);
            System.out.printf("%-8s %-20s %-10s %-12.2f %-8.2f %-8s %-10s%n",
                    s.getTicker(), s.getName(), s.getSector(),
                    valueDKK, s.getDividendYield(), s.getRating(), s.getMarket());
        }
        System.out.println();
    }

    private static void displayBonds(List<Bond> bonds, CurrencyService currencyService,
                                     Comparator<Bond> comparator) {

        List<Bond> sorted = new ArrayList<>(bonds);
        sorted.sort(comparator);

        System.out.println("\n---------------------- BONDS ------------------------");
        System.out.printf("%-8s %-20s %-12s %-12s %-10s %-10s %-8s%n",
                "Ticker", "Name", "Price (DKK)", "Coupon", "Issue", "Maturity", "Rating");

        for (Bond b : sorted) {
            double priceDKK = b.getCurrency().equalsIgnoreCase("DKK")
                    ? b.getPrice()
                    : b.getPrice() * currencyService.getRate(b.getCurrency());

            System.out.printf("%-8s %-20s %-12.2f %-12.2f %-10d %-10d %-8s%n",
                    b.getTicker(), b.getName(), priceDKK, b.getCouponRate(),
                    b.getIssueDate(), b.getMaturityDate(), b.getRating());
        }
        System.out.println();
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
        System.out.printf("| Transaction ID : %-12d |%n", t.getId());
        System.out.printf("| User ID        : %-12d |%n", t.getUserId());
        System.out.printf("| Date           : %-12d |%n", t.getDate());
        System.out.printf("| Ticker         : %-12s |%n", t.getTicker());
        System.out.printf("| Quantity       : %-12d |%n", t.getQuantity());
        System.out.printf("| Price / unit   : %-12.2f |%n", t.getPrice());
        System.out.printf("| Currency       : %-12s |%n", t.getCurrency());
        System.out.printf("| Total (DKK)    : %-12.2f |%n", totalInDKK);
        System.out.println("+-------------------------------+");
        System.out.println("Status: BUY completed successfully");
        System.out.println();
    }

    public void formatTransactionHistory(int userId) {
        TransactionRepository transactionRepository = new TransactionRepository();
        List<Transaction> transactions = transactionRepository.getTransactionByUserId(userId);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this user.\n");
            return;
        }

        // Sort by date ascending
        Collections.sort(transactions);

        // Print header
        System.out.println("=================================================================================");
        System.out.printf("| %-4s | %-10s | %-10s | %-10s | %-8s | %-8s |\n",
                "ID", "Date", "Ticker", "Type", "Price", "Qty");
        System.out.println("=================================================================================");

        // Print each transaction
        for (Transaction t : transactions) {
            String dateFormatted = String.format("%04d-%02d-%02d",
                    t.getDate() / 10000,
                    (t.getDate() / 100) % 100,
                    t.getDate() % 100
            );

            System.out.printf("| %-4d | %-10s | %-10s | %-10s | %-8.2f | %-8d |\n",
                    t.getId(),
                    dateFormatted,
                    t.getTicker(),
                    t.getOrderType(),
                    t.getPrice(),
                    t.getQuantity()
            );
        }

        System.out.println("=================================================================================\n");
    }


    public static void formatSellTransaction(Transaction t, CurrencyService currencyService) {
        double totalInAssetCurrency = t.getPrice() * t.getQuantity();
        double totalInDKK = currencyService.convertToDKK(totalInAssetCurrency, t.getCurrency());

        System.out.println("=== SELL TRANSACTION ===");
        System.out.println("+-------------------------------+");
        System.out.printf("| Transaction ID : %-12d |%n", t.getId());
        System.out.printf("| User ID        : %-12d |%n", t.getUserId());
        System.out.printf("| Date           : %-12d |%n", t.getDate());
        System.out.printf("| Ticker         : %-12s |%n", t.getTicker());
        System.out.printf("| Quantity       : %-12d |%n", t.getQuantity());
        System.out.printf("| Price / unit   : %-12.2f |%n", t.getPrice());
        System.out.printf("| Currency       : %-12s |%n", t.getCurrency());
        System.out.printf("| Total (DKK)    : %-12.2f |%n", totalInDKK);
        System.out.println("+-------------------------------+");
        System.out.println("Status: SELL completed successfully");
        System.out.println();
    }




    public void formatLoginScreen() {

        System.out.println("======================================");
        System.out.println("           LOGIN MENU");
        System.out.println("======================================");
        System.out.println("1: Admin login");
        System.out.println("2: User login");
        System.out.print("Valg: ");

        String choice = sc.nextLine();

        switch (choice) {
            case "1" -> adminLoginFlow();
            case "2" -> userLoginFlow();
            default -> {
                System.out.println("Ugyldigt valg.\n");
                formatLoginScreen(); // restart screen
            }
        }
    }

    private void adminLoginFlow() {
        System.out.print("Admin brugernavn: ");
        String username = sc.nextLine();

        System.out.print("Admin password: ");
        String password = sc.nextLine();

        boolean ok = adminController.adminLogin(username, password);

        if (!ok) {
            System.out.println("Forkert admin login!\n");
            formatLoginScreen();
            return;
        }

        System.out.println("✔ Admin login succesfuld!\n");
        // continue to admin menu here
    }

    private void userLoginFlow() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        try {
            User u = userController.login(email, password);
            System.out.println("✔ Login succesfuld! Velkommen, " + u.getFullName());
            // continue to user menu here

        } catch (IllegalArgumentException e) {
            System.out.println("Login fejl: " + e.getMessage() + "\n");
            formatLoginScreen();
        }
    }
}
