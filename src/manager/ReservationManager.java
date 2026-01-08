package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.FileCorruptedException;
import objects.OperationResult;
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
                if (!addReservation(Reservation.fromString(line)).getStatus())
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

    public OperationResult<Reservation, Reservation> addReservation(Reservation newReservation) {
        LocalDateTime newReservationStartTime = newReservation.getTime();
        LocalDateTime newReservationEndTime = newReservationStartTime.plus(newReservation.getReservationLength());
        List<Reservation> overlappedReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            LocalDateTime reservationStartTime = reservation.getTime();
            LocalDateTime reservationEndTime = reservationStartTime.plus(reservation.getReservationLength());

            if (newReservation.getFirstName().equals(reservation.getFirstName())
                    && newReservation.getLastName().equals(reservation.getLastName())
                    && newReservationStartTime.toLocalDate().equals(reservationStartTime.toLocalDate()))
                return OperationResult.failed(reservation,
                        "One person is only allow to make one reservation per day: ");

            if (newReservationStartTime.isBefore(reservationEndTime)
                    && reservationStartTime.isBefore(newReservationEndTime)) {
                overlappedReservations.add(reservation);
            }
        }
        reservations.add(newReservation);
        return OperationResult.success(newReservation, "Reservation Created: ");
    }

    public OperationResult<OperationResult<Reservation, Reservation>, IOException> addReservationAndSync(
            Reservation newReservation) {
        try {
            readFile();
            OperationResult<Reservation, Reservation> state = addReservation(newReservation);
            if (state.getStatus())
                writeFile();
            return OperationResult.success(state);
        } catch (IOException e) {
            return OperationResult.failed(e);
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
