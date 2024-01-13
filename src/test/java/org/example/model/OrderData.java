package org.example.model;

import java.util.List;

public class OrderData {
    private final List<String> ingredients;

    public OrderData(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "ingredients=" + ingredients +
                '}';
    }
}
