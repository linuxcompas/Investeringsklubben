package structure;

public class Stock implements Asset, Comparable<Stock>{

    /* INGEN MATEMATIK ELLER LOGIK -
   Her fortæller vi java:
   Hvad er en stock?

   Vi skal ikke have nogen logik herinde.
    */

    String ticker;
    String name;
    String sector;
    double price;
    String currency;
    String rating;
    double dividendYield;
    String market;
    int lastUpdated;

    public void getTicker() {

    }
    public void getPrice() {

    }

    public void getCurrency() {
    }

    public void getName() {
    }
    public void getRate() {
    }


    public int compareTo(Stock o) {
        return 0;
    }
}
