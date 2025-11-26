package structure;

import java.util.*;

public class Stock implements Asset, Comparable<Stock>{

    /* INGEN MATEMATIK ELLER LOGIK -
   Her fortæller vi java:
   Hvad er en stock?

   Vi skal ikke have nogen logik herinde.
    */

    private String ticker;
    private String name;
    private String sector;
    private double price;
    private String currency;
    private String rating;
    private double dividendYield;
    private String market;
    private int lastUpdated;

    // constructor
    public Stock(String ticker, String name, String sector, double price, String currency,
                 String rating, double dividendYield, String market, int lastUpdated) {

        this.ticker = ticker;
        this.name = name;
        this.sector = sector;
        this.price = price;
        this.currency = currency;
        this.rating = rating;
        this.dividendYield = dividendYield;
        this.market = market;
        this.lastUpdated = lastUpdated;
    }

    // getters
    public String getTicker(){ return ticker; }
    public String getName(){ return name; }
    public String getSector(){ return sector; }
    public double getPrice(){ return price; }
    public String getCurrency(){ return currency; }
    public String getRating(){ return rating; }
    public double getDividendYield(){ return dividendYield; }
    public String getMarket(){ return market; }
    public int getLastUpdated(){ return lastUpdated; }

    // setters
    public void setTicker(String ticker){ this.ticker = ticker; }
    public void setName(String name){ this.name = name; }
    public void setSector(String sector){ this.sector = sector; }
    public void setPrice(double price){ this.price = price; }
    public void setCurrency(String currency){ this.currency = currency; }
    public void setRating(String rating){ this.rating = rating; }
    public void setDividendYield(double dividendYield){ this.dividendYield = dividendYield; }
    public void setMarket(String market){ this.market = market; }
    public void setLastUpdated(int lastUpdated){ this.lastUpdated = lastUpdated; }


    // comparator. default = ticker alfabetisk
    @Override
    public int compareTo(Stock o) {
        return this.ticker.compareTo(o.ticker);
    }

    // pris stigende
    public static Comparator<Stock> sortByPriceAsc =
            Comparator.comparingDouble(Stock::getPrice);

    // pris faldende
    public static Comparator<Stock> sortByPriceDesc =
            Comparator.comparingDouble(Stock::getPrice).reversed();

    // ticker alfabetisk
    public static Comparator<Stock> sortByTickerAlphabetic =
            Comparator.comparing(Stock::getTicker);

    // afkast stigende
    public static Comparator<Stock> sortByYieldAsc =
            Comparator.comparingDouble(Stock::getDividendYield);

    // afkast faldende
    public static Comparator<Stock> sortByYieldDesc =
            Comparator.comparingDouble(Stock::getDividendYield).reversed();



}
