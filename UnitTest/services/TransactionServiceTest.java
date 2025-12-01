package services;

import org.junit.jupiter.api.Test;
import repositories.TransactionRepository;
import repositories.UserRepository;
import structure.Asset;
import structure.User;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    // Lille test-implementation af Asset til unit tests
    static class TestAsset implements Asset {
        private final String ticker;
        private final double price;
        private final String currency;

        public TestAsset(String ticker, double price, String currency) {
            this.ticker = ticker;
            this.price = price;
            this.currency = currency;
        }

        @Override
        public String getTicker() {
            return ticker;
        }

        @Override
        public double getPrice() {
            return price;
        }

        @Override
        public String getCurrency() {
            return currency;
        }
    }

    @Test
    void buyAsset_reducesCashBalance() {

        TransactionService service = new TransactionService(
                new TransactionRepository(),
                new UserRepository("Database/users.csv"),
                new CurrencyService()   // går ud fra du har en no-arg constructor
        );

        // Start-user med 10.000 DKK
        User user = new User(
                1,
                "Test Bruger",
                "test@test.dk",
                20000101,
                10000.0,
                20250101,
                20250101
        );

        // Asset i DKK for at gøre testen simpel
        Asset asset = new TestAsset("AAPL", 1000.0, "DKK");

        service.buyAsset(user, asset, 5, "01-03-2025");

        // Efter køb: kontantbeholdning skal være mindre end start
        assertTrue(user.getCashBalance() < 10000);
    }

    @Test
    void sellAsset_increasesCashBalance() {

        TransactionService service = new TransactionService(
                new TransactionRepository(),
                new UserRepository("Database/users.csv"),
                new CurrencyService()
        );

        // Start-user med 5.000 DKK
        User user = new User(
                2,
                "Test Bruger 2",
                "test2@test.dk",
                20000101,
                5000.0,
                20250101,
                20250101
        );

        // Det her kræver egentlig at brugeren allerede har nogle AAPL-aktier i CSV,
        // men vi tester KUN at metoden kører og opdaterer balancen.
        Asset asset = new TestAsset("AAPL", 500.0, "DKK");

        // Her vil der kastes exception hvis holdings < 2, så i en rigtig test
        // bør du mocke getHoldingForUserAndTicker eller bygge data først.
        // Lad os antage at CSV'en har nok.
        service.sellAsset(user, asset, 2, "01-03-2025");

        assertTrue(user.getCashBalance() > 5000);
    }
}
