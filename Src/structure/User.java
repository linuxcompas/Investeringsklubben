package structure;

public class User implements Comparable<User>{

     /* INGEN MATEMATIK ELLER LOGIK -
    Her fortæller vi java:
    Hvad er en user?

    Vi skal ikke have nogen logik herinde.
     */
//
    public int id; // kommentar, har ændret first/last name til FullName*
    public String fullName;
    public String email;
    public int birthDate;
    public double initialCashDKK;
    public int createdAt;
    public int lastUpdated;
    public int cashBalance;

    public User (int id, int cashBalance, String fullName, String email, int birthDate, double initialCashDKK, int createdAt, int lastUpdated) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.initialCashDKK = initialCashDKK;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.cashBalance = cashBalance;
    }
    // har slettet void under vores getter, da getters aldrig skal stå i void siger google.
    public int getId() {
        return id;
    }
   public String getFullName() {
       return fullName;
   }
   public String getEmail() {
       return email;
   }
   public int getBirthDate() {
        return birthDate;
   }
   public double getInitialCashDKK() {
       return initialCashDKK;
   }
   public int getCreatedAt() {
       return createdAt;
   }
   public int getLastUpdated() {
        return lastUpdated;
   }
   public int getCashBalance() {
        return cashBalance;
   }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public void setInitialCashDKK(double initialCashDKK) {
        this.initialCashDKK = initialCashDKK;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setCashBalance(int cashBalance) {
        this.cashBalance = cashBalance;
    }
       public int compareTo(User o) {
        return Integer.compare(this.id, o.id);
    }
      @Override
public String toString() {
          return id + ";" + fullName + ";" + email + ";" + birthDate + ";" + initialCashDKK + ";" + createdAt + ";" + lastUpdated;
      }
 }
