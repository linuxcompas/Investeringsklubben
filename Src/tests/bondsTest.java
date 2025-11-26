package tests;
import repositories.*;
import structure.Bond;
import java.util.*;

public class bondsTest {

    public static void main(String[] args) {

        BondRepository repo = new BondRepository();
        List<Bond> bonds = repo.loadBonds();

        Collections.sort(bonds);

        System.out.println("--------- Sorteret efter ticker ---------");
        for (Bond b : bonds) {
            System.out.println(b.getTicker() + " | " + b.getName() + " | " + b.getPrice() + " | " + b.getCouponRate());
        }

        Collections.sort(bonds, Comparator.comparing(Bond::getPrice));
        System.out.println("--------- Sorteret efter pris ---------");
        for (Bond b : bonds) {
            System.out.println(b.getTicker() + " | " + b.getName() + " | " + b.getPrice() + " | " + b.getCouponRate());
        }

        Collections.sort(bonds, Comparator.comparing(Bond::getCouponRate));
        System.out.println("--------- Sorteret efter afkast ---------");
        for (Bond b : bonds) {
            System.out.println(b.getTicker() + " | " + b.getName() + " | " + b.getPrice() + " | " + b.getCouponRate());
        }
    } // lets fucking goooo
}
