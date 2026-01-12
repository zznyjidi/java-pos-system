package manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import objects.Food;
import objects.OperationResult;

public class IngredientManger {
    Map<String, Integer> ingredients = new HashMap<>();

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
}
