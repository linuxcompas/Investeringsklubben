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

    public Stock getStockByTicker(String ticker) {
        return stockService.getStockByTicker(ticker);
    }

    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    public List<Stock> getStocksSortedByValueDesc() {
        return stockService.getSortedStocksByValue();
    }

    public List<Stock> getStocksSortedByPriceAsc() {
        return stockService.getAllStocks()
                .stream()
                .sorted(Comparator.comparingDouble(Stock::getPrice))
                .toList();
    }

    public List<Stock> getStocksSortedByPriceDesc() {
        return stockService.getAllStocks()
                .stream()
                .sorted(Comparator.comparingDouble(Stock::getPrice).reversed())
                .toList();
    }
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

    public double getValueInDKK(Stock stock) {
        return stockService.getValueInDKK(stock);
    }
}
