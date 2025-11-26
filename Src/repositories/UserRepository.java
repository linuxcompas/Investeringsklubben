package repositories;

import structure.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";");

                int id = Integer.parseInt(parts[0]);
                String fullName = parts[1];
                String email = parts[2];
                String birthDate = parts[3];
                double initialCash = Double.parseDouble(parts[4]);
                String createdAt = parts[5];
                String lastUpdated = parts[6];

                User user = new User(id, fullName, email, birthDate,
                        initialCash, createdAt, lastUpdated);

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public void saveUsers(List<User> users) {

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Database/users.csv"))) {

                bw.write("user_id;full_name;email;birth_date;initial_cash_DKK;created_at;last_updated");
                bw.newLine();

                for (User u : users) {

                    String line = String.join(";",
                            String.valueOf(u.getId()),
                            u.getFullName(),
                            u.getEmail(),
                            u.getBirthDate(),
                            String.valueOf(u.getInitialCashDKK()),
                            u.getCreatedAt(),
                            u.getLastUpdated();

                    bw.write(line);
                    bw.newLine();
                }

            }
            catch (IOException e) {
                e.printStackTrace();
        }
    }
}



