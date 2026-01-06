package objects;

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

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }
}
