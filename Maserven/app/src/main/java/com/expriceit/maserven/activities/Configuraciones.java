package com.expriceit.maserven.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.expriceit.maserven.R;
import com.expriceit.maserven.utils.SharedPreferencesManager;
import com.expriceit.maserven.utils.Utils;

public class Configuraciones extends Activity {
    TextView tv_iva,tc_procentale_iva;
    EditText ediIVA;
    ImageButton guardarIVA, editar_IVA;

    private String PREFERENCES_INICIO_TOKEN  = "inicioPreferences";
    String PREFERENCIA_INICIO = "maservenapp";
    String KEY_IVA = "iva";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        guardarIVA = (ImageButton) findViewById(R.id.img_btn_guardar_iva);
        editar_IVA = (ImageButton) findViewById(R.id.img_btn_editar_iva);
        tv_iva = (TextView) findViewById(R.id.tv_valor_iva);
        tc_procentale_iva = (TextView) findViewById(R.id.tv_porcentaje);
        ediIVA = (EditText) findViewById(R.id.edit_valor_iva);
        tc_procentale_iva.setVisibility(View.VISIBLE);
        tv_iva.setVisibility(View.VISIBLE);
        ediIVA.setVisibility(View.GONE);
        guardarIVA.setVisibility(View.GONE);
        tv_iva.setText(SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_IVA));

        editar_IVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ediIVA.setVisibility(View.VISIBLE);
                tv_iva.setVisibility(View.GONE);
                tc_procentale_iva.setVisibility(View.GONE);
                guardarIVA.setVisibility(View.VISIBLE);
                editar_IVA.setVisibility(View.GONE);



            }
        });


        guardarIVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ediIVA.getText().toString().isEmpty()){
                    Utils.generarAlerta(Configuraciones.this, "Mensaje!", "Debe Ingresar un Valor de IVA");
                    return;
                }
                if(Integer.parseInt(ediIVA.getText().toString())>50){
                    Utils.generarAlerta(Configuraciones.this, "Mensaje!", "El Valor de IVA excede el porcentaje permitido");
                    return;
                }
                if(Integer.parseInt(ediIVA.getText().toString())<=0){
                    Utils.generarAlerta(Configuraciones.this, "Mensaje!", "El Valor de IVA debe ser mayor a CERO");
                    return;
                }
                //
                SharedPreferencesManager.deleteValor(getApplicationContext(),PREFERENCIA_INICIO,KEY_IVA);
                SharedPreferencesManager.setValor(getApplicationContext(),PREFERENCIA_INICIO,ediIVA.getText().toString(), KEY_IVA);
                //Toast.makeText(Configuraciones.this, "Valor del IVA EDIT"+ediIVA.getText().toString(), Toast.LENGTH_SHORT).show();
                SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO, KEY_IVA);
                //Toast.makeText(Configuraciones.this, "Valor del IVA SharedPreferencesManager"+SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO, KEY_IVA), Toast.LENGTH_SHORT).show();

                tv_iva.setText(ediIVA.getText().toString());

                ediIVA.setVisibility(View.GONE);
                tv_iva.setVisibility(View.VISIBLE);
                tc_procentale_iva.setVisibility(View.VISIBLE);
                guardarIVA.setVisibility(View.GONE);
                editar_IVA.setVisibility(View.VISIBLE);
                Toast.makeText(Configuraciones.this, "Valor del IVA Actualizado", Toast.LENGTH_SHORT).show();




            }
        });


    }
}
