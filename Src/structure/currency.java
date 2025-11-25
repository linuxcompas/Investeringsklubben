package structure;

public class currency implements Comparable<currency>{

    /* INGEN MATEMATIK ELLER LOGIK -
       Her fortæller vi java:
       Hvad er en currency?

       Vi skal ikke have nogen logik herinde.
        */

    String baseCurrency;
    String quoteCurrency;
    double rate;
    int lastUpdated;

    public int compareTo(currency o) {
        return 0;
    }

}
