//package structure;
//
//import services.*;
//import structure.*;
//import repositories.*;
//import org.junit.jupiter.api.*;
//import static org.junit.jupiter.api.Assertions.*;
//import java.util.List;
//
//public class PortfolioTest {
//
//    private User user;
//    private Portfolio portfolio;
//
//    @BeforeEach
//    void setup() {
//        user = new User(1, "Alice", "alice@example.com", 20000101, 100000.0, 20251101, 20251101);
//        portfolio = new Portfolio(user);
//    }
//
//    @Test
//    void testInitialCash() {
//        assertEquals(100000.0, portfolio.getCashBalance());
//    }
//
//    @Test
//    void testAddHolding() {
//        portfolio.addToHolding("AAPL", 10);
//        assertEquals(10, portfolio.getHolding("AAPL"));
//
//        portfolio.addToHolding("AAPL", 5);
//        assertEquals(15, portfolio.getHolding("AAPL"));
//
//        portfolio.addToHolding("TSLA", 3);
//        assertEquals(3, portfolio.getHolding("TSLA"));
//    }
//
//    @Test
//    void testGetHoldingForTickerWithTransactions() {
//        Transaction t1 = new Transaction(1, 1, 20251101, "AAPL", 100.0, "DKK", "buy", 10);
//        Transaction t2 = new Transaction(2, 1, 20251102, "AAPL", 100.0, "DKK", "sell", 4);
//        Transaction t3 = new Transaction(3, 1, 20251103, "TSLA", 200.0, "DKK", "buy", 5);
//
//        List<Transaction> transactions = List.of(t1, t2, t3);
//
//        assertEquals(6, portfolio.getHoldingForTicker("AAPL", transactions));
//        assertEquals(5, portfolio.getHoldingForTicker("TSLA", transactions));
//    }
//}
