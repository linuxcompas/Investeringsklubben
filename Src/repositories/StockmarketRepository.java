package repositories;
import structure.*;
import java.io.*;
import java.util.*;

/* CSV I/O
Her fortæller vi java - hvor kommer data fra?
 */

public class StockmarketRepository {


    // .csv IN:
    public List<Stock> loadStockmarket() {
        List<Stock> stocks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Database/stockMarket.csv"))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                String ticker = parts[0];
                String name = parts[1];
                String sector = parts[2];
                double price = Double.parseDouble(parts[3].replace(",", "."));
                String currency = parts[4];
                String rating = parts[5];
                double dividendYield = Double.parseDouble(parts[6].replace(",", "."));
                String market = parts[7];
                int lastUpdated = Integer.parseInt(parts[8].replace("-", ""));

                // kalder vores constructor fra Stock
                Stock s = new Stock(
                        ticker, name, sector, price, currency, rating,
                        dividendYield, market, lastUpdated
                );
                stocks.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    // .csv OUT:
    public void saveStocks(List<Stock> stocks) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Database/stockmarket.csv"))) {

            for (Stock s : stocks) {
                String line = String.join(";",
                        s.getTicker(),
                        s.getName(),
                        s.getSector(),
                        String.valueOf(s.getPrice()),
                        s.getCurrency(),
                        s.getRating(),
                        String.valueOf(s.getDividendYield()),
                        s.getMarket(),
                        String.valueOf(s.getLastUpdated())
                );

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
