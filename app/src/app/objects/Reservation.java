package objects;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reservation {
    LocalDateTime time;
    String firstName;
    String lastName;
    int guestCount;
    Duration reservationLength;

    public static Duration defaultReservationLength = Duration.ofHours(1);
    public static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yy h:mma", Locale.US);
    public static Pattern reservationPattern = Pattern.compile(
            "(?<daytime>[0-9]{2}\\/[0-9]{2}\\/[0-9]{2,4} [0-9]{1,2}:[0-9]{2}(AM|PM)) (?<firstName>[a-zA-Z]+) (?<lastName>[a-zA-Z]+) (?<guestCount>[0-9]+)");

    public Reservation(LocalDateTime time, String firstName, String lastName, int guestCount,
            Duration reservationLength) {
        this.time = time;
        this.firstName = firstName;
        this.lastName = lastName;
        this.guestCount = guestCount;
        this.reservationLength = reservationLength;
    }

    public static Reservation fromString(String reservationString) {
        Matcher matcher = reservationPattern.matcher(reservationString);
        if (!matcher.find())
            throw new IllegalArgumentException("Reservation String Invalid: " + reservationString);
        return new Reservation(
                LocalDateTime.parse(matcher.group("daytime"), Reservation.timeFormat),
                matcher.group("firstName"), matcher.group("lastName"),
                Integer.parseInt(matcher.group("guestCount")), defaultReservationLength);
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

    public boolean isOverlapping(Reservation other) {
        return getTime().isBefore(other.getTime().plus(other.getReservationLength()))
                && other.getTime().isBefore(getTime().plus(getReservationLength()));
    }

    public String toString() {
        return String.format("%s %s %s %d",
                timeFormat.format(time),
                firstName, lastName, guestCount);
    }
}
