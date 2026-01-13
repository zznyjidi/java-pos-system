package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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

    int tableCount = 8;
    int eachTableSeat = 6;

    public ReservationManager(File reservationFile) {
        this.reservationFile = reservationFile;
    }

    public void readFile() throws IOException {
        reservations = new ArrayList<>();
        try (Scanner file = new Scanner(reservationFile)) {
            while (file.hasNext()) {
                String line = file.nextLine();
                if (line.isEmpty())
                    continue;
                OperationResult<Reservation, Reservation> newReservationResult = addReservation(
                        Reservation.fromString(line));
                if (!newReservationResult.getStatus())
                    throw new FileCorruptedException(newReservationResult.getError());
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
        if (newReservation.getGuestCount() > tableCount * eachTableSeat)
            return OperationResult.failed(newReservation, "Reservation Too Large: ");

        LocalDateTime newReservationStartTime = newReservation.getTime();
        List<Reservation> overlappedReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            LocalDateTime reservationStartTime = reservation.getTime();

            if (newReservation.getFirstName().equals(reservation.getFirstName())
                    && newReservation.getLastName().equals(reservation.getLastName())
                    && newReservationStartTime.toLocalDate().equals(reservationStartTime.toLocalDate()))
                return OperationResult.failed(reservation,
                        "One person is only allow to make one reservation per day: ");

            if (newReservation.isOverlapping(reservation)) {
                overlappedReservations.add(reservation);
            }
        }

        int requiredTable = getTableCount(newReservation);
        for (int i = 0; i < overlappedReservations.size(); i++) {
            Reservation currentReservation = overlappedReservations.get(i);
            int occupiedTableCount = getTableCount(currentReservation) + requiredTable;
            for (int j = i + 1; j < overlappedReservations.size(); j++) {
                Reservation checkingReservation = overlappedReservations.get(j);
                if (currentReservation.isOverlapping(checkingReservation))
                    occupiedTableCount += getTableCount(checkingReservation);
            }
            if (occupiedTableCount > tableCount)
                return OperationResult.failed(newReservation, "Not Enough Seat Left: ");
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

    public OperationResult<Reservation, LocalDate> removeReservation(String firstName, String lastName,
            LocalDate date) {
        Reservation reservationToRemove = null;
        for (Reservation reservation : reservations) {
            if (reservation.getTime().toLocalDate().equals(date)
                    && reservation.getFirstName().equals(firstName)
                    && reservation.getLastName().equals(lastName)) {
                reservationToRemove = reservation;
                break;
            }
        }
        if (reservationToRemove != null) {
            reservations.remove(reservationToRemove);
            return OperationResult.success(reservationToRemove, "Reservation Removed: ");
        }
        return OperationResult.failed(date, "Reservation Not Found: ");
    }

    public OperationResult<OperationResult<Reservation, LocalDate>, IOException> removeReservatinoAndSync(
            String firstName, String lastName, LocalDate date) {
        try {
            readFile();
            OperationResult<Reservation, LocalDate> state = removeReservation(firstName, lastName, date);
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

    int getTableCount(Reservation reservation) {
        return (reservation.getGuestCount() / eachTableSeat)
                + (reservation.getGuestCount() % eachTableSeat == 0 ? 0 : 1);
    }
}
