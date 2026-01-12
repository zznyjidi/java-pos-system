package main;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import manager.FoodManager;
import manager.IngredientManger;
import manager.OrderManager;
import manager.ReservationManager;
import menu.MenuNode;
import objects.Food;
import objects.Reservation;

public class App {
    static boolean running = true;

    static ReservationManager reservationManager = new ReservationManager(new File("reservations.table.txt"));
    static IngredientManger ingredientManger = new IngredientManger(new File("inventory.table.txt"));
    static OrderManager orderManager = new OrderManager(new File("profit.table.txt"), ingredientManger);
    static FoodManager foodManager;

    static {
        try {
            foodManager = new FoodManager(new File("menu.table.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void addReservationWithString(String input) {
        reservationManager.addReservationAndSync(Reservation.fromString(input)).printResult();
    }

    static void orderAction() {
        orderManager.newOrder();
        String input;
        while (true) {
            input = IO.readln("Order: ");
            if (input.isEmpty()) {
                orderManager.finishOrderAndSync().printResult();
                break;
            } else {
                Food newFood = foodManager.getFood(input);
                if (newFood != null)
                    orderManager.addToOrder(newFood).printResult();
                else
                    IO.println("Food not found: " + input);
            }
        }
    }

    @SuppressWarnings("null")
    static MenuNode root = new MenuNode("> ", Map.of(
            "1", new MenuNode("Enter Reservation: ", App::addReservationWithString),
            "3", App::orderAction,
            "exit", () -> running = false
    //
    ));

    public static void main(String[] args) {
        running = true;
        while (running)
            root.runNode();
    }
}
