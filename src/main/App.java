package main;

import java.io.File;
import java.util.Map;

import manager.ReservationManager;
import menu.MenuNode;
import menu.NodeParser;
import objects.Reservation;

public class App {
    static ReservationManager reservationManager = new ReservationManager(new File("reservations.table.txt"));

    public static void addReservationWithString(String input) {
        reservationManager.addReservationAndSync(Reservation.fromString(input)).printResult();
    }

    public static void main(String[] args) throws Exception {
        MenuNode root = new MenuNode("> ", Map.of(
                "1", new MenuNode("Enter Reservation: ", App::addReservationWithString)
        //
        ));
        NodeParser.runNode(root);
    }
}
