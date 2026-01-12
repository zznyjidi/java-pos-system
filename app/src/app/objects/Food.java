package objects;

import java.util.HashMap;
import java.util.Map;

public class Food {
    String name;
    double price;
    Map<String, Integer> ingredients;

    public Food(String name, double price, Map<String, Integer> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public static Food fromString(String foodString) {
        String[] args = foodString.split(" ");
        Map<String, Integer> ingredients = new HashMap<>();

        for (int i = 2; i < args.length; i++) {
            ingredients.compute(args[i], (name, count) -> count == null ? 1 : count + 1);
        }

        return new Food(args[0], Double.parseDouble(args[1].substring(1)), ingredients);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public String toString() {
        return name;
    }
}
