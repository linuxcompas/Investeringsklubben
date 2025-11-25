package structure;

import java.util.List;

public class Portfolio implements Comparable<Portfolio>{

     /*
    Her fortæller vi java:
    Hvad er et portfolio?

    Matematik her er begrænset KUN til at fortælle java:
    Hvad består portfolio af?

    Vi regner det ikke endnu.
     */

    List<Asset> assets;
    User owner;

    public Portfolio(List<Asset> assets, User owner) {

    }

    public void addAsset(Asset asset){

    }
    public void removeAsset(Asset asset){

    }

    public void getTotalValue(){

    }
    public void getGainLoss(){

    }

    public int compareTo(Portfolio o) { // til at fortælle hvordan vi sorterer ranglister
        return 0;
    }
}
