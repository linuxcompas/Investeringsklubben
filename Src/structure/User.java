package structure;

public class User implements Comparable<User>{

     /* INGEN MATEMATIK ELLER LOGIK -
    Her fortæller vi java:
    Hvad er en user?

    Vi skal ikke have nogen logik herinde.
     */

    static int ID; // kommentar
    String firstName;
    String lastName;
    String email;
    int birthDate;
    double initialCashDKK;
    int createdAt;
    int lastUpdated;

    public static void getID() {
    }
    public void getFirstName() {
    }
    public void getLastName() {
    }

    public void getTotalWealth(){

    }


    public int compareTo(User o) {
        return 0;
    }
}
