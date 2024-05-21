package com.example.appfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.API.API;
import com.example.appfood.API.APIService.FoodService;
import com.example.appfood.Activity.IngredientesActivity;
import com.example.appfood.Activity.OtroActivity;
import com.example.appfood.Adapter.adapter_lvPlatos;
import com.example.appfood.Model.FoodModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtData;

    private Button btnSeleccionar;
    ListView listView;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSeleccionar=(Button) findViewById(R.id.btnSeleccionar);





        ListarPlatos();


    }

private void ListarPlatos()
{
    FoodService foodService = API.getAppi().create(FoodService.class);
    Call<List<FoodModel>> foodCall = foodService.getFood();

    foodCall.enqueue(new Callback<List<FoodModel>>() {
        @Override
        public void onResponse(Call<List<FoodModel>> call, Response<List<FoodModel>> response) {

            List<FoodModel> platos=response.body();



            listView = (ListView) findViewById(R.id.lvPlatos);
            adapter_lvPlatos _adapter_lvPlatos = new adapter_lvPlatos(MainActivity.this, R.layout.item_listview_platos, platos);
            listView.setAdapter(_adapter_lvPlatos);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    FoodModel foodModel = (FoodModel) adapterView.getItemAtPosition(position);
//                    Toast.makeText(MainActivity.this, Integer.toString(foodModel.getFoodId()), Toast.LENGTH_SHORT).show();

                    FoodModel foodModel = (FoodModel) platos.get(position);

                    if (foodModel.isChecked())
                        foodModel.setChecked(false);

                    else
                        foodModel.setChecked(true);

                    //now update adapter
                    _adapter_lvPlatos.updateRecords(platos);

                }
            });

            btnSeleccionar.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                   String platosId = "";
                    String platos = "";
                    int cntChoice = listView.getCount();

                    for(int i = 0; i < cntChoice; i++){
                        FoodModel foodModel = (FoodModel) listView.getItemAtPosition(i);
                        if(foodModel.isChecked()) {
                            platosId += foodModel.getFoodId() + ",";
                            platos += foodModel.getFoodName() + "\n";
                        }
                    }
                    if(platosId==""){
                        Toast.makeText(MainActivity.this, "Seleccione al menos un plato", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Intent intent = new Intent(MainActivity.this, OtroActivity.class);
                    intent.putExtra("platosId", removeLastChar(platosId) );
                    intent.putExtra("platos", platos );
                    startActivity(intent);
//                    listView.setEnabled(false);
//                    btnSeleccionar.setEnabled(false);
//                   Toast.makeText(MainActivity.this, selected, Toast.LENGTH_LONG).show();

                }});

//            btnSeleccionar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent intent = new Intent(MainActivity.this, OtroActivity.class);
//
//
//
//                    startActivity(intent);
//                }
//            });
        }

        @Override
        public void onFailure(Call<List<FoodModel>> call, Throwable t) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setMessage(t.getMessage());
            builder1.setCancelable(false);
            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();
        }
    });

}
    public static String removeLastChar(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }


}