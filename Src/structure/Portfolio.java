    package structure;

    import java.util.*;
    import services.*;

    public class Portfolio implements Comparable<Portfolio>{

         /*
        Her fortæller vi java:
        Hvad er et portfolio?

        Matematik her er begrænset KUN til at fortælle java:
        Hvad består portfolio af?

        Vi regner det ikke endnu.
         */

        private final User owner;
        private Map<String, Integer> holdings = new HashMap<>();
        private double cashBalance;

        // constructor
        public Portfolio (User owner) {
            this.owner = owner;
            this.cashBalance = owner.getInitialCashDKK();
            this.holdings = new HashMap<>();
        }


        // getters
        public User getOwner() { return owner; }
        public double getCashBalance() { return cashBalance; }
        public Map<String, Integer> getHolding() {return holdings;}
        public int getUserId() { return owner.getId(); }
        public String getEmail() { return owner.getEmail(); }



        public void setCashBalance(double cashBalance) { this.cashBalance = cashBalance; }

        // hjælpemetode -> tilføjer til portfolio ud fra ticker
        public int getHoldingForTicker(String ticker, List<Transaction> transactions) {

            int total = 0;

                    for (Transaction t : transactions) {
                        if (t.getUserId() != owner.getId()) continue;
                        if (!t.getTicker().equalsIgnoreCase(ticker)) continue;

                        switch (t.getOrderType().toLowerCase()) {
                            case "buy" -> total += t.getQuantity();
                            case "sell" -> total -= t.getQuantity();
                        }
                    }
                    return total;
        }

        public Map<String, Integer> getAllHoldings(List<Transaction> transactions){

            Map<String, Integer> holdings = new HashMap<>();

            for (Transaction t: transactions) {
                if (t.getUserId() != owner.getId()) continue;

                int qty = holdings.getOrDefault(t.getTicker(), 0);

                if (t.getOrderType().equalsIgnoreCase("buy")) {
                    qty += t.getQuantity();
                } else if (t.getOrderType().equalsIgnoreCase("sell")) {
                    qty -= t.getQuantity();
                }

                if (qty > 0) holdings.put(t.getTicker(), qty);
                else holdings.remove(t.getTicker());
            }
            return holdings;
        }

        public double getCashBalance(List<Transaction> transactions, CurrencyService currencyService){

            double balance = owner.getInitialCashDKK();

            for (Transaction t : transactions) {
                if (t.getUserId() != owner.getId()) continue;

                // samlet pris i gældende valuta
                double total = t.getPrice() * t.getQuantity();

                // omregner til dkk
                double totalDKK = currencyService.convertToDKK(total, t.getCurrency());

                if (t.getOrderType().equalsIgnoreCase("buy")) {
                    balance -= totalDKK;
                } else if (t.getOrderType().equalsIgnoreCase("sell")) {
                    balance += totalDKK;
                }
            }

            return balance;
        }

        public void addToHolding(String ticker, int amount){
            int current = holdings.getOrDefault(ticker, 0);
            holdings.put(ticker, current+amount);
        }



        public int compareTo(Portfolio o) { // til at fortælle hvordan vi sorterer ranglister
            return Integer.compare(this.owner.getId(), o.owner.getId());
        }

        @Override
        public String toString() {
        return "Portfolio {" +
        "owner=" + owner.getFullName() +
        ", holdings="  +
        ", cashBalance="  +
        "}";
        }
    }

