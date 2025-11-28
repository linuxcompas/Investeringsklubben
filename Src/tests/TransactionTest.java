package tests;
import repositories.TransactionRepository;
import structure.Stock;
import structure.Transaction;

import java.util.*;

public class TransactionTest {
    public static void main(String[] args) {

        TransactionRepository repo = new TransactionRepository();
        List<Transaction> transactions = repo.loadTransactions();

        Collections.sort(transactions);

        System.out.println("--------- Sorteret efter ticker ---------");
        for (Transaction t : transactions) {
            System.out.println(t.getTicker() + " | " + t.getUserId() + " | " + t.getPrice() + " | " + t.getQuantity());
        }

        Collections.sort(transactions, Comparator.comparing(Transaction::getPrice));
        System.out.println("--------- Sorteret efter pris ---------");
        for (Transaction t : transactions) {
            System.out.println(t.getTicker() + " | " + t.getUserId() + " | " + t.getPrice() + " | " + t.getQuantity());
        }

        Collections.sort(transactions, Comparator.comparing(Transaction::getQuantity));
        System.out.println("--------- Sorteret efter afkast ---------");
        for (Transaction t : transactions) {
            System.out.println(t.getTicker() + " | " + t.getUserId() + " | " + t.getPrice() + " | " + t.getQuantity());
        }
    }
}
