package tests;
import repositories.StockmarketRepository;
import structure.Stock;
import java.util.*;

public class stockTest {
    public static void main(String[] args) {

        StockmarketRepository repo = new StockmarketRepository();
        List<Stock> stocks = repo.loadStockmarket();

        Collections.sort(stocks);

        System.out.println("--------- Sorteret efter ticker ---------");
        for (Stock s : stocks) {
            System.out.println(s.getTicker() + " | " + s.getName() + " | " + s.getPrice() + " | " + s.getDividendYield());
        }

        Collections.sort(stocks, Comparator.comparing(Stock::getPrice));
        System.out.println("--------- Sorteret efter pris ---------");
        for (Stock s : stocks) {
            System.out.println(s.getTicker() + " | " + s.getName() + " | " + s.getPrice() + " | " + s.getDividendYield());
        }

        Collections.sort(stocks, Comparator.comparing(Stock::getDividendYield));
        System.out.println("--------- Sorteret efter afkast ---------");
        for (Stock s : stocks) {
            System.out.println(s.getTicker() + " | " + s.getName() + " | " + s.getPrice() + " | " + s.getDividendYield());
        }
    }
}
