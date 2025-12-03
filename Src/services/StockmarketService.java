package services;
import repositories.StockmarketRepository;
import structure.*;
import java.util.*;

/* MATEMATIK / LOGIK
Her fortæller vi java - hvordan regner vi aktiemarkedet ud?
 */

public class StockmarketService {

        private StockmarketRepository stockRepo;
        private CurrencyService currencyService;

        //constructor
        public StockmarketService(StockmarketRepository stockRepo, CurrencyService currencyService) {
            this.stockRepo = stockRepo;
            this.currencyService = currencyService;
        }

        //Finder aktie via ticker
        public Stock getStockByTicker(String ticker) {
            if (ticker == null || ticker.isBlank()) return null;
            List<Stock> stocks = stockRepo.loadStockmarket();
            for (Stock s : stocks) {
                if (s.getTicker().equalsIgnoreCase(ticker.trim())) {
                    return s;
                }
            }
            return null;
        }

        //stocks i DKK værdi og henter data fra csv
        public List<Stock> getSortedStocksByValue() {
            List<Stock> stocks = stockRepo.loadStockmarket();
            Collections.sort(stocks, (a, b) -> {
                double aVal = getValueInDKK(a);
                double bVal = getValueInDKK(b);

                if (aVal < bVal) return 1;
                if (aVal > bVal) return -1;
                return 0;
            });
            return stocks;

        }
        //stocks pris i DKK
        public double getValueInDKK(Stock stock) {
            if (stock == null) return 0;
            double price = stock.getPrice();
            String currency = stock.getCurrency();
            //kun hvis aktien allerede er i DKK
            if (currency.equalsIgnoreCase("DKK")) {
                return price;
            }
            double rate = currencyService.getRate(currency);

            return price * rate;
        }

        // henter alle aktier
    public List<Stock> getAllStocks() {
        return stockRepo.loadStockmarket();
    }

}
