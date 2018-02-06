package com.expriceit.maserven;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.expriceit.maserven.activities.ValidaPin;
import com.expriceit.maserven.entities.AccesoUsuario;
import com.expriceit.maserven.utils.SharedPreferencesManager;

import retrofit2.Call;

public class SplashScreen extends Activity {

    private Call<AccesoUsuario.getAcceso> CallUser;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String TAG = "InPinActivities";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String PREFERENCES_INICIO_TOKEN  = "inicioPreferences";

    private String GCM_TOKEN = "gcmToken";

    String PREFERENCIA_INICIO = "maservenapp";
    String KEY_USER = "usuario";
    String KEY_IVA = "iva";
    String IDUSER="idusuario";
    String KEY_PASS="pass";
    String EMPRESA="2";
    String ORIGENCLI="APPC";
    String PUSHID="fdsfdsfdsfds";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.w("Splash_Screen", "Valida las credenciales si estan vacias");
                //// TODO: 12/1/2018 Agregando IVA por default 12%
                if(SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_IVA)== null){
                    SharedPreferencesManager.setValor(getApplicationContext(),PREFERENCIA_INICIO, "12", KEY_IVA);
                }

                Log.w("Splash_Screen", "Valida las credenciales si estan vacias");
                Log.w("Splash_Screen", "registrado"+SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_USER));
                if(SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_USER)!= null && SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_PASS)!= null){
                    Log.w("Splash_Screen", "Valida el acceso");
                    Intent intent = new Intent(SplashScreen.this, ValidaPin.class);
                    startActivity(intent);
                    finish();
                   // Acceso_usuario(SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_USER), SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_PASS));

                }else{
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }





            }
        },2000);
    }
}
