package exception;

import java.io.IOException;

import objects.Reservation;

public class FileCorruptedException extends IOException {
    Reservation conflictReservation;

    public FileCorruptedException(Reservation conflict) {
        super();
        this.conflictReservation = conflict;
    }

    public Reservation getConflictReservation() {
        return conflictReservation;
    }
}
