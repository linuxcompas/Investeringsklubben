package controller;

import services.BondService;
import structure.Bond;

import java.util.List;

public class BondController {

    private final BondService bondService;

    public BondController(BondService bondService) {
        this.bondService = bondService;
    }


    // henter alle obligationer
    public List<Bond> getAllBonds() {
        return bondService.getAllBonds();
    }


    // henter obligationer sorteret efter udløbsdato
    public List<Bond> getBondsSortedByMaturityAsc() {
        return getAllBonds().stream()
                .sorted(Bond.sortByMaturityAsc)
                .toList();
    }


    // henter obligationer sorteret efter gevinst
    public List<Bond> getBondsSortedByYieldDesc() {
        return getAllBonds().stream()
                .sorted(Bond.sortByYieldDesc)
                .toList();
    }


    // henter obligationer ud fra ticker
    public Bond getBondByTicker(String ticker) {
        return bondService.getBondByTicker(ticker);
    }

    // henter værdi i danske kroner
    public double getValueInDKK(Bond b) {
        return bondService.getPriceInDKK(b);
    }
}
