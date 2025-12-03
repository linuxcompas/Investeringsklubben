//package services;
//
//import org.junit.jupiter.api.*;
//import structure.*;
//import repositories.*;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class MockTransactionRepo extends TransactionRepository {
//    private List<Transaction> transactions = new ArrayList<>();
//
//    @Override
//    public List<Transaction> loadTransactions() {
//        return transactions;
//    }
//
//    @Override
//    public void saveTransactions(List<Transaction> all) {
//        transactions = all;
//    }
//}
//
//class MockCurrencyService extends CurrencyService {
//    @Override
//    public double convertToDKK(double value, String currency) {
//        return value; // 1:1 conversion for testing
//    }
//}
//
//public class TransactionServiceTest {
//
//    private TransactionService transactionService;
//    private User user;
//    private MockTransactionRepo transactionRepo;
//
//    @BeforeEach
//    void setup() {
//        user = new User(1, "Alice", "alice@example.com",
//                20000101, 100000.0, 20251101, 20251101);
//        transactionRepo = new MockTransactionRepo();
//        transactionService = new TransactionService(transactionRepo, null, new MockCurrencyService());
//    }
//
//    @Test
//    void testBuyAssetCreatesTransaction() {
//        Asset testAsset = new Asset() {
//            public String getTicker() { return "TEST"; }
//            public double getPrice() { return 50.0; }
//            public String getCurrency() { return "DKK"; }
//        };
//
//        // Buy twice
//        transactionService.buyAsset(user, testAsset, 5, 20251101);
//        transactionService.buyAsset(user, testAsset, 10, 20251201);
//
//        List<Transaction> txs = transactionRepo.loadTransactions();
//        assertEquals(2, txs.size());
//
//        assertEquals("buy", txs.get(0).getOrderType());
//        assertEquals(5, txs.get(0).getQuantity());
//        assertEquals("buy", txs.get(1).getOrderType());
//        assertEquals(10, txs.get(1).getQuantity());
//    }
//
//    @Test
//    void testSellAssetThrowsIfInsufficientQuantity() {
//        Asset testAsset = new Asset() {
//            public String getTicker() { return "TEST"; }
//            public double getPrice() { return 50.0; }
//            public String getCurrency() { return "DKK"; }
//        };
//
//        // No buy yet
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            transactionService.sellAsset(user, testAsset, 1, 20251101);
//        });
//        assertTrue(exception.getMessage().contains("Insufficient asset quantity"));
//    }
//
//    @Test
//    void testSellAssetAfterBuy() {
//        Asset testAsset = new Asset() {
//            public String getTicker() { return "TEST"; }
//            public double getPrice() { return 50.0; }
//            public String getCurrency() { return "DKK"; }
//        };
//
//        // Buy first
//        transactionService.buyAsset(user, testAsset, 10, 20251101);
//
//        // Sell some
//        transactionService.sellAsset(user, testAsset, 5, 20251201);
//
//        List<Transaction> txs = transactionRepo.loadTransactions();
//        assertEquals(2, txs.size());
//
//        Transaction sellTx = txs.get(1);
//        assertEquals("sell", sellTx.getOrderType());
//        assertEquals(5, sellTx.getQuantity());
//    }
//
//    @Test
//    void testBuyAssetInsufficientFunds() {
//        Asset expensiveAsset = new Asset() {
//            public String getTicker() { return "EXP"; }
//            public double getPrice() { return 1_000_000.0; }
//            public String getCurrency() { return "DKK"; }
//        };
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            transactionService.buyAsset(user, expensiveAsset, 1, 20251101);
//        });
//
//        assertTrue(exception.getMessage().contains("Insufficient funds"));
//    }
//}
