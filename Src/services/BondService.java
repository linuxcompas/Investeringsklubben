package services;

import repositories.BondRepository;
import structure.Bond;
import java.util.List;

public class BondService {

    private final BondRepository bondRepo;
    private final CurrencyService currencyService;

    public BondService(BondRepository bondRepo, CurrencyService currencyService) {
        this.bondRepo = bondRepo;
        this.currencyService = currencyService;
    }


    // henter alle obligationer
    public List<Bond> getAllBonds() {
        return bondRepo.loadBonds();
    }


    // hent obligation efter ticker
    public Bond getBondByTicker(String ticker) {
        if (ticker == null || ticker.isBlank()) return null;

        for (Bond b : getAllBonds()) {
            if (b.getTicker().equalsIgnoreCase(ticker.trim())) {
                return b;
            }
        }
        return null;
    }

// hent pris i danske kroner
    public double getPriceInDKK(Bond bond) {
        if (bond.getCurrency().equalsIgnoreCase("DKK")) {
            return bond.getPrice();
        }
        return bond.getPrice() * currencyService.getRate(bond.getCurrency());
    }
}
