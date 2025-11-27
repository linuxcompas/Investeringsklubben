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

    public User (int id, String fullName, String email, int birthDate, double initialCashDKK, int createdAt, int lastUpdated) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.initialCashDKK = initialCashDKK;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
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

   //Setter: Opdatere datoen for hvornår brugeren sidst blev ændret i systemet, så hvergang der bliver foretaget en handling så bliver den nye dato gemt i users.csv
    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

       public int compareTo(User o) {
        return Integer.compare(this.id, o.id);
    }
      @Override
public String toString() {
          return id + ";" + fullName + ";" + email + ";" + birthDate + ";" + initialCashDKK + ";" + createdAt + ";" + lastUpdated;
      }
 }
