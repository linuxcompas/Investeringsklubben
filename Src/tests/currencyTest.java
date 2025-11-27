package tests;

import repositories.CurrencyRepository;
import structure.Currency;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class currencyTest {
    public static void main(String[] args) {

        CurrencyRepository repo = new CurrencyRepository();
        List<Currency> currencies = repo.loadCurrency();

        Collections.sort(currencies);

        System.out.println("--------- Sorteret efter Base Currency ---------");
        for (Currency c : currencies) {
            System.out.println(c.getBaseCurrency() + " | " + c.getQuoteCurrency() + " | " + c.getRate() + " | " + c.getLastUpdated());
        }

       /* Collections.sort(currencies, Comparator.comparing(Currency::getLastUpdated));
        System.out.println("--------- Sorteret efter dato ---------");
        for (Currency c : currencies) {
        System.out.println(c.getBaseCurrency() + " | " + c.getQuoteCurrency() + " | " + c.getRate() + " | " + c.getLastUpdated());
    }
*/
        Collections.sort(currencies, Comparator.comparing(Currency::getRate));
        System.out.println("--------- Sorteret efter rate ---------");
        for (Currency c : currencies) {
            System.out.println(c.getBaseCurrency() + " | " + c.getQuoteCurrency() + " | " + c.getRate() + " | " + c.getLastUpdated());
        }
    }
}
