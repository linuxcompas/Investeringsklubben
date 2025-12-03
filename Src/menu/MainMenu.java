package menu;
import controller.*;
import services.*;
import repositories.*;
import structure.*;



public class MainMenu {

    public static void main(String[] args) {

        BondRepository bondRepository = new BondRepository();
        StockmarketRepository stockmarketRepository = new StockmarketRepository();
        CurrencyRepository currencyRepository = new CurrencyRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        UserRepository userRepository = new UserRepository("Database/users.csv");


        BondService bondService = new BondService(bondRepository, currencyRepository);
        CurrencyService currencyService = new CurrencyService();
        PortfolioService portfolioService = new PortfolioService(transactionRepository,
                bondRepository,
                stockmarketRepository,
                currencyService);
        RankingService rankingService = new RankingService(portfolioService);
        StockmarketService stockmarketService = new StockmarketService(stockmarketRepository,
                currencyService);
        TransactionService transactionService = new TransactionService(transactionRepository,
                userRepository,
                portfolioService,
                currencyService);

        AdminController adminController = new AdminController(userRepository,
                rankingService,
                portfolioService,
                stockController,
                currencyService);
        PortfolioController portfolioController = new PortfolioController(portfolioService,
                transactionService,
                transactionRepository,
                currencyService,
                stockmarketService);
        StockController stockController = new StockController(stockmarketService);
        BondController bondController = new BondController(bondService);
        UserController userController = new UserController();
        TransactionController transactionController = new TransactionController(transactionService);

        ASCIIFormatter formatter = new ASCIIFormatter(adminController,
                portfolioController,
                stockController,
                bondController,
                userController);


        formatter.formatLoginScreen();

    }


}
