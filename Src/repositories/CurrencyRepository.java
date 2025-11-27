package repositories;/* CSV I/O
Her fortæller vi java - hvor kommer data fra?
 */

import java.io.*;


public class CurrencyRepository {

    public static void main(String[] args) {
        String file = "Database/currency.csv";
        readFromFile(file);}

        // Læs tekst fra en fil og returnér som String
        public static String readFromFile (String file){
            String line = "";
            StringBuilder sb = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                boolean isFirstLine = true;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }

                    String[] row = line.split(";");
                    for (String index : row) {
                        System.out.printf("%-8s", index);
                        sb.append(index).append(" ");
                    }
                    System.out.println();
                    sb.append("\n");
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return sb.toString();

        }


    }



