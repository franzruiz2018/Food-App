package com.example.appfood.Model;

public class FoodListIngredientesModel {


    private int FoodIngredientsId;
    private String FoodIngredientsName;
    private int Cantidad;

    private String ListFood;

    public FoodListIngredientesModel() {

    }

    public FoodListIngredientesModel(int foodIngredientsId, String foodIngredientsName, int cantidad, String listFood) {
        FoodIngredientsId = foodIngredientsId;
        FoodIngredientsName = foodIngredientsName;
        Cantidad = cantidad;
        ListFood = listFood;
    }

    public int getFoodIngredientsId() {
        return FoodIngredientsId;
    }

    public void setFoodIngredientsId(int foodIngredientsId) {
        FoodIngredientsId = foodIngredientsId;
    }

    public String getFoodIngredientsName() {
        return FoodIngredientsName;
    }

    public void setFoodIngredientsName(String foodIngredientsName) {
        FoodIngredientsName = foodIngredientsName;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public String getListFood() {
        return ListFood;
    }

    public void setListFood(String listFood) {
        ListFood = listFood;
    }
}
