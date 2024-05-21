package com.example.appfood.API.APIService;

import com.example.appfood.Model.FoodListIngredientesModel;
import com.example.appfood.Model.FoodModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface FoodService {

    @GET("food/listar")
    Call<List<FoodModel>> getFood();

    @POST("food/listar_ingredientes")
    Call<List<FoodListIngredientesModel>> postFoodListIngredientes(@Body FoodListIngredientesModel foodListIngredientesModel);

}
