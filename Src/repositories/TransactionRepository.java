package repositories;

import structure.Transaction;
import java.io.*;
import java.util.*;


public class TransactionRepository {

    // .csv IN:
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Database/transactions.csv"))) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                int id = Integer.parseInt(parts[0]);
                int userId = Integer.parseInt(parts[1]);
                String dateString = parts[2].replace("-", "");
                int date = Integer.parseInt(dateString);
                String ticker = parts[3];
                double price = Double.parseDouble(parts[4].replace(",", "."));
                String currency = parts[5];
                String orderType = parts[6];
                int quantity = Integer.parseInt(parts[7]);

                Transaction t = new Transaction(
                        id,
                        userId,
                        date,
                        ticker,
                        price,
                        currency,
                        orderType,
                        quantity
                );


                transactions.add(t);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<Transaction> getTransactionByUserId(int userId) {
        List<Transaction> allTransactions = loadTransactions();
        List<Transaction> userTransactions = new ArrayList<>();

        for (Transaction t : allTransactions) {
            if (t.getUserId() == userId) {
                userTransactions.add(t);
            }
        }
        return userTransactions;
    }

    public void saveTransactions(List<Transaction> transactions) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Database/transactions.csv"))) {


            bw.write("id;user_id;date;ticker;price;currency;order_type;quantity");
            bw.newLine();

            for (Transaction t : transactions) {
                String dateStr = String.valueOf(t.getDate());
                while (dateStr.length() < 8) {
                    dateStr = "0" + dateStr;
                }
                String formattedDate = dateStr.substring(0, 2) + "-" +
                        dateStr.substring(2, 4) + "-" +
                        dateStr.substring(4);


                String priceText = String.valueOf(t.getPrice()).replace(".", ",");

                String line = String.join(";",
                        String.valueOf(t.getId()),
                        String.valueOf(t.getUserId()),
                        String.valueOf(t.getDate()),
                        t.getTicker(),
                        priceText,
                        t.getCurrency(),
                        t.getOrderType(),
                        String.valueOf(t.getQuantity())
                );

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
