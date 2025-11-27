package repositories;/* CSV I/O
Her fortæller vi java - hvor kommer data fra?
 */

import structure.Currency;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRepository {
    String file = "Database/currency.csv";
    public static void main(String[] args) {
        CurrencyRepository repository = new CurrencyRepository();
        List<Currency> currencies = repository.loadCurrency();
    }




    public List<Currency> loadCurrency(){
        List<Currency> currencies = new ArrayList<>();
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

                String[] parts = line.split(";");

                String baseCurrency = parts[0];
                String quoteCurrency = parts[1];
                double rate = Double.parseDouble(parts[2].replace(",", "."));
                int lastUpdated = Integer.parseInt(parts[3].replace("-", ""));

                // kalder vores constructor fra Currency
                Currency c = new Currency(
                        baseCurrency, quoteCurrency, rate, lastUpdated
                );
                currencies.add(c);


                String[] row = line.split(";");
                for (String index : row) {
                    System.out.printf("%-8s", index);
                }
                System.out.println();

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }


    // .csv OUT:
    public void saveCurrency(List<Currency> currencies) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (Currency c : currencies) {
                String line = String.join(";",
                        c.getBaseCurrency(),
                        c.getQuoteCurrency(),
                        String.valueOf(c.getRate()),
                        String.valueOf(c.getLastUpdated())
                );

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



