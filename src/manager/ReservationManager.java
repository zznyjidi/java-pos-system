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
            "(?<daytime>[0-9]{2}\\/[0-9]{2}\\/[0-9]{2,4} [0-9]{1,2}:[0-9]{2}(AM|PM)) (?<firstName>[a-zA-Z]+) (?<lastName>[a-zA-Z]+) (?<guestCount>[0-9]+)");

    public ReservationManager(File reservationFile) {
        this.reservationFile = reservationFile;
    }

    public void readFile() throws IOException {
        try (Scanner file = new Scanner(reservationFile)) {
            while (file.hasNext()) {
                String line = file.nextLine();
                if (line.isEmpty())
                    continue;
                Matcher matcher = reservationPattern.matcher(line);
                if (!matcher.find())
                    throw new FileCorruptedException();
                if (!addReservation(new Reservation(
                        LocalDateTime.parse(matcher.group("daytime"), Reservation.timeFormat),
                        matcher.group("firstName"), matcher.group("lastName"),
                        Integer.parseInt(matcher.group("guestCount")), defaultReservationLength)))
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

    public boolean addReservation(Reservation newReservation) {
        for (Reservation reservation : reservations) {
            if (newReservation.getFirstName().equals(reservation.getFirstName())
                    && newReservation.getLastName().equals(reservation.getLastName())
                    && newReservation.getTime().toLocalDate().equals(reservation.getTime().toLocalDate()))
                return false;
        }
        reservations.add(newReservation);
        return true;
    }

    public boolean addReservationAndSync(Reservation newReservation) throws IOException {
        readFile();
        boolean state = addReservation(newReservation);
        if (state)
            writeFile();
        return state;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
