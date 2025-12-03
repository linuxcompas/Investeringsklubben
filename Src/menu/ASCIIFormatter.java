package menu;
import structure.*;
import controller.*;
import java.util.*;

public class ASCIIFormatter {

    private final AdminController adminController;
    private final PortfolioController portfolioController;
    private final StockController stockController;
    private final BondController bondController;
    private final UserController userController;

    public ASCIIFormatter(AdminController adminController,
                          PortfolioController portfolioController,
                          StockController stockController,
                          BondController bondController,
                          UserController userController) {
        this.adminController = adminController;
        this.portfolioController = portfolioController;
        this.stockController = stockController;
        this.bondController = bondController;
        this.userController = userController;
    }

    private static final Scanner sc = new Scanner(System.in);


    // Valg af aktie oversigt + obligationer
    public void formatStockmarketMenu() {

        boolean exit = false;

        while (!exit) {
            System.out.println("=====================================================");
            System.out.println("                  AKTIER & OBLIGATIONER");
            System.out.println("=====================================================");
            System.out.println("[1] Se aktier (by market value desc)");
            System.out.println("[2] Se aktier (pris stigende)");
            System.out.println("[3] Se aktier (pris faldende)");
            System.out.println("[4] Se obligationer (udløbsdato stigende)");
            System.out.println("[5] Se obligationer (rente faldende)");
            System.out.println("[6] Exit");
            System.out.print("Vælg en mulighed: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> displayStocksSortedByValue();
                case "2" -> displayStocksSorted(Stock.sortByPriceAsc);
                case "3" -> displayStocksSorted(Stock.sortByPriceDesc);
                case "4" -> displayBondsSorted(bondController.getBondsSortedByMaturityAsc());
                case "5" -> displayBondsSorted(bondController.getBondsSortedByYieldDesc());
                case "6" -> exit = true;
                default -> System.out.println("Ugyldigt input. Prøv igen.\n");
            }
        }
    }


    // ------------------------------------------------------------
    // STOCK formatting:
    // ------------------------------------------------------------
    private void displayStocksSortedByValue() {
        List<Stock> stocks = stockController.getStocksSortedByValueDesc();

        System.out.println("\n---------------------- AKTIER -----------------------");
        System.out.printf("%-8s %-20s %-10s %-12s %-8s %-8s %-10s%n",
                "Ticker", "Navn", "Sektor", "Kurs(DKK)", "Udbytte", "Kreditrating", "Børs");

        for (Stock s : stocks) {
            double priceDKK = stockController.getValueInDKK(s);

            System.out.printf("%-8s %-20s %-10s %-12.2f %-8.2f %-8s %-10s%n",
                    s.getTicker(), s.getName(), s.getSector(),
                    priceDKK, s.getDividendYield(), s.getRating(), s.getMarket());
        }
        System.out.println();
    }


    private void displayStocksSorted(Comparator<Stock> comparator) {
        List<Stock> stocks = stockController.getAllStocks().stream()
                .sorted(comparator)
                .toList();

        System.out.println("\n---------------------- AKTIER -----------------------");
        System.out.printf("%-8s %-20s %-10s %-12s %-8s %-8s %-10s%n",
                "Ticker", "Navn", "Sektor", "Kurs(DKK)", "Udbytte", "Kreditrating", "Børs");

        for (Stock s : stocks) {
            double priceDKK = stockController.getValueInDKK(s);

            System.out.printf("%-8s %-20s %-10s %-12.2f %-8.2f %-8s %-10s%n",
                    s.getTicker(), s.getName(), s.getSector(),
                    priceDKK, s.getDividendYield(), s.getRating(), s.getMarket());
        }
        System.out.println();
    }


    // ------------------------------------------------------------
    // BOND formattering:
    // ------------------------------------------------------------
    private void displayBondsSorted(List<Bond> bonds) {

        System.out.println("\n-------------------- OBLIGATIONER ----------------------");
        System.out.printf("%-8s %-20s %-12s %-12s %-10s %-10s %-8s%n",
                "Ticker", "Navn", "Kurs(DKK)", "Kuponrente", "Udstedelsesdato", "Udløbsdato", "Kreditrating");

        for (Bond b : bonds) {
            double priceDKK = bondController.getValueInDKK(b);

            System.out.printf("%-8s %-20s %-12.2f %-12.2f %-10d %-10d %-8s%n",
                    b.getTicker(), b.getName(), priceDKK, b.getCouponRate(),
                    b.getIssueDate(), b.getMaturityDate(), b.getRating());
        }

        System.out.println();
    }
    // ------------------------------------------------------------
    // PORTFOLIO formattering:
    // ------------------------------------------------------------

    public void formatPortfolio(User user, PortfolioController portfolioController) {

        Map<String, Object> portfolioData = portfolioController.getPortfolio(user);

        double cash = (double) portfolioData.get("cash");
        Map<String, Integer> holdings =
                (Map<String, Integer>) portfolioData.get("holdings");

        System.out.println("=== Portfolio opsummering ===");
        System.out.printf("Kontantbeholdning: %.2f DKK%n", cash);

        if (holdings.isEmpty()) {
            System.out.println("Ingen aktier i portfolio.");
            return;
        }

        System.out.println("\nAktiver:");
        System.out.println("+-------+----------+");
        System.out.println("| Ticker| Mængde |");
        System.out.println("+-------+----------+");

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            System.out.printf("| %-6s | %8d |%n", entry.getKey(), entry.getValue());
        }

        System.out.println("+-------+----------+");
    }
    // ------------------------------------------------------------
    // KØB :
    // ------------------------------------------------------------
    public void formatBuyMenu(User user,
                              StockController stockController,
                              BondController bondController,
                              TransactionController transactionController) {

        System.out.println("=== KØB AKTIVER ===");

        System.out.print("Indtast ticker: ");
        String ticker = sc.nextLine().trim().toUpperCase();

        // Search in controllers (not repos)
        Asset asset = stockController.getStockByTicker(ticker);
        if (asset == null) {
            asset = bondController.getBondByTicker(ticker);
        }

        if (asset == null) {
            System.out.println("Ukendt ticker.\n");
            return;
        }

        System.out.print("Indtast mængde: ");
        int qty;

        try {
            qty = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Mængde skal være et tal.\n");
            return;
        }

        // Call controller (will validate)
        try {
            transactionController.buy(user, asset, qty);
            System.out.println("✔ Køb fuldført!\n");
        }
        catch (IllegalArgumentException e) {
            // Print error from controller/service
            System.out.println("Advarsel: " + e.getMessage() + "\n");
        }
    }

    // ------------------------------------------------------------
    // SALG:
    // ------------------------------------------------------------
    public void formatSellMenu(User user,
                               StockController stockController,
                               BondController bondController,
                               TransactionController transactionController) {

        System.out.println("=== KØB AKTIVER ===");

        System.out.print("Indtast ticker: ");
        String ticker = sc.nextLine().trim().toUpperCase();

        Asset asset = stockController.getStockByTicker(ticker);
        if (asset == null) {
            asset = bondController.getBondByTicker(ticker);
        }

        if (asset == null) {
            System.out.println("Ukendt ticker.\n");
            return;
        }

        System.out.print("Indtast mængde: ");
        int qty;

        try {
            qty = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Mængde skal være et tal. \n");
            return;
        }

        try {
            transactionController.sell(user, asset, qty);
            System.out.println("✔ Salg gennemført!\n");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Advarsel: " + e.getMessage() + "\n");
        }
    }

    // ------------------------------------------------------------
    // LISTER formattering:
    // ------------------------------------------------------------
    private void formatAllUserValues() {
        Map<User, Double> values = adminController.getPortfolioValues();

        System.out.println("\n===== BRUGER PORTFOLIO VÆRDI =====");
        for (var entry : values.entrySet()) {
            System.out.printf("%-20s : %.2f DKK%n",
                    entry.getKey().getFullName(),
                    entry.getValue());
        }
        System.out.println();
    }
    private void formatRankings() {
        List<User> ranking = adminController.viewRankings();

        System.out.println("\n===== RANGLISTE =====");
        int pos = 1;

        for (User u : ranking) {
            double total = portfolioController.getPortfolioValue(u);
            System.out.printf("%d. %-20s  %.2f DKK%n",
                    pos++, u.getFullName(), total);
        }
        System.out.println();
    }

    private void formatAssetDistribution() {
        System.out.print("Indtast bruger ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Map<String, Integer> dist = adminController.getAssetDistribution(id);

        System.out.println("\n===== AKTIVER FORDELING =====");
        System.out.println("Aktier: " + dist.get("stock"));
        System.out.println("Obligationer : " + dist.get("bonds"));
        System.out.println();
    }

    private void formatSectorDistribution(User user) {
        Map<String, Integer> sectors = adminController.viewSectorDistribution(user);

        System.out.println("=== Sektor Fordeling ===");
        for (String sector : sectors.keySet()) {
            System.out.printf("%-20s : %d%n", sector, sectors.get(sector));
        }
    }

    // ------------------------------------------------------------
    // TRANSAKTIONSHISTORIK formattering:
    // ------------------------------------------------------------
    public void formatTransactionHistory(User user, TransactionController transactionController) {
        List<Transaction> transactions = transactionController.getTransactions(user);

        if (transactions.isEmpty()) {
            System.out.println("Ingen transaktioner for denne bruger.\n");
            return;
        }

        // Sorteret stigende:
        Collections.sort(transactions);

        System.out.println("=================================================================================");
        System.out.printf("| %-4s | %-10s | %-10s | %-10s | %-8s | %-8s |\n",
                "ID", "Dato", "Ticker", "Type", "Kurs", "Antal");
        System.out.println("=================================================================================");

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




    // ------------------------------------------------------------
    // LOGIN MENU formattering:
    // ------------------------------------------------------------
    public void formatLoginScreen() {

        System.out.println("======================================");
        System.out.println("           LOGIN MENU");
        System.out.println("======================================");
        System.out.println("1: Admin login");
        System.out.println("2: Bruger login");
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
    // ------------------------------------------------------------
    // ADMIN:
    // ------------------------------------------------------------
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

    // ------------------------------------------------------------
    // MEDLEM:
    // ------------------------------------------------------------

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

    // ------------------------------------------------------------
    // ADMIN MENU:
    // ------------------------------------------------------------

    public void formatAdminMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("============= ADMIN MENU =============");
            System.out.println("1: Se alle brugere + portfolio værdi");
            System.out.println("2: Se rangliste");
            System.out.println("3: Se aktiefordeling for en bruger");
            System.out.println("4: Se sektorfordeling for en bruger");
            System.out.println("5: Exit");
            System.out.print("Valg: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> formatAllUserValues();
                case "2" -> formatRankings();
                case "3" -> formatAssetDistribution();
                case "4" -> {
                    System.out.print("Indtast bruger ID: ");
                    int id = Integer.parseInt(sc.nextLine());

                    User user = adminController.getUserById(id); // You may need to add this
                    if (user == null) {
                        System.out.println("Bruger ikke fundet.\n");
                    } else {
                        formatSectorDistribution(user);
                    }
                }
                case "5" -> exit = true;
                default -> System.out.println("Ugyldigt valg.\n");
            }
        }
    }

    // ------------------------------------------------------------
    // USER MENU:
    // ------------------------------------------------------------

    public void formatUserMenu(User user,
                               StockController stockController,
                               BondController bondController,
                               PortfolioController portfolioController,
                               TransactionController transactionController) {

        boolean exit = false;

        while (!exit) {
            System.out.println("============= USER MENU =============");
            System.out.println("Logget ind som: " + user.getFullName());
            System.out.println("1: Se aktiver");
            System.out.println("2: Køb aktiver");
            System.out.println("3: Sælg aktiver");
            System.out.println("4: Se portfolio");
            System.out.println("5: Se transaktionshistorik");
            System.out.println("6: Log ud");
            System.out.print("Vælg: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> formatStockmarketMenu(); // already implemented

                case "2" -> formatBuyMenu(user, stockController, bondController, transactionController);

                case "3" -> formatSellMenu(user, stockController, bondController, transactionController);

                case "4" -> formatPortfolio(user, portfolioController);

                case "5" -> formatTransactionHistory(user, transactionController);

                case "6" -> {
                    System.out.println("Logger ud...\n");
                    exit = true;
                }

                default -> System.out.println("Ugyldigt valg.\n");
            }
        }
    }


}
