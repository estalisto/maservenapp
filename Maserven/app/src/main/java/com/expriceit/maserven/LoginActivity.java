package com.expriceit.maserven;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expriceit.maserven.activities.RecuperarContrasenia;
import com.expriceit.maserven.activities.ValidaPin;
import com.expriceit.maserven.entities.AccesoUsuario;
import com.expriceit.maserven.utils.SharedPreferencesManager;
import com.expriceit.maserven.utils.Utils;

import java.util.regex.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    TextView txtRegistrar, txt_Olvido_Contrasenia;

    EditText editUsuario;
    EditText editContrasenia;
    FancyButton btn_iniciar;
    ProgressBar load_progreesbar;
    private String PREFERENCES_INICIO_TOKEN  = "inicioPreferences";

    String PREFERENCIA_INICIO = "maservenapp";
    String KEY_USER = "usuario";
    String KEY_PASS="pass";
    String IDUSER="idusuario";
    String EMPRESA="2";
    String ORIGENCLI="APPC";
    String PUSHID="fdsfdsfdsfds";
    private String GCM_TOKEN = "gcmToken";
    private Call<AccesoUsuario.getAcceso> CallUser;
    private String TAG = "InPinActivities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsuario = (EditText) findViewById(R.id.editEmail);
        editContrasenia = (EditText) findViewById(R.id.editContrasenia);
        load_progreesbar = (ProgressBar) findViewById(R.id.progressBar2);
        //textOlvidoContrasenia
        txt_Olvido_Contrasenia = (TextView) findViewById(R.id.textOlvidoContrasenia);
        //  = (Button) findViewById(R.id.btnInicar);
        btn_iniciar = (FancyButton) findViewById(R.id.btnInicar);
        Log.w("Acceso_usuario", "Ejecutando el WS");
        btn_iniciar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (!validarEmail(editUsuario.getText().toString())){
                    Utils.generarAlerta(LoginActivity.this, "ALERTA!", "Email no válido");
                    return;
                }
                if (editUsuario.getText().toString().equals("")){
                    Utils.generarAlerta(LoginActivity.this, "ALERTA!", "Debe ingrear el usuario");
                    return;
                }
                if (editContrasenia.getText().toString().equals("")){
                    Utils.generarAlerta(LoginActivity.this, "ALERTA!", "Debe ingrear la Contraseña");
                    return;
                }
                /* provisional*/
              /*  load_progreesbar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(LoginActivity.this, ValidaPin.class);
                startActivity(intent);
                finish();
                save_acces(editUsuario.getText().toString(),editContrasenia.getText().toString(),editContrasenia.getText().toString());
                Intent intent = new Intent(LoginActivity.this, ValidaPin.class);
                startActivity(intent);
                finish();*/
                //TODO se debe habilitar la linea de abajo
                Acceso_usuario(editUsuario.getText().toString(),editContrasenia.getText().toString());
            }
        });

        txt_Olvido_Contrasenia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                /* provisional*/
                load_progreesbar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(LoginActivity.this, RecuperarContrasenia.class);
                startActivity(intent);
                finish();
                //Acceso_usuario(editUsuario.getText().toString(),editContrasenia.getText().toString());
            }
        });
    }

    private void  Acceso_usuario(String Usuario, String password){
        load_progreesbar.setVisibility(View.VISIBLE);
        btn_iniciar.setEnabled(false);
        Log.w("Acceso_usuario", "acceso_login");
        AccesoUsuario acceso_login = MaservenApplication.getApplication().getRestAdapter().create(AccesoUsuario.class);
        Log.w("Acceso_usuario", "acceso_login");
        try{
            CallUser = acceso_login.consumeLoginWS(Usuario.toString(),password.toString());
        } catch (IllegalArgumentException e1) {
            //Toast.makeText(getApplicationContext(),"No se puede conectar con la radio.",Toast.LENGTH_LONG).show();
            Log.w("Acceso_usuario", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
            e1.printStackTrace();
            btn_iniciar.setEnabled(true);
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
            btn_iniciar.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
            btn_iniciar.setEnabled(true);
        }

        Log.w("Acceso_usuario", "CallUser");
        CallUser.enqueue(new Callback<AccesoUsuario.getAcceso>() {
            @Override
            public void onResponse(Call<AccesoUsuario.getAcceso> call, Response<AccesoUsuario.getAcceso> response) {
                String err = "";
                try {
                   // err = response.errorBody().toString();
                    Log.w("Acceso_usuario", "Consultando respuesta" +err );
                    if (err.equalsIgnoreCase("")) {
                        if (response.body() != null) {
                            if (response.isSuccess()) {
                                AccesoUsuario.getAcceso otp = response.body();
                                Log.w("Acceso_usuario", "nombre -> "+otp.getEmail() + "");
                                Log.w("Acceso_usuario", "cargo -> "+otp.getCargo() + "");
                                Log.w("Acceso_usuario", "response -> "+otp.getMensaje() + "");
                                Log.w("Acceso_usuario", "codigo -> "+otp.getCodigo() + "");
                               // Log.w("Acceso_usuario", "idusuario -> "+Usuario+ "");
                                if (otp.getCodigo().equals("1")){
                                    save_acces(editUsuario.getText().toString(),editContrasenia.getText().toString(),editContrasenia.getText().toString());
                                    Intent intent = new Intent(LoginActivity.this, ValidaPin.class);
                                    startActivity(intent);
                                    finish();
                                    load_progreesbar.setVisibility(View.INVISIBLE);

                                }else{
                                    Utils.generarAlerta(LoginActivity.this, "ALERTA!", "Usuario Incorrecto");
                                    load_progreesbar.setVisibility(View.INVISIBLE);
                                    btn_iniciar.setEnabled(true);
                                }

                            } else {
                                Log.e("Acceso_usuario", "Error en el webservice, success false");
                                load_progreesbar.setVisibility(View.INVISIBLE);
                                btn_iniciar.setEnabled(true);
                            }
                        } else {
                            Log.e("Acceso_usuario", "Error de web service, no viene json");
                            load_progreesbar.setVisibility(View.INVISIBLE);
                            btn_iniciar.setEnabled(true);
                        }
                    } else {
                        Log.e("Acceso_usuario", "Error en el webservice " + err);
                        load_progreesbar.setVisibility(View.INVISIBLE);
                        btn_iniciar.setEnabled(true);
                    }
                    // Log.w("Acceso_usuario", "ERROR: "+err);
                } catch (Exception e) {
                    // err = "";
                    Log.w("Acceso_usuario", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());
                    load_progreesbar.setVisibility(View.INVISIBLE);
                    btn_iniciar.setEnabled(true);
                }

            }

            @Override
            public void onFailure(Call<AccesoUsuario.getAcceso> call, Throwable t) {
                //Log.w("111111", t.getMessage());
                Log.w("Acceso_usuario", "onFailure - "+t.getMessage());
                load_progreesbar.setVisibility(View.INVISIBLE);
            }
        });
        Log.w("Acceso_usuario", "Fin CallUser");
    }

    private void save_acces(String usuario, String pass, String idusuario) {

        Log.w("Acceso_usuario", "Registrando USER ->"+usuario);
        SharedPreferencesManager.setValor(this,PREFERENCIA_INICIO, usuario, KEY_USER);
        Log.w("Acceso_usuario", "Registrando PASS ->"+SharedPreferencesManager.getValorEsperado(this,PREFERENCIA_INICIO,KEY_USER));

        SharedPreferencesManager.setValor(this,PREFERENCIA_INICIO, pass, KEY_PASS);
        SharedPreferencesManager.setValor(this,PREFERENCIA_INICIO, idusuario, IDUSER);
        Log.w("Acceso_usuario", "Accesos registrados ->"+pass);
        Log.w("Acceso_usuario", "Mostrar Usuario registrado ->"+SharedPreferencesManager.getValorEsperado(this,PREFERENCIA_INICIO,KEY_USER)+
                " Mostrar Pass registrado ->"+SharedPreferencesManager.getValorEsperado(this,PREFERENCIA_INICIO,KEY_PASS));



    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}
