package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.FileCorruptedException;
import objects.Reservation;

public class ReservationManager {
    List<Reservation> reservations = new ArrayList<>();
    File reservationFile;
    Duration defaultReservationLength = Duration.ofHours(1);
    Pattern reservationPattern = Pattern.compile(
            "(?<day>[0-9]{2})/(?<month>[0-9]{2})/(?<year>[0-9]{2,4}) (?<hour>[0-9]{1,2}):(?<minute>[0-9]{2})(?<AM>AM|PM) (?<firstName>[a-zA-Z]+) (?<lastName>[a-zA-Z]+) (?<guestCount>[0-9]+)");

    public ReservationManager(File reservationFile) {
        this.reservationFile = reservationFile;
    }

    public void readFile() throws IOException {
        try (Scanner file = new Scanner(reservationFile)) {
            while (file.hasNext()) {
                Matcher matcher = reservationPattern.matcher(file.nextLine());
                matcher.find();
                if (addReservation(
                        LocalDateTime.of(
                                Integer.parseInt(matcher.group("year")),
                                Integer.parseInt(matcher.group("month")),
                                Integer.parseInt(matcher.group("day")),
                                Integer.parseInt(matcher.group("hour")) + (matcher.group("AM").equals("PM") ? 12 : 0),
                                Integer.parseInt(matcher.group("minute"))),
                        matcher.group("firstName"), matcher.group("lastName"),
                        Integer.parseInt(matcher.group("guestCount")), defaultReservationLength))
                    throw new FileCorruptedException();
            }
        } catch (FileNotFoundException e) {
            reservationFile.createNewFile();
        }
    }

    public void writeFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reservationFile))) {
            for (Reservation reservation : reservations) {
                writer.write(reservation.toString() + "\n");
            }
        }
    }

    public boolean addReservation(LocalDateTime time, String firstName, String lastName,
            int guestCount, Duration reservationLength) {
        for (Reservation reservation : reservations) {
            if (reservation.getFirstName().equals(firstName) && reservation.getLastName().equals(lastName)
                    && reservation.getTime().toLocalDate().equals(time.toLocalDate()))
                return false;
        }
        reservations.add(new Reservation(time, firstName, lastName, guestCount, reservationLength));
        return true;
    }
}
