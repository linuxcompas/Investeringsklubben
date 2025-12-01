package services;
import repositories.*;
import structure.*;
import java.util.List;

/* MATEMATIK / LOGIK
Her fortæller vi java - hvordan regner vi valuta ud?
 */


public class CurrencyService {

    private CurrencyRepository currencyRepository;
    private List<Currency> currencies;

    public CurrencyService() {
        this.currencyRepository = new CurrencyRepository();
        this.currencies = currencyRepository.loadCurrency();
    }

    public double convertToDKK(double value, String currency) {
        double rate = getRate(currency);
        return value * rate;
    }

    public double getRate(String currency) {
        for (Currency c : currencies) {
            if (c.getBaseCurrency().equalsIgnoreCase(currency)) {
                return c.getRate();
            }
        }
        throw new IllegalArgumentException("Valuta findes ikke: " + currency);
    }

}


