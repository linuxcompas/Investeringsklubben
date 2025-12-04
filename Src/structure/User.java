package structure;

public class User implements Comparable<User>{

     /* INGEN MATEMATIK ELLER LOGIK -
    Her fortæller vi java:
    Hvad er en user?

    Vi skal ikke have nogen logik herinde.
     */
//
    private int id; // kommentar, har ændret first/last name til FullName*
    private String fullName;
    private String email;
    private int birthDate;
    private double initialCashDKK;
    private int createdAt;
    private int lastUpdated;

    public User (int id, String fullName, String email, int birthDate, double initialCashDKK, int createdAt, int lastUpdated) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.initialCashDKK = initialCashDKK;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    // getters
    public int getId() { return id; }
   public String getFullName() { return fullName; }
   public String getEmail() { return email; }
   public int getBirthDate() { return birthDate; }
   public double getInitialCashDKK() { return initialCashDKK; }
   public int getCreatedAt() { return createdAt; }
   public int getLastUpdated() { return lastUpdated; }

   // setters
    public void setId(int id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setBirthDate(int birthDate) { this.birthDate = birthDate; }
    public void setLastUpdated(int lastUpdated) { this.lastUpdated = lastUpdated; }

       public int compareTo(User o) {
        return Integer.compare(this.id, o.id);
    }
      @Override
public String toString() {
          return id + ";" + fullName + ";" + email + ";" + birthDate + ";" + initialCashDKK + ";" + createdAt + ";" + lastUpdated;
      }
 }
