package structure;

import java.util.*;

public class Portfolio implements Comparable<Portfolio>{

     /*
    Her fortæller vi java:
    Hvad er et portfolio?

    Matematik her er begrænset KUN til at fortælle java:
    Hvad består portfolio af?

    Vi regner det ikke endnu.
     */

    private User owner;
    private Map<String, Integer> holdings;
    private double cashBalance;


    // constructor
    public Portfolio (User owner) {
        this.owner = owner;
        this.holdings = new HashMap<>();
        this.cashBalance = owner.getInitialCashDKK();
    }


    // getters
    public User getOwner() { return owner; }
    public Map<String, Integer> getHoldings() { return new  HashMap<>(holdings); }
    public double getCashBalance() { return cashBalance; }
    public int getQuantity(String ticker) {
        return holdings.getOrDefault(ticker, 0);}
    public Set<String> getTickers() { return holdings.keySet(); }


    // setters
    public void setHolding(String ticker, int quantity) {
        if (quantity > 0) {
            holdings.put(ticker, quantity);
        } else  {
            holdings.remove(ticker);}}
    public void setCashBalance(double cashBalance) { this.cashBalance = cashBalance; }


    // hjælpemetode -> tilføjer til portfolio ud fra ticker
    public void addToHolding(String ticker, int quantity) {
        holdings.put(ticker, holdings.getOrDefault(ticker, 0) + quantity);
    }

    public int compareTo(Portfolio o) { // til at fortælle hvordan vi sorterer ranglister
        return Integer.compare(this.owner.getId(), o.owner.getId());
    }

    @Override
    public String toString() {
    return "Portfolio {" +
    "owner=" + owner.getFullName() +
    ", holdings=" + holdings +
    ", cashBalance=" + cashBalance +
    "}";
    }
}

