package controller;

import services.*;
import structure.*;
import java.util.*;


public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    // Køb af aktier
    public void buy(User user, Asset asset, int qty) {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (asset == null) throw new IllegalArgumentException("Asset is null");
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");

        transactionService.buyAsset(user, asset, qty);
    }

    // Salg af aktier
    public void sell(User user, Asset asset, int qty) {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (asset == null) throw new IllegalArgumentException("Asset is null");
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");

        transactionService.sellAsset(user, asset, qty);
    }

    // Vis transaktionshistorik
    public List<Transaction> getTransactions(User user) {
        if (user == null) throw new IllegalArgumentException("User is null");
        return transactionService.getTransactionsByUser(user.getId());
    }

}
