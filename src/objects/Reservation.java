package objects;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    LocalDateTime time;
    String firstName;
    String lastName;
    int guestCount;
    Duration reservationLength;

    public Reservation(LocalDateTime time, String firstName, String lastName, int guestCount,
            Duration reservationLength) {
        this.time = time;
        this.firstName = firstName;
        this.lastName = lastName;
        this.guestCount = guestCount;
        this.reservationLength = reservationLength;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public Duration getReservationLength() {
        return reservationLength;
    }

    public void setReservationLength(Duration reservationLength) {
        this.reservationLength = reservationLength;
    }

    public String toString() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yy h:mma");
        return String.format("%s %s %s %d",
                timeFormat.format(time),
                firstName, lastName, guestCount);
    }
}
