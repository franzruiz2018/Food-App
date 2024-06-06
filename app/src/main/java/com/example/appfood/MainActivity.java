package com.example.appfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtData;
    public static List<FoodModel> platos;
    public static int idPlatoSeleccionado;
    public static int checkPlatoSeleccionado;
    private List<FoodModel> platosSeleccionados;
    private FoodModel platoSeleccionado;
    private List<FoodModel> platosInicial2;
    private Button btnSeleccionar;
    private Button btnFondo;
    private Button btnDesayuno;
    private Button btnSopa;
    private Button btnAll;
    ListView listView;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSeleccionar=(Button) findViewById(R.id.btnSeleccionar);
        btnFondo=(Button) findViewById(R.id.btnFondo);
        btnAll=(Button) findViewById(R.id.btnAll);
        btnDesayuno=(Button) findViewById(R.id.btnDesayuno);
        btnSopa=(Button) findViewById(R.id.btnSopa);
        txtData=(TextView) findViewById(R.id.txtData);
        txtData.setText("Seleccione algún plato");
        BuscarPlatos();
        botones();



    }

    private void botones() {

        btnFondo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "botones --- Fondo");
                ListarPlatosSeleccionados("Segundo", 0);
            }
        });
        btnAll.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "botones --- All");
                ListarPlatosSeleccionados("", 1);
            }
        });
        btnDesayuno.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "botones --- Desayuno");
                ListarPlatosSeleccionados("Desayuno", 0);
            }
        });
        btnSopa.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "botones --- Sopa");
                ListarPlatosSeleccionados("Sopa", 0);
            }
        });

        btnSeleccionar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "botones --- btnSeleccionar");
                String platosId = "";
                String platos = "";
                int cntChoice = listView.getCount();

                for (int i = 0; i < cntChoice; i++) {
                    FoodModel foodModel = (FoodModel) listView.getItemAtPosition(i);
                    if (foodModel.isChecked()) {
                        platosId += foodModel.getFoodId() + ",";
                        platos += foodModel.getFoodName() + "\n";
                    }
                }
                if (platosId == "") {
                    Toast.makeText(MainActivity.this, "Seleccione al menos un plato", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, OtroActivity.class);
                intent.putExtra("platosId", removeLastChar(platosId));
                intent.putExtra("platos", platos);
                Log.d("tag", "botones --- btnSeleccionar--Intent platosId:" + platosId +" platos:" +platos );
                startActivity(intent);
            }
        });
    }

    private void ListarPlatosSeleccionados(String tipoPlato, int menu) {
        platosSeleccionados=platos;
        Log.d("tag","ListarPlatosSeleccionados --- tipoplato:"+tipoPlato+ "Menu:"+menu);

        if (menu == 1) {
            Log.d("tag","ListarPlatosSeleccionados --- opcion Menu=1 ");
            platosSeleccionados=platosSeleccionados
                    .stream()
                    .filter(x -> x.isChecked())
                    .collect(Collectors.toList());
            Log.d("tag","ListarPlatosSeleccionados --- Menu=1 / Solo los Seleccionados");

            if(platosSeleccionados.stream().count()>0){
                txtData.setText("Platos Seleccionados:");
            }
            else{
                txtData.setText("Seleccione algún plato");
            }
        }
        else{
            Log.d("tag","ListarPlatosSeleccionados --- opcion Menu=0 ");
            platosSeleccionados = platosSeleccionados.stream()
                    .filter(persona -> persona.getFoodCategoryName().equals(tipoPlato))
                    .collect(Collectors.toList());
            Log.d("tag","ListarPlatosSeleccionados --- Menu=0 / Por tipo de Plato");
            platosSeleccionados=platosSeleccionados
                    .stream()
                    .filter(x -> !x.isChecked())
                    .collect(Collectors.toList());
            Log.d("tag","ListarPlatosSeleccionados --- Menu=0 / Por tipo de Plato / Solo los no seleccionados");
            txtData.setText("Seleccione algún plato");
        }



        listView = (ListView) findViewById(R.id.lvPlatos);
        adapter_lvPlatos _adapter_lvPlatos = new adapter_lvPlatos(MainActivity.this, R.layout.item_listview_platos, platosSeleccionados);
        listView.setAdapter(_adapter_lvPlatos);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                platoSeleccionado = (FoodModel) (listView.getItemAtPosition(position));
                idPlatoSeleccionado =platoSeleccionado.getFoodId();
                if(platoSeleccionado.isChecked()){
                    checkPlatoSeleccionado=0;
                }
                else {
                    checkPlatoSeleccionado=1;
                }

                Log.d("tag","ListarPlatosSeleccionados --- idPlato:"+idPlatoSeleccionado+" Check:"+checkPlatoSeleccionado+ "Plato:"+platoSeleccionado.getFoodName());
                ActualizarListaPlatos(idPlatoSeleccionado,checkPlatoSeleccionado);

            }

        });

    }

    private void ActualizarListaPlatos(int idPlato,int check)
    {
        FoodModel platoQueSeActualizara=new FoodModel();
        for (FoodModel p : platos) {

            if (p.getFoodId()==idPlato) {
                platoQueSeActualizara=p;
            }
        }

        if (check==1)
        platoQueSeActualizara.setChecked(true);
        else
        platoQueSeActualizara.setChecked(false);
        Log.d("tag","ActualizarListaPlatos -- idPlato:"+platoQueSeActualizara.getFoodId()+" Check:"+check+ "Plato:"+platoQueSeActualizara.getFoodName());
        int  indice;
        indice = buscarIndicePorId(platos, idPlato);
        Log.d("tag","ActualizarListaPlatos -- Indice:"+indice);
        platos.set(indice, platoQueSeActualizara);

        listarElementosEnLogcat(platos);
        ListarPlatosSeleccionados("",1);
    }

    private void listarElementosEnLogcat(List<FoodModel>  lista) {
        for (int i = 0; i < lista.size(); i++) {
            Log.d("TAG", "Elemento " + i + ": " + lista.get(i).getFoodId() +"-"+ lista.get(i).getFoodName());
        }
    }

    public static int buscarIndicePorId(List<FoodModel>  lista, int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getFoodId()==id) {
                return i;
            }
        }
        return -1; // Retorna -1 si no se encuentra
    }

    private void BuscarPlatos()
    {
    FoodService foodService = API.getAppi().create(FoodService.class);
    Call<List<FoodModel>> foodCall = foodService.getFood();


    foodCall.enqueue(new Callback<List<FoodModel>>() {
        @Override
        public void onResponse(Call<List<FoodModel>> call, Response<List<FoodModel>> response) {
            platos=response.body();
            ListarPlatosSeleccionados("",1);
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