package com.expriceit.maserven.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.expriceit.maserven.R;
import com.expriceit.maserven.mismodelos.DetallePedidos;

import java.util.ArrayList;

/**
 * Created by stalyn on 11/1/2018.
 */

public class AdapterListMiDetPedido extends BaseAdapter {
    private Context context;
    private ArrayList<DetallePedidos> detallePedidosArrayList;


    public AdapterListMiDetPedido(Context context, ArrayList<DetallePedidos> detallePedidosArrayList) {
        this.context = context;
        this.detallePedidosArrayList = detallePedidosArrayList;
    }

    @Override
    public int getCount() {
        return detallePedidosArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return detallePedidosArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_items_pedidos,viewGroup,false);

        }
        TextView producto = (TextView)view.findViewById(R.id.list_ped_descripcion);
        TextView cod_interno = (TextView)view.findViewById(R.id.list_ped_cod_int);
        TextView cod_alterno = (TextView)view.findViewById(R.id.list_ped_cod_alt);
        TextView tvPVP = (TextView)view.findViewById(R.id.list_ped_pvp);
        TextView tvCantidad = (TextView)view.findViewById(R.id.list_ped_cant);
        TextView tvTotal = (TextView)view.findViewById(R.id.list_ped_total);

        producto.setText(detallePedidosArrayList.get(i).getDescripcion());
        cod_interno.setText(detallePedidosArrayList.get(i).getCod_inter());
        cod_alterno.setText(detallePedidosArrayList.get(i).getCod_alter());
        tvPVP.setText(detallePedidosArrayList.get(i).getValor());
        tvCantidad.setText(detallePedidosArrayList.get(i).getCantidad());
        tvTotal.setText(detallePedidosArrayList.get(i).getTotal());
        return view;
    }
}
