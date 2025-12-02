package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

    public class DateTime implements Comparable<DateTime> {

        private final LocalDate date;

        // godkender både ddmmyyyy og dd-mm-yyyy
        public DateTime(String dateStr) {
            if (dateStr == null || dateStr.isBlank()) {
                throw new IllegalArgumentException("Date string cannot be null or blank");
            }

            DateTimeFormatter[] formatters = {
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                    DateTimeFormatter.ofPattern("ddMMyyyy")
            };

            LocalDate parsedDate = null;
            for (DateTimeFormatter f : formatters) {
                try {
                    parsedDate = LocalDate.parse(dateStr, f);
                    break;
                } catch (DateTimeParseException ignored) {}
            }

            if (parsedDate == null) {
                throw new IllegalArgumentException("Invalid date format: " + dateStr);
            }

            this.date = parsedDate;
        }

        // evt konstruktør med localdate direkte
        public DateTime(LocalDate localDate) {
            if (localDate == null) {
                throw new IllegalArgumentException("LocalDate cannot be null");
            }
            this.date = localDate;
        }

        public int getDay() {
            return date.getDayOfMonth();
        }

        public int getMonth() {
            return date.getMonthValue();
        }

        public int getYear() {
            return date.getYear();
        }

        @Override
        public String toString() {
            return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        @Override
        public int compareTo(DateTime other) {
            return this.date.compareTo(other.date);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof DateTime)) return false;
            return this.date.equals(((DateTime) obj).date);
        }

        @Override
        public int hashCode() {
            return date.hashCode();
        }

        // hjælpemetode til at hente localdate
        public LocalDate toLocalDate() {
            return date;
        }

        public int toInt() {
            return Integer.parseInt(date.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        }


        public static int todayAsInt() {
            return Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        }
    }
