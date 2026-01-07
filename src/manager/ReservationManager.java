package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.FileCorruptedException;
import objects.Reservation;

public class ReservationManager {
    List<Reservation> reservations = new ArrayList<>();
    File reservationFile;

    public ReservationManager(File reservationFile) {
        this.reservationFile = reservationFile;
    }

    public void readFile() throws IOException {
        try (Scanner file = new Scanner(reservationFile)) {
            while (file.hasNext()) {
                String line = file.nextLine();
                if (line.isEmpty())
                    continue;
                if (!addReservation(Reservation.fromString(line)))
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
