package com.expriceit.maserven.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.expriceit.maserven.R;
import com.expriceit.maserven.entities.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stalyn on 11/12/2017.
 */

public class Adapter extends BaseAdapter {
    private Context context;
    private List<Items.getItems> arrayList;

    public Adapter(Context context, List<Items.getItems> arrayList){
        this.context=context;
        this.arrayList=arrayList;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_mis_items,viewGroup,false);


        }
        TextView producto = (TextView)view.findViewById(R.id.lst_descripcion);
        TextView cod_interno = (TextView)view.findViewById(R.id.lst_cod_interno);
        TextView cod_alterno = (TextView)view.findViewById(R.id.lst_codigo_alterno);
        TextView tvPVP = (TextView)view.findViewById(R.id.lst_pvp);
        TextView tvStock = (TextView)view.findViewById(R.id.lst_stock);

        producto.setText(arrayList.get(i).getDescripcion());
        cod_interno.setText(arrayList.get(i).getCodigo_interno());
        cod_alterno.setText(arrayList.get(i).getCodigo_alterno());
        tvPVP.setText(arrayList.get(i).getPvp());
        tvStock.setText(arrayList.get(i).getStock());




        return view;
    }
}
