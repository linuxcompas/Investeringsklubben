package structure;

/**
 * INGEN MATEMATIK ELLER LOGIK
 * Klassen definerer kun, hvad en Transaction er.
 * Hver transaktion er en "kvittering" for en begivenhed.
 */
public class Transaction implements Comparable<Transaction> {

    private int id;
    private int userId;
    private int date;
    private String ticker;
    private double price;
    private String currency;
    private String orderType;
    private int quantity;

    public Transaction(int id,
                       int userId,
                       int date,
                       String ticker,
                       double price,
                       String currency,
                       String orderType,
                       int quantity) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.ticker = ticker;
        this.price = price;
        this.currency = currency;
        this.orderType = orderType;
        this.quantity = quantity;
    }



    // Getters
    public int getId() {return id;}
    public int getUserId() {return userId;}
    public int getDate() {return date;}
    public String getTicker() {return ticker;}
    public double getPrice() {return price;}
    public String getCurrency() {return currency;}
    public String getOrderType() {return orderType;}
    public int getQuantity() {return quantity;}

    // Setters
    public void setId(int id) {this.id = id;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setDate(int date) {this.date = date;}
    public void setTicker(String ticker) {this.ticker = ticker;}
    public void setPrice(double price) {this.price = price;}
    public void setCurrency(String currency) {this.currency = currency;}
    public void setOrderType(String orderType) {this.orderType = orderType;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * compareTo:
     * Sorterer først efter dato, og hvis datoen er ens, så efter id.
     */
    @Override
    public int compareTo(Transaction t) {
        int dateCompare = Integer.compare(this.date, t.date);
        if (dateCompare != 0) {
            return dateCompare;
        }
        return Integer.compare(this.id, t.id);
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ", ticker='" + ticker + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", orderType='" + orderType + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
