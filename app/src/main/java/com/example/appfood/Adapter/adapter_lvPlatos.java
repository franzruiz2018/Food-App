package com.example.appfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.Model.FoodModel;
import com.example.appfood.R;

import java.util.List;
import java.util.stream.Collectors;

public class adapter_lvPlatos extends BaseAdapter {

    private Context context;
    private int layout;
    private List<FoodModel> Platos;

    public adapter_lvPlatos(Context context, int layout, List<FoodModel> platos) {
        this.context = context;
        this.layout = layout;
        Platos = platos;
    }

    public int getCount() {
        return Platos.size();
    }
    public Object getItem(int position) {
        return this.Platos.get(position);
    }

    public long getItemId(int id) {
        return id;
    }


    public View getView(int position, View view, ViewGroup viewGroup) {
        View v =view;
        LayoutInflater layoutInflater=LayoutInflater.from(this.context);
        v= layoutInflater.inflate(R.layout.item_listview_platos,null);


        final FoodModel Item = (FoodModel) getItem(position);
        TextView _txtNombrePlato =(TextView) v.findViewById(R.id.txtPlato);
        _txtNombrePlato.setText(Item.getFoodName());
        TextView _txtCategoria =(TextView) v.findViewById(R.id.txtCategoria);

        _txtCategoria.setText(Item.getFoodCategoryName());
        ImageView ivCheckBox = (ImageView) v.findViewById(R.id.iv_check_box);

        if (Item.isChecked())  {
            ivCheckBox.setBackgroundResource(R.drawable.checked);
        }
        else{
            ivCheckBox.setBackgroundResource(R.drawable.check);
        }

        return v;
    }

    public void updateRecords(List<FoodModel> platos){
        this.Platos = platos;
        notifyDataSetChanged();
    }

}
