package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.Food;
import objects.OperationResult;

public class OrderManager {
    double profit;
    File profitFile;
    IngredientManger ingredientManger;

    public OrderManager(File profitFile, IngredientManger ingredientManger) {
        this.profitFile = profitFile;
        this.ingredientManger = ingredientManger;
    }

    public void readFile() throws IOException {
        try (Scanner file = new Scanner(profitFile)) {
            profit = file.nextDouble();
        } catch (FileNotFoundException e) {
            profitFile.createNewFile();
        }
    }

    public void writeFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(profitFile))) {
            writer.write(String.format("%.2f", profit));
        }
    }

    List<Food> currentOrder;

    public OperationResult<String, IOException> newOrder() {
        try {
            readFile();
            ingredientManger.readFile();
            currentOrder = new ArrayList<>();
        } catch (IOException e) {
            return OperationResult.failed(e);
        }
        return OperationResult.success("", "New Order Ready. ");
    }

    public OperationResult<Food, OperationResult<String, String>> addToOrder(Food food) {
        OperationResult<Food, OperationResult<String, String>> state = ingredientManger.useIngredient(food);
        if (state.getStatus())
            currentOrder.add(food);
        return state;
    }

    public OperationResult<Double, IOException> finishOrderAndSync() {
        double orderPrice = currentOrder.stream().mapToDouble((food) -> (food.getPrice())).sum();
        try {
            profit += orderPrice;
            writeFile();
            ingredientManger.writeFile();
        } catch (IOException e) {
            return OperationResult.failed(e);
        }
        return OperationResult.success(orderPrice, "Order Finished: Total: ");
    }

    public double getProfit() {
        return profit;
    }
}
