package utility;

public class DateAndTime {

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
    }
