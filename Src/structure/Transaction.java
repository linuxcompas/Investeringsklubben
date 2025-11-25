package structure;

public class Transaction implements Comparable<Transaction>{


    /* INGEN MATEMATIK ELLER LOGIK -
    Her fortæller vi java:
    Hvad er en transaction?

    Vi skal ikke have nogen logik herinde.
     */

    int userID = User.ID; // abstraction -> vi skal ikke kalde på User class i alle transaktioner
    int transactionID;
    int transactionDate;
    String ticker;
    double price;
    String currency;
    String orderType; // buy/sell
    int quantity;

    public void getuserID(){
    }
    public void gettransactionID(){
    }
    public void gettransactionDate(){
    }



    public int compareTo(Transaction o) {
        return 0;
    }
}
