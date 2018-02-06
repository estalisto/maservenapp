package com.expriceit.maserven.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.expriceit.maserven.R;

import java.util.List;

/**
 * Created by stalyn on 12/12/2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    private List<String> lstData;
    private Activity activity;
    private LayoutInflater inflater;

    public SpinnerAdapter(List<String> lstData, Activity activity){
        this.lstData=lstData;
        this.activity=activity;
        this.inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int i) {
        return lstData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (convertView==null){
            view = inflater.inflate(R.layout.spinner_tipo_consulta,null);

        }

        TextView    tv = (TextView)view.findViewById(R.id.TextSpinner);
        tv.setText(lstData.get(i));
        return view;
    }
}
