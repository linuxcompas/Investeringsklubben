package structure;

public class Currency implements Comparable<Currency>{

    /* INGEN MATEMATIK ELLER LOGIK -
       Her fortæller vi java:
       Hvad er en Currency?

       Vi skal ikke have nogen logik herinde.
        */

    String baseCurrency;
    String quoteCurrency;
    double rate;
    int lastUpdated;

    public int compareTo(Currency o) {
        return 0;
    }

}
