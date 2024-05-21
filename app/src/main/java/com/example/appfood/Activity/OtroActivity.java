package com.example.appfood.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.API.API;
import com.example.appfood.API.APIService.FoodService;
import com.example.appfood.Adapter.adapter_lvPlatos;
import com.example.appfood.MainActivity;
import com.example.appfood.Model.FoodListIngredientesModel;
import com.example.appfood.Model.FoodModel;
import com.example.appfood.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtroActivity extends AppCompatActivity {

    String t;

    String platos;
    TextView txtIngredientes;
    private TextView txtPlatos;
    String content;
   // private Button btnEnviar;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otro);

        txtIngredientes=(TextView)findViewById(R.id.txtIngredientes);
        //btnEnviar=(Button) findViewById(R.id.btnEnviar);
        txtPlatos=(TextView) findViewById(R.id.txtListaPlatos);
        txtPlatos.setMovementMethod(new ScrollingMovementMethod());
        txtIngredientes.setMovementMethod(new ScrollingMovementMethod());
        fab = findViewById(R.id.fabEnviarWhatsapp);

        t =(String) getIntent().getSerializableExtra("platosId");
        platos =(String) getIntent().getSerializableExtra("platos");

        FoodListIngredientesModel f =new FoodListIngredientesModel();
        f.setListFood(t);


        FoodService foodService = API.getAppi().create(FoodService.class);
        Call<List<FoodListIngredientesModel>> ingredientesCall = foodService.postFoodListIngredientes(f);

        ingredientesCall.enqueue(new Callback<List<FoodListIngredientesModel>>() {
            @Override
            public void onResponse(Call<List<FoodListIngredientesModel>> call, Response<List<FoodListIngredientesModel>> response) {

                List<FoodListIngredientesModel> Ingredientes=response.body();

                txtPlatos.append(platos);

                for (FoodListIngredientesModel r:Ingredientes)
                {
                    String content="";
                    content+=r.getFoodIngredientsName()+" ("+r.getCantidad()+")\n";
                    txtIngredientes.append(content);
                }






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
            public void onFailure(Call<List<FoodListIngredientesModel>> call, Throwable t) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(OtroActivity.this);
                builder1.setMessage(t.getMessage());
                builder1.setCancelable(false);
                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();
            }

        });

//        btnEnviar.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                enviaMensajeWhatsApp("*Lista de Platos*\n"+platos+"\n"+"*Lista de Ingredientes*\n"+txtIngredientes.getText().toString());
//            }});


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaMensajeWhatsApp("*Lista de Platos*\n"+platos+"\n"+"*Lista de Ingredientes*\n"+txtIngredientes.getText().toString());

            }
        });
    }

    public void enviaMensajeWhatsApp(String msj) {

        Intent sendIntent=new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,msj);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);


    }
}