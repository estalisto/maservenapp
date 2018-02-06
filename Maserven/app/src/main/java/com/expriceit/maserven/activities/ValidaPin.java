package com.expriceit.maserven.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.expriceit.maserven.MaservenApplication;
import com.expriceit.maserven.R;
import com.expriceit.maserven.entities.ValidaPinAcceso;
import com.expriceit.maserven.utils.SharedPreferencesManager;
import com.expriceit.maserven.utils.Utils;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by stalyn on 21/11/2017.
 */

public class ValidaPin extends Activity implements View.OnKeyListener {

    EditText pin,edtPin2,edtPin1;
    String PREFERENCIA_INICIO = "maservenapp";
    String KEY_PIN = "pin_acceso";
    String KEY_USER = "usuario";
    LinearLayout layout1,layout2,layout3;
    FancyButton btn_Registra_PIN;
    ProgressBar load_progreesbar;
    private Call<ValidaPinAcceso.getPinAcceso> CallPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valida_pin);

        pin = (EditText) findViewById(R.id.editContrasenia);
        edtPin1 = (EditText) findViewById(R.id.editPin1);
        edtPin2 = (EditText) findViewById(R.id.editPin2);

        load_progreesbar = (ProgressBar) findViewById(R.id.progressBar2);
        btn_Registra_PIN = (FancyButton) findViewById(R.id.btnRegistraPIN);
        layout1 = (LinearLayout) findViewById(R.id.lytpin1);
        layout2 = (LinearLayout) findViewById(R.id.lytpin2);
        layout3 = (LinearLayout) findViewById(R.id.lytpin3);


        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        btn_Registra_PIN.setVisibility(View.GONE);

        if(SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_PIN)!= null ){
            layout3.setVisibility(View.VISIBLE);
            pin.setOnKeyListener(this);
        }else{
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            btn_Registra_PIN.setVisibility(View.VISIBLE);
            btn_Registra_PIN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edtPin1.getText().toString().length()<4){
                        Utils.generarAlerta(ValidaPin.this, "ALERTA!", "El pin debe contener 4 digitos numéricos");
                        return;
                    }

                            if(edtPin1.getText().toString()== ""){
                                Utils.generarAlerta(ValidaPin.this, "ALERTA!", "Existe Campos Vacios");
                                return;
                            }
                            if(edtPin2.getText().toString()== ""){
                                Utils.generarAlerta(ValidaPin.this, "ALERTA!", "Existe Campos Vacios");
                                return;
                            }
                            if(edtPin1.getText().toString().equals(edtPin2.getText().toString())){
                                save_pin(edtPin1.getText().toString());
                                Intent intent = new Intent(ValidaPin.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Utils.generarAlerta(ValidaPin.this, "ALERTA!", "Deben de ser iguales los valores del PIN.");
                            }
                }
            });

        }

    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

            pin = (EditText) findViewById(R.id.editContrasenia);
            Log.w("Acceso_pin >>> ", pin.getText().toString());
            if(pin.getText().toString().length()==4){
            String pinlocal="";
            String pintmp="";

                if(SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_PIN)!= null ){
                    pinlocal=SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_PIN);
                    pinlocal=pinlocal.trim();
                    Log.w("Acceso_pin >>> ", "pinlocal: "+pinlocal);
                    pintmp= pin.getText().toString();
                    pintmp =pintmp.trim();
                    Log.w("Acceso_pin >>> ", "pintemp: "+pintmp);
                    Log.w("Acceso_pin", "Validando preferencias PIN"+SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_PIN));
                    if(pintmp.toString().compareTo(pinlocal.toString())==0){
                        Intent intent = new Intent(ValidaPin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Utils.generarAlerta(ValidaPin.this, "ALERTA!", "El Pin es incorrecto");
                        Log.w("Acceso_pin >>> ", "El Pin regitrado es incorrecto");
                        pin.setText("");
                       // return true;
                    }
                }else {

                    load_progreesbar.setVisibility(View.VISIBLE);
                    Log.w("Acceso_pin", "pin:"+pin.getText().toString());

                    Acceso_pin(SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_USER),pin.getText().toString());

                }




                    return true;
            }else{
                return false;
            }


    }


    private void  Acceso_pin(String Usuario, String clave_pin){
        //load_progreesbar.setVisibility(View.VISIBLE);

        Log.w("Acceso_pin", "ValidaPinAcceso acceso_pin");
        ValidaPinAcceso acceso_pin = MaservenApplication.getApplication().getRestAdapter().create(ValidaPinAcceso.class);
        Log.w("Acceso_pin", "CallPin acceso_pin");
        try{
            CallPin = acceso_pin.validaPinWS(Usuario,clave_pin);
            Log.w("Acceso_pin", "Usuario: "+Usuario+" clave_pin:"+clave_pin);
        } catch (IllegalArgumentException e1) {
            //Toast.makeText(getApplicationContext(),"No se puede conectar con la radio.",Toast.LENGTH_LONG).show();
            Log.w("Acceso_pin", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.w("Acceso_pin", "CallPin");
        CallPin.enqueue(new Callback<ValidaPinAcceso.getPinAcceso>() {




            @Override
            public void onResponse(Call<ValidaPinAcceso.getPinAcceso> call, Response<ValidaPinAcceso.getPinAcceso> response) {
                String err = "";
                try {
                    // err = response.errorBody().toString();
                    Log.w("Acceso_pin", "Consultando respuesta" +err );
                    if (err.equalsIgnoreCase("")) {
                        if (response.body() != null) {
                            if (response.isSuccess()) {
                                ValidaPinAcceso.getPinAcceso otp = response.body();

                                Log.w("Acceso_pin", "response -> "+otp.getMensaje() + "");
                                Log.w("Acceso_pin", "codigo -> "+otp.getCodigo() + "");
                                // Log.w("Acceso_usuario", "idusuario -> "+Usuario+ "");
                                if (otp.getCodigo().equals("1")){

                                   save_pin(pin.getText().toString());

                                    Log.w("Valida PIN >>", "ConsultaWS PIN:"+pin.getText().toString());
                                    Intent intent = new Intent(ValidaPin.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                   load_progreesbar.setVisibility(View.INVISIBLE);

                                }else{
                                    Utils.generarAlerta(ValidaPin.this, "ALERTA!", otp.getMensaje());
                                   load_progreesbar.setVisibility(View.INVISIBLE);
                                    pin.setText("");

                                }

                            } else {
                                Log.e("Acceso_pin", "Error en el webservice, success false");
                                load_progreesbar.setVisibility(View.INVISIBLE);

                            }
                        } else {
                            Log.e("Acceso_pin", "Error de web service, no viene json");
                            load_progreesbar.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Log.e("Acceso_pin", "Error en el webservice " + err);
                        load_progreesbar.setVisibility(View.INVISIBLE);
                    }
                    // Log.w("Acceso_usuario", "ERROR: "+err);
                } catch (Exception e) {
                    // err = "";
                    Log.w("Acceso_pin", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());
                    load_progreesbar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ValidaPinAcceso.getPinAcceso> call, Throwable t) {
                //Log.w("111111", t.getMessage());
                Log.w("Acceso_pin", "onFailure - "+t.getMessage());
                load_progreesbar.setVisibility(View.INVISIBLE);
            }
        });
        Log.w("Acceso_pin", "Fin CallUser");
    }

    private void save_pin(String pin) {

        Log.w("Acceso_pin", "Registrando PIN ->"+pin);
        SharedPreferencesManager.setValor(this,PREFERENCIA_INICIO, pin, KEY_PIN);
        Log.w("Acceso_pin", "PIN Registrado ->"+SharedPreferencesManager.getValorEsperado(this,PREFERENCIA_INICIO,KEY_PIN));
        Log.w("Acceso_pin", "Mostrar Pin: ->"+SharedPreferencesManager.getValorEsperado(this,PREFERENCIA_INICIO,KEY_PIN));



    }


}
