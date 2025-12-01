package controller;

import services.*;
import structure.*;

import java.util.*;

public class StockController {
    private final StockmarketService stockmarketService;

    public StockController(StockmarketService stockmarketService) {
        this.stockmarketService = stockmarketService;
    }

    public Stock getStockByTicker(String ticker) {
        return stockmarketService.getStockByTicker(ticker);
    }

    public List<Stock> getStockMarketOverview() {
        return stockmarketService.getSortedStocksByValue();
    }
}
