package repositories;

import structure.Transaction;
import structure.User;
import java.io.*;
import java.util.*;

/* CSV I/O
Her fortæller vi java - hvor kommer data fra?
 */

public class UserRepository {

    private final String filePath;

    public UserRepository(String filePath) {
        this.filePath = filePath;
    }
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Database/users.csv"))) {

            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";");

                int id = Integer.parseInt(parts[0]);
                String fullName = parts[1];
                String email = parts[2];
                int birthDate = Integer.parseInt(parts[3].replace("-", ""));
                double initialCash = Double.parseDouble(parts[4].replace("-", ""));
                int createdAt = Integer.parseInt(parts[5].replace("-", ""));
                int lastUpdated = Integer.parseInt(parts[6].replace("-", ""));
                int cashBalance = Integer.parseInt(parts[7].replace("-", ""));;

                User u = new User(id, fullName, email, birthDate,
                        initialCash, createdAt, lastUpdated, cashBalance);

                users.add(u);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getUserIdLookup(int Id) {
        List<User> allUsers = loadUsers();
        List<User> userList = new ArrayList<>();

        for (User u : allUsers) {
            if (u.getId() == Id) {
                userList.add(u);
            }
        }
        return userList;
    }

    public User getUserByEmail(String email) {
        List<User> allUsers = loadUsers();
        for (User u : allUsers) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        List<User> allUsers = loadUsers();
        for (User u : allUsers) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null; // hvis ingen bruger findes
    }

    public void saveUsers(List<User> users) {

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Database/users.csv"))) {

                bw.write("user_id;full_name;email;birth_date;initial_cash_DKK;created_at;last_updated; cashBalance");
                bw.newLine();

                for (User u : users) {

                    String line = String.join(";",
                            String.valueOf(u.getId()),
                            u.getFullName(),
                            u.getEmail(),
                            String.valueOf(u.getBirthDate()),
                            String.valueOf(u.getInitialCashDKK()),
                            String.valueOf(u.getCreatedAt()),
                            String.valueOf(u.getLastUpdated()),
                            String.valueOf(u.getCashBalance())
                    );

                    bw.write(line);
                    bw.newLine();
                }

            }
            catch (IOException e) {
                e.printStackTrace();
        }
    }

    public void updateBalance(int id, double newBalance) {
        List<User> users = loadUsers();

        boolean found = false;
        for (User u : users) {
            if (u.getId() == id) {
                u.setCashBalance((int) newBalance);
                u.setLastUpdated(java.time.LocalDate.now().getYear()
                        + java.time.LocalDate.now().getMonthValue()
                        + java.time.LocalDate.now().getDayOfMonth());
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("User with id" + id + " not found.");
        }

        saveUsers(users);
        }
    }
   //hej


