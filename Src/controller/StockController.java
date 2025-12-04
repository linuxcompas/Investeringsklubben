package controller;

import services.StockmarketService;
import structure.Stock;
import structure.User;

import java.util.*;

public class StockController {

    private final StockmarketService stockService;



    public StockController(StockmarketService stockService) {
        this.stockService = stockService;
    }


    // henter aktier fra ticker
    public Stock getStockByTicker(String ticker) {
        return stockService.getStockByTicker(ticker);
    }


    // henter alle aktier
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }


    // henter aktier ud far værdi faldende
    public List<Stock> getStocksSortedByValueDesc() {
        return stockService.getSortedStocksByValue();
    }


    // henter aktier efter efter pris stigende
    public List<Stock> getStocksSortedByPriceAsc() {
        return stockService.getAllStocks()
                .stream()
                .sorted(Comparator.comparingDouble(Stock::getPrice))
                .toList();
    }


    // henter aktier efter pris faldende
    public List<Stock> getStocksSortedByPriceDesc() {
        return stockService.getAllStocks()
                .stream()
                .sorted(Comparator.comparingDouble(Stock::getPrice).reversed())
                .toList();
    }

    // henter sektorfordeling
    public Map<String, Integer> getSectorDistribution(User user, PortfolioController portfolioController) {

        Map<String, Object> portfolio = portfolioController.getPortfolio(user);
        Map<String, Integer> holdings = (Map<String, Integer>) portfolio.get("holdings");

        List<Stock> stocks = stockService.getAllStocks();
        Map<String, Integer> sectors = new HashMap<>();

        for (String ticker : holdings.keySet()) {
            int qty = holdings.get(ticker);

            for (Stock s : stocks) {
                if (s.getTicker().equalsIgnoreCase(ticker)) {
                    String sector = s.getSector();
                    sectors.put(sector, sectors.getOrDefault(sector, 0) + qty);
                }
            }
        }

        return sectors;
    }


    // henter værdi i danske kroner
    public double getValueInDKK(Stock stock) {
        return stockService.getValueInDKK(stock);
    }
}
