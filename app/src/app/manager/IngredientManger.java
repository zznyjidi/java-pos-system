package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import objects.Food;
import objects.OperationResult;

public class IngredientManger {
    Map<String, Integer> ingredients = new LinkedHashMap<>();
    File inventoryFile;

    public IngredientManger(File inventoryFile) {
        this.inventoryFile = inventoryFile;
    }

    public void readFile() throws IOException {
        ingredients = new LinkedHashMap<>();
        try (Scanner file = new Scanner(inventoryFile)) {
            while (file.hasNext()) {
                String line = file.nextLine();
                if (line.isEmpty())
                    continue;
                String[] splited = line.split(" ");
                ingredients.put(splited[0], Integer.parseInt(splited[1]));
            }
        } catch (FileNotFoundException e) {
            inventoryFile.createNewFile();
        }
    }

    public void writeFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inventoryFile))) {
            for (Entry<String, Integer> ingredient : ingredients.entrySet()) {
                writer.write(ingredient.getKey() + " " + ingredient.getValue() + "\n");
            }
        }
    }

    public void restockIngredient(String ingredientName, int number) {
        this.ingredients.compute(ingredientName, (key, value) -> (value == null ? number : value + number));
    }

    public OperationResult<String, String> useIngredient(String ingredientName, int number) {
        int ingredientLeft = this.ingredients.getOrDefault(ingredientName, 0);
        if (ingredientLeft >= number) {
            this.ingredients.computeIfPresent(ingredientName, (key, value) -> (value - number));
            return OperationResult.success(ingredientName, "Ingredient Used: ");
        }
        return OperationResult.failed(ingredientName, "Not Enough Ingredient: ");
    }

    public OperationResult<Food, OperationResult<String, String>> useIngredient(Food food) {
        Map<String, Integer> transactionDone = new HashMap<>();
        boolean failed = false;

        OperationResult<String, String> useResult = OperationResult.failed(food.toString(), "No Ingredient in Food: ");
        for (Entry<String, Integer> ingredient : food.getIngredients().entrySet()) {
            useResult = useIngredient(ingredient.getKey(), ingredient.getValue());
            if (useResult.getStatus())
                transactionDone.put(ingredient.getKey(), ingredient.getValue());
            else {
                failed = true;
                break;
            }
        }

        if (failed)
            for (Entry<String, Integer> ingredient : transactionDone.entrySet())
                restockIngredient(ingredient.getKey(), ingredient.getValue());

        if (!failed)
            return OperationResult.success(food, "Ingredient Used for Food: ");
        else
            return OperationResult.failed(useResult);
    }

    public OperationResult<OperationResult<Food, OperationResult<String, String>>, IOException> useIngredientAndSync(
            Food food) {
        try {
            readFile();
            OperationResult<Food, OperationResult<String, String>> state = useIngredient(food);
            if (state.getStatus())
                writeFile();
            return OperationResult.success(state);
        } catch (IOException e) {
            return OperationResult.failed(e);
        }
    }
}
