package menu;

import controller.*;
import services.*;
import repositories.*;

public class MainMenu {

    public static void main(String[] args) {

        // ---------------------
        // REPOSITORIES
        // ---------------------
        UserRepository userRepository = new UserRepository("Database/users.csv");
        BondRepository bondRepository = new BondRepository();
        CurrencyRepository currencyRepository = new CurrencyRepository();
        StockmarketRepository stockmarketRepository = new StockmarketRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        // ---------------------
        // SERVICES
        // ---------------------
        CurrencyService currencyService = new CurrencyService();
        BondService bondService = new BondService(bondRepository, currencyService);
        StockmarketService stockmarketService = new StockmarketService(stockmarketRepository, currencyService);
        PortfolioService portfolioService = new PortfolioService(
                transactionRepository,
                bondRepository,
                stockmarketRepository,
                currencyService
        );
        RankingService rankingService = new RankingService(portfolioService);
        TransactionService transactionService = new TransactionService(
                transactionRepository,
                userRepository,
                portfolioService,
                currencyService
        );

        // ---------------------
        // CONTROLLERS
        // ---------------------
        StockController stockController = new StockController(stockmarketService);
        BondController bondController = new BondController(bondService);
        TransactionController transactionController = new TransactionController(transactionService);

        PortfolioController portfolioController = new PortfolioController(
                portfolioService,
                transactionService,
                transactionRepository,
                currencyService,
                stockmarketService
        );

        AdminController adminController = new AdminController(
                userRepository,
                rankingService,
                portfolioService,
                stockController,
                portfolioController
        );

        UserController userController = new UserController();

        // ---------------------
        // FORMATTER & START
        // ---------------------
        ASCIIFormatter formatter = new ASCIIFormatter(
                adminController,
                portfolioController,
                stockController,
                bondController,
                userController
        );

        formatter.formatLoginScreen();
    }
}
