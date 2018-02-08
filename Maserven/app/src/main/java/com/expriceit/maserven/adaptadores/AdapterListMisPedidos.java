package com.expriceit.maserven.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.expriceit.maserven.R;
import com.expriceit.maserven.mismodelos.DetallePedidos;
import com.expriceit.maserven.mismodelos.Pedidos;

import java.util.ArrayList;

/**
 * Created by stalyn on 10/1/2018.
 */

public class AdapterListMisPedidos extends BaseAdapter {
    private Context context;
    private ArrayList<Pedidos> pedidosArrayList;

    public AdapterListMisPedidos(Context context, ArrayList<Pedidos> pedidosArrayList) {
        this.context = context;
        this.pedidosArrayList = pedidosArrayList;
    }

    @Override
    public int getCount() {
        return pedidosArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return pedidosArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.mis_pedidos_diarios,viewGroup,false);
        }
        TextView id_pedido = (TextView) view.findViewById(R.id.tv_list_pedidos_id);
        TextView num_pedido = (TextView) view.findViewById(R.id.tv_list_pedidos_num_pedido);
        TextView num_factura = (TextView) view.findViewById(R.id.tv_list_pedidos_num_fact);
        TextView identificacion = (TextView) view.findViewById(R.id.tv_list_pedidos_ident);
        TextView nom_cliente = (TextView) view.findViewById(R.id.tv_list_pedidos_cliente);
        TextView subtotal = (TextView) view.findViewById(R.id.tv_list_pedidos_subt);
        TextView descuento = (TextView) view.findViewById(R.id.tv_list_pedidos_desc);
        TextView iva = (TextView) view.findViewById(R.id.tv_list_pedidos_iva);
        TextView total = (TextView) view.findViewById(R.id.tv_list_pedidos_total);
        TextView fecha_registro = (TextView) view.findViewById(R.id.tv_list_pedidos_fecha_pedido);
        TextView fecha_sincronizacion = (TextView) view.findViewById(R.id.tv_list_pedidos_fecha_sync);
        ImageButton btn_sincronizar = (ImageButton)view.findViewById(R.id.bt_list_pedidos_sync);
        TextView sync_ok = (TextView) view.findViewById(R.id.txt_syncOK);
        ViewGroup layout_sync_ok = (ViewGroup) view.findViewById(R.id.layout_sync);



        id_pedido.setText(Long.toString(pedidosArrayList.get(i).getId()));
        num_pedido.setText(pedidosArrayList.get(i).getNum_pedido());
        num_factura.setText(pedidosArrayList.get(i).getNum_factura());
        identificacion.setText(pedidosArrayList.get(i).getIdent_cliente());
        nom_cliente.setText(pedidosArrayList.get(i).getNom_cliente());
        subtotal.setText(pedidosArrayList.get(i).getVal_bruto());
        descuento.setText(pedidosArrayList.get(i).getVal_descuento());
        iva.setText(pedidosArrayList.get(i).getVal_iva());
        total.setText(pedidosArrayList.get(i).getVal_total());
        fecha_registro.setText(pedidosArrayList.get(i).getFecha_registro());
        fecha_sincronizacion.setText(pedidosArrayList.get(i).getFecha_sincronizacion());
        if(pedidosArrayList.get(i).getSincronizado().equals("N")){
            sync_ok.setText(":(");
            layout_sync_ok.setBackgroundColor(Color.parseColor("#f4dfbc"));
            btn_sincronizar.setBackgroundResource(R.drawable.ic_highlight_off_black_24dp);
        }else{
            layout_sync_ok.setBackgroundColor(Color.parseColor("#eff9e1"));
            sync_ok.setText("OK");
            btn_sincronizar.setBackgroundResource(R.drawable.ic_done_all_black_24dp);
        }






        return view;
    }
}
