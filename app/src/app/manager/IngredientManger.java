package manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import objects.Food;

public class IngredientManger {
    Map<String, Integer> ingredients = new HashMap<>();

    public void restockIngredient(String ingredientName, int number) {
        this.ingredients.compute(ingredientName, (key, value) -> (value == null ? number : value + number));
    }

    public boolean useIngredient(String ingredientName, int number) {
        int ingredientLeft = this.ingredients.getOrDefault(ingredientName, 0);
        if (ingredientLeft >= number) {
            this.ingredients.computeIfPresent(ingredientName, (key, value) -> (value - number));
            return true;
        }
        return false;
    }

    public boolean useIngredient(Food food) {
        Map<String, Integer> transactionDone = new HashMap<>();
        boolean failed = false;

        for (Entry<String, Integer> ingredient : food.getIngredients().entrySet()) {
            if (useIngredient(ingredient.getKey(), ingredient.getValue()))
                transactionDone.put(ingredient.getKey(), ingredient.getValue());
            else {
                failed = true;
                break;
            }
        }

        if (failed)
            for (Entry<String, Integer> ingredient : transactionDone.entrySet())
                restockIngredient(ingredient.getKey(), ingredient.getValue());

        return !failed;
    }
}
