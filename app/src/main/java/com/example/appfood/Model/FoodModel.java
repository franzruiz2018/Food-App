package com.example.appfood.Model;

public class FoodModel {


    private int FoodId;
    private String FoodName;
    private int FoodCategoryId;

    private String FoodCategoryName;
    private boolean checked;

    public FoodModel() {
    }

    public FoodModel(int foodId, String foodName, int foodCategoryId, String foodCategoryName, boolean checked) {
        FoodId = foodId;
        FoodName = foodName;
        FoodCategoryId = foodCategoryId;
        FoodCategoryName = foodCategoryName;
        this.checked = checked;
    }

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public int getFoodCategoryId() {
        return FoodCategoryId;
    }

    public void setFoodCategoryId(int foodCategoryId) {
        FoodCategoryId = foodCategoryId;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public String getFoodCategoryName() {
        return FoodCategoryName;
    }

    public void setFoodCategoryName(String foodCategoryName) {
        FoodCategoryName = foodCategoryName;
    }
}
