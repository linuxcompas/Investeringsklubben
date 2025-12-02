package menu;

package util;

import structure.Stock;
import java.util.List;

public class SimpleAsciiFormatter {
    
    public static void print(List<Stock> stocks) {
        if (stocks == null || stocks.isEmpty()) {
            System.out.println("No stocks available.");
            return;
        }

        String header = String.format("| %-6s | %-20s | %-10s | %8s | %6s | %6s | %5s |",
                "Ticker", "Name", "Sector", "Price", "Cur", "Rating", "Div%");
        String border = new String(new char[header.length()]).replace('\0', '-');

        System.out.println(border);
        System.out.println(header);
        System.out.println(border);

        for (Stock s : stocks) {
            String name = safe(s.getName(), 20);
            String sector = safe(s.getSector(), 10);
            String ticker = safe(s.getTicker(), 6);
            String currency = safe(s.getCurrency(), 6);
            String rating = safe(s.getRating(), 6);
            double price = safeDouble(s.getPrice());
            double div = safeDouble(s.getDividendYield());

            System.out.printf("| %-6s | %-20s | %-10s | %8.2f | %6s | %-6s | %5.1f |%n",
                    ticker, name, sector, price, currency, rating, div);
        }

        System.out.println(border);
        System.out.printf("Total: %d stocks%n", stocks.size());
    }

    private static String safe(String v, int maxLen) {
        if (v == null) return "";
        if (v.length() <= maxLen) return v;
        return v.substring(0, maxLen - 2) + "..";
    }

    private static double safeDouble(Double d) {
        if (d == null) return 0.0;
        return d;
}
//hej aa
