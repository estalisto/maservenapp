package com.expriceit.maserven.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.expriceit.maserven.R;
import com.expriceit.maserven.entities.ItemListPedidos;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 * Created by stalyn on 13/12/2017.
 */

public class AdapterListPedidos extends BaseAdapter {
    private Activity activity;
    private List<ItemListPedidos.getItems> arrayList;

    public AdapterListPedidos(Activity activity, List<ItemListPedidos.getItems> arrayList){
        this.activity=activity;
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
            LayoutInflater layoutInflater=(LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_items_pedidos,viewGroup,false);

        }
        TextView tvID = (TextView)view.findViewById(R.id.list_ped_id);
        TextView producto = (TextView)view.findViewById(R.id.list_ped_descripcion);
        TextView cod_interno = (TextView)view.findViewById(R.id.list_ped_cod_int);
        TextView cod_alterno = (TextView)view.findViewById(R.id.list_ped_cod_alt);
        TextView tvPVP = (TextView)view.findViewById(R.id.list_ped_pvp);
        TextView tvCantidad = (TextView)view.findViewById(R.id.list_ped_cant);
        TextView tvTotal = (TextView)view.findViewById(R.id.list_ped_total);



        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("####.##",simbolos);
        tvID.setText(arrayList.get(i).getIdItem());
        producto.setText(arrayList.get(i).getDescripcion());
        cod_interno.setText(arrayList.get(i).getCodigo_interno());
        cod_alterno.setText(arrayList.get(i).getCodigo_alterno());
        tvPVP.setText(formateador.format(Double.parseDouble(arrayList.get(i).getPvp())));
        tvCantidad.setText(formateador.format(Double.parseDouble(arrayList.get(i).getCantidad())));
        tvTotal.setText(formateador.format(Double.parseDouble(arrayList.get(i).getTotal())));
        return view;
    }
}
