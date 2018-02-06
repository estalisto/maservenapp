package com.expriceit.maserven.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.expriceit.maserven.R;
import com.expriceit.maserven.entities.AllConsultaClientesWS;

import java.util.List;

/**
 * Created by stalyn on 16/1/2018.
 */

public class AdapterMisClientes extends BaseAdapter {
    private Context context;
    private List<AllConsultaClientesWS.getClientes> arrayList;
    public AdapterMisClientes(Context context, List<AllConsultaClientesWS.getClientes> arrayList){
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
            view = layoutInflater.inflate(R.layout.lista_clientes,viewGroup,false);


        }
        TextView codigo = (TextView)view.findViewById(R.id.tv_lista_cliente_codigo);
        TextView identificacion = (TextView)view.findViewById(R.id.tv_lista_cliente_identificacion);
        TextView nombre = (TextView)view.findViewById(R.id.tv_lista_cliente_nombre);

        codigo.setText(arrayList.get(i).getCodigo());
        identificacion.setText(arrayList.get(i).getIdentificacion());
        nombre.setText(arrayList.get(i).getRazon_social());
        return view;
    }
}
