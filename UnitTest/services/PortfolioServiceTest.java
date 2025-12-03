//package services;
//
//import org.junit.jupiter.api.*;
//import structure.*;
//import repositories.*;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class MockTransactionRepository extends TransactionRepository {
//    @Override
//    public List<Transaction> getTransactionByUserId(int userId) {
//        return List.of(
//                new Transaction(1, userId, 20251101, "AAPL", 100, "DKK", "buy", 10),
//                new Transaction(2, userId, 20251102, "AAPL", 100, "DKK", "sell", 3),
//                new Transaction(3, userId, 20251103, "TSLA", 200, "DKK", "buy", 2)
//        );
//    }
//}
//
//class TestCurrencyService extends CurrencyService {
//    @Override
//    public double convertToDKK(double value, String currency) {
//        return value; // assume 1:1 for simplicity
//    }
//}
//
//public class PortfolioServiceTest {
//
//    private PortfolioService portfolioService;
//    private User user;
//
//    @BeforeEach
//    void setup() {
//        user = new User(1, "Alice", "alice@example.com", 20000101, 100000.0, 20251101, 20251101);
//        portfolioService = new PortfolioService(
//                new MockTransactionRepository(),
//                null,
//                new MockCurrencyService()
//        );
//    }
//
//    @Test
//    void testBuildPortfolio() {
//        Portfolio portfolio = portfolioService.buildPortfolio(user);
//
//        assertEquals(2, portfolio.getHolding("AAPL")); // 10 buy - 8 sell
//        assertEquals(2, portfolio.getHolding("TSLA"));
//        assertEquals(100000 - (10*100) + (3*100) - (2*200), portfolio.getCashBalance()); // simplified
//    }
//}
