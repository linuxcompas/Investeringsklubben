package structure;
import java.util.*;


public class Bond implements Asset, Comparable<Bond> {

    /* INGEN MATEMATIK ELLER LOGIK -
   Her fortæller vi java:
   Hvad er et bond?

   Vi skal ikke have nogen logik herinde.
    */

    private String ticker;
    private String name;
    private double price;
    private String currency;
    private double couponRate;
    private int issueDate;
    private int maturityDate;
    private String rating;
    private String market;
    private int lastUpdated;


    // constructor
    public Bond(String ticker, String name, double price, String currency, double couponRate,
                int issueDate, int maturityDate, String rating, String market, int lastUpdated) {
        this.ticker = ticker;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.couponRate = couponRate;
        this.issueDate = issueDate;
        this.maturityDate = maturityDate;
        this.rating = rating;
        this.market = market;
        this.lastUpdated = lastUpdated;

    }

    // getters
    public String getTicker() {return ticker;}
    public String getName(){return name;}
    public double getPrice(){return price;}
    public String getCurrency(){return currency;}
    public double getCouponRate(){return couponRate;}
    public int getIssueDate(){return issueDate;}
    public int getMaturityDate(){return maturityDate;}
    public String getRating(){return rating;}
    public String getMarket(){return market;}
    public int getLastUpdated(){return lastUpdated;}


    // setters
    public void setTicker(String ticker){this.ticker = ticker;}
    public void setName(String name){this.name = name;}
    public void setPrice(double price){this.price = price;}
    public void setCurrency(String currency){this.currency = currency;}
    public void setCouponRate(double couponRate){this.couponRate = couponRate;}
    public void setIssueDate(int issueDate){this.issueDate = issueDate;}
    public void setMaturityDate(int maturityDate){this.maturityDate = maturityDate;}
    public void setRating(String rating){this.rating = rating;}
    public void setMarket(String market){this.market = market;}
    public void setLastUpdated(int lastUpdated){this.lastUpdated = lastUpdated;}

    /*public String toString() {
        return;
    }
    */


    // comparator = default er ticker alfabetisk
    @Override
    public int compareTo(Bond o) {
        return this.ticker.compareTo(o.ticker);
    }

    // pris stigende
    public static Comparator<Bond> sortByPriceAsc =
        Comparator.comparingDouble(Bond::getPrice);

    // pris faldende
    public static Comparator<Bond> sortByPriceDesc =
            Comparator.comparingDouble(Bond::getPrice).reversed();

    // ticker alfabetisk
    public static Comparator<Bond> sortByTickerAlphabetic =
            Comparator.comparing(Bond::getTicker);

    // afkast stigende
    public static Comparator<Bond> sortByYieldAsc =
            Comparator.comparingDouble(Bond::getCouponRate);

    public static Comparator<Bond> sortByMaturityAsc =
    Comparator.comparingDouble(Bond::getMaturityDate);

    // afkast faldende
    public static Comparator<Bond> sortByYieldDesc =
            Comparator.comparingDouble(Bond::getCouponRate).reversed();


}
