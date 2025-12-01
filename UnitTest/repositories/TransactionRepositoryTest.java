package repositories;

import org.junit.jupiter.api.Test;
import structure.Transaction;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {

    @Test
    void loadTransactions() {
        TransactionRepository repo = new TransactionRepository();

        List<Transaction> transactions = repo.loadTransactions();

        assertNotNull(transactions);
    }

    @Test
    void getTransactionByUserId() {
        TransactionRepository repo = new TransactionRepository();

        List<Transaction> userTransactions = repo.getTransactionByUserId(1);

        assertNotNull(userTransactions);
    }

    @Test
    void saveTransactions() {
        TransactionRepository repo = new TransactionRepository();
        List<Transaction> transactions = repo.loadTransactions();

        repo.saveTransactions(transactions);

        List<Transaction> reloaded = repo.loadTransactions();
        assertEquals(transactions.size(), reloaded.size());
    }
}