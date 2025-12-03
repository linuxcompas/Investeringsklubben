package controller;

import services.BondService;
import structure.Bond;

import java.util.List;

public class BondController {

    private final BondService bondService;

    public BondController(BondService bondService) {
        this.bondService = bondService;
    }

    public List<Bond> getAllBonds() {
        return bondService.getAllBonds();
    }

    public List<Bond> getBondsSortedByMaturityAsc() {
        return getAllBonds().stream()
                .sorted(Bond.sortByMaturityAsc)
                .toList();
    }

    public List<Bond> getBondsSortedByYieldDesc() {
        return getAllBonds().stream()
                .sorted(Bond.sortByYieldDesc)
                .toList();
    }

    public Bond getBondByTicker(String ticker) {
        return bondService.getBondByTicker(ticker);
    }

    public double getValueInDKK(Bond b) {
        return bondService.getPriceInDKK(b);
    }
}
