package structure;

public class Bond implements Asset, Comparable<Bond> {

    /* INGEN MATEMATIK ELLER LOGIK -
   Her fortæller vi java:
   Hvad er et bond?

   Vi skal ikke have nogen logik herinde.
    */

    String ticker;
    String name;
    double price;
    String currency;
    double couponRate;
    int issueDate;
    int maturityDate;
    String rating;
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

    public int compareTo(Bond o) {
        return 0;
    }
}
