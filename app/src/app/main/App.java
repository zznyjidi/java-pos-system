package main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.US);
    static Pattern removeReservationPattern = Pattern
            .compile("(?<daytime>[0-9]{2}\\/[0-9]{2}\\/[0-9]{2,4}) (?<firstName>[a-zA-Z]+) (?<lastName>[a-zA-Z]+)");

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

    static void removeReservationWithString(String input) {
        Matcher matcher = removeReservationPattern.matcher(input);
        if (!matcher.find())
            throw new IllegalArgumentException("Reservation String Invalid: " + input);
        reservationManager.removeReservatinoAndSync(
                matcher.group("firstName"), matcher.group("lastName"),
                LocalDate.parse(matcher.group("daytime"), timeFormat)).printResult();
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

    static void showReservationAndSync() {
        try {
            reservationManager.readFile();
            IO.println(reservationManager.getReservations());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void showProfitAndSync() {
        try {
            orderManager.readFile();
            IO.println(orderManager.getProfit());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void showInventoryAndSync() {
        try {
            ingredientManger.readFile();
            IO.println(ingredientManger.getIngredients());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("null")
    static MenuNode root = new MenuNode("Create(1), Delete(2) Reservation, Order(3), Show Status(4)\n> ", Map.of(
            "1", new MenuNode("Enter Reservation: ", App::addReservationWithString),
            "2", new MenuNode("Remove Reservation: ", App::removeReservationWithString),
            "3", App::orderAction,
            "4", new MenuNode("Reservation(1), Profit(2) or Inventory(3): ", Map.of(
                    "1", App::showReservationAndSync,
                    "2", App::showProfitAndSync,
                    "3", App::showInventoryAndSync
            //
            )),
            "exit", () -> running = false
    //
    ));

    public static void main(String[] args) {
        running = true;
        while (running)
            root.runNode();
    }
}
