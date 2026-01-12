package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import objects.Food;

public class FoodManager {
    Map<String, Food> foods = new LinkedHashMap<>();
    File menuFile;

    public FoodManager(File menuFile) throws IOException {
        this.menuFile = menuFile;
        readFile();
    }

    public void readFile() throws IOException {
        foods = new LinkedHashMap<>();
        try (Scanner file = new Scanner(menuFile)) {
            while (file.hasNext()) {
                String line = file.nextLine();
                if (line.isEmpty())
                    continue;
                Food newFood = Food.fromString(line);
                foods.put(newFood.getName(), newFood);
            }
        } catch (FileNotFoundException e) {
            menuFile.createNewFile();
        }
    }

    public Food getFood(String name) {
        return foods.get(name);
    }
}
