package repositories;
import structure.Bond;
import java.io.*;
import java.util.*;

/* CSV I/O
Her fortæller vi java - hvor kommer data fra?
 */

public class BondRepository {


    // .csv IN:
    public List<Bond> loadBonds() {
        List<Bond> bonds = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Database/bondMarket.csv"))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                String ticker = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2].replace(",", "."));
                String currency =  parts[3];
                double couponRate = Double.parseDouble(parts[4].replace(",", "."));
                int issueDate =  Integer.parseInt(parts[5].replace("-", ""));
                int maturityDate = Integer.parseInt(parts[6].replace("-", ""));
                String rating = parts[7];
                String market = parts[8];
                int lastUpdated = Integer.parseInt(parts[9].replace("-", ""));

                Bond b = new Bond(
                        ticker, name, price, currency, couponRate, issueDate,
                        maturityDate, rating, market, lastUpdated
                );
                bonds.add(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return bonds;
    }

    public void saveBonds(List<Bond> bonds) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Database/bondMarket.csv"))) {

            for (Bond b : bonds) {
                String line = String.join(";",
                        b.getTicker(),
                        b.getName(),
                        String.valueOf(b.getPrice()),
                        b.getCurrency(),
                        String.valueOf(b.getCouponRate()),
                        String.valueOf(b.getIssueDate()),
                        String.valueOf(b.getMaturityDate()),
                        b.getRating(),
                        b.getMarket(),
                        String.valueOf(b.getLastUpdated())
                );

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
