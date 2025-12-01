package structure;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void getTicker() {
        Transaction transaction = new Transaction(1, 100, 20240315, "AAPL", 150.50, "USD", "buy", 10);

        assertEquals("AAPL", transaction.getTicker());
    }

    @Test
    void getCurrency() {
        Transaction transaction = new Transaction(2, 200, 20240320, "NOVO-B", 450.75, "DKK", "buy", 5);

        assertEquals("DKK", transaction.getCurrency());
    }

    @Test
    void getOrderType() {
        Transaction transaction = new Transaction(3, 150, 20240325, "TSLA", 250.00, "USD", "sell", 20);

        assertEquals("sell", transaction.getOrderType());
    }
}

// hej