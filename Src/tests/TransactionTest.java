package tests;

import repositories.TransactionRepository;
import structure.Transaction;

import java.util.*;

public class TransactionTest {
    public static void main(String[] args) {

        TransactionRepository repo = new TransactionRepository();
        List<Transaction> transactions = repo.loadTransactions();

        // Naturlig sortering (compareTo) -> dato
        Collections.sort(transactions);
        System.out.println("--------- Sorteret efter dato (compareTo) ---------");
        for (Transaction t : transactions) {
            System.out.println(t.getDate() + " | " + t.getTicker() + " | " + t.getUserId());
        }

        // Sortering efter pris
        Collections.sort(transactions, Comparator.comparing(Transaction::getPrice));
        System.out.println("--------- Sorteret efter pris ---------");
        for (Transaction t : transactions) {
            System.out.println(t.getPrice() + " | " + t.getTicker() + " | " + t.getUserId());
        }

        // Sortering efter quantity (antal)
        Collections.sort(transactions, Comparator.comparing(Transaction::getQuantity));
        System.out.println("--------- Sorteret efter quantity ---------");
        for (Transaction t : transactions) {
            System.out.println(t.getQuantity() + " | " + t.getTicker() + " | " + t.getUserId());
        }
    }
}
