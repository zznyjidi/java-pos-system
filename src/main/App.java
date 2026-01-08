package main;

import java.io.File;
import java.util.Map;

import manager.ReservationManager;
import menu.MenuNode;
import objects.Reservation;

public class App {
    static boolean running = true;

    static ReservationManager reservationManager = new ReservationManager(new File("reservations.table.txt"));

    static void addReservationWithString(String input) {
        reservationManager.addReservationAndSync(Reservation.fromString(input)).printResult();
    }

    static MenuNode root = new MenuNode("> ", Map.of(
            "1", new MenuNode("Enter Reservation: ", App::addReservationWithString),
            "", () -> running = false
    //
    ));

    public static void main(String[] args) {
        while (running)
            root.runNode();
    }
}
