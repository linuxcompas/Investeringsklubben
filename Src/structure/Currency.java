package structure;

import java.util.Comparator;

public class Currency implements Comparable<Currency>{

    /* INGEN MATEMATIK ELLER LOGIK -
       Her fortæller vi java:
       Hvad er en Currency?

       Vi skal ikke have nogen logik herinde.
        */

    private String baseCurrency;
    private String quoteCurrency;
    private double rate;
    private int lastUpdated;

    //constructor
    public Currency(String baseCurrency, String quoteCurrency, double rate, int lastUpdated) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.rate = rate;
        this.lastUpdated = lastUpdated;
    }

    //Getters
    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public double getRate() {
        return rate;
    }

    public int getLastUpdated() {
        return lastUpdated;
    }

    //Setters
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /*public String toString() {
        return;
    }
    */

    // Compare currencies by base currency
    @Override
    public int compareTo(Currency o) {
        return this.baseCurrency.compareTo(o.baseCurrency);
    }

    // last updated "Oldest to Newest"
    public static Comparator<Currency> sortByLastUpdateAsc =
            Comparator.comparingInt(Currency::getLastUpdated);

    // last updated "Newest to Oldest "
    public static Comparator<Currency> sortByLastUpdateDesc =
            Comparator.comparingInt(Currency::getLastUpdated).reversed();

    // Base currency alphabetical
    public static Comparator<Currency> sortByBaseAlphabetic =
            Comparator.comparing(Currency::getBaseCurrency);

    // Rate Low to High
    public static Comparator<Currency> sortByRatedAsc =
            Comparator.comparingDouble(Currency::getRate);

    // Rate high to low
    public static Comparator<Currency> sortByRateDesc =
            Comparator.comparingDouble(Currency::getRate).reversed();
}
