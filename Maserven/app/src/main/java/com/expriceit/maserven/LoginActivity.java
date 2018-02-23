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
import android.widget.Toast;

import com.expriceit.maserven.activities.MainActivity;
import com.expriceit.maserven.activities.RecuperarContrasenia;
import com.expriceit.maserven.activities.ValidaPin;
import com.expriceit.maserven.entities.AccesoUsuario;
import com.expriceit.maserven.entities.SyncTodosPedidosWS;
import com.expriceit.maserven.mismodelos.DetallePedidos;
import com.expriceit.maserven.mismodelos.Pedidos;
import com.expriceit.maserven.utils.SharedPreferencesManager;
import com.expriceit.maserven.utils.Utils;

import java.util.concurrent.Exchanger;
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
    private Pedidos pedidos;
    private DetallePedidos detallepedido;
    String PREFERENCIA_INICIO = "maservenapp";
    String KEY_USER = "usuario";
    String KEY_PASS="pass";
    String IDUSER="idusuario";
    String EMPRESA="2";
    String ORIGENCLI="APPC";
    String PUSHID="fdsfdsfdsfds";
    private String GCM_TOKEN = "gcmToken";
    private Call<AccesoUsuario.getAcceso> CallUser;
    private Call<SyncTodosPedidosWS.MisPedidos> callMisPedidosWS;
    private String TAG = "InPinActivities";
    Long idItem;
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
                                    SyncAllMisPedidos(editUsuario.getText().toString());

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


    public void SyncAllMisPedidos(String email){

        Log.w("SyncAllMisPedidos", "Sincronizando:::" +email );
        SyncTodosPedidosWS getMisPedidosWS = MaservenApplication.getApplication().getRestAdapter().create(SyncTodosPedidosWS.class);
        try{
            callMisPedidosWS = getMisPedidosWS.getAllPedidosWS(email);
        } catch (IllegalArgumentException e1) {
            Log.w("SyncAllMisPedidos", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        callMisPedidosWS.enqueue(new Callback<SyncTodosPedidosWS.MisPedidos>() {
            @Override
            public void onResponse(Call<SyncTodosPedidosWS.MisPedidos> call, Response<SyncTodosPedidosWS.MisPedidos> response) {
                String err = "";
                try {
                    // err = response.errorBody().toString();
                    Log.w("SyncAllMisPedidos", "Consultando respuesta" +err );
                    if (err.equalsIgnoreCase("")) {
                        if (response.body() != null) {
                            if (response.isSuccess()) {
                                SyncTodosPedidosWS.MisPedidos otp = response.body();

                                if (otp.getPedidos().size()>0){
                                     long ITEM = 0;
                                    long ID=0;
                                    for (int i = 0; i < otp.getPedidos().size(); i++){
                                        //Toast.makeText(LoginActivity.this, "Total Pedidos:"+otp.getPedidos().size(), Toast.LENGTH_SHORT).show();

                                        ID=0;
                                        pedidos = new Pedidos(otp.getPedidos().get(i).getId_pedido().toString(),otp.getPedidos().get(i).getNum_factura().toString(),otp.getPedidos().get(i).getIdentificacion_cliente().toString(),otp.getPedidos().get(i).getNombre_cliente().toString(),otp.getPedidos().get(i).getSubtotal().toString(),otp.getPedidos().get(i).getDescuento().toString(),otp.getPedidos().get(i).getIva().toString(),otp.getPedidos().get(i).getTotal().toString(),otp.getPedidos().get(i).getEstado().toString(),"S",otp.getPedidos().get(i).getFecha_registro().toString(),SharedPreferencesManager.getValorEsperado(LoginActivity.this,PREFERENCIA_INICIO,KEY_USER),"");
                                         ID = pedidos.save();
                                       // Toast.makeText(LoginActivity.this, "Obteniendo Pedidos... Espere Por favor ID_APP: "+ID+"IDPEDIDO: "+otp.getPedidos().get(i).getId_pedido().toString()+" Cliente: "+otp.getPedidos().get(i).getNombre_cliente().toString()+" estado:"+otp.getPedidos().get(i).getEstado()+" => Detalle cout: "+otp.getPedidos().get(i).getDetalle().size(), Toast.LENGTH_SHORT).show();
                                        Log.w("Detalle:=>>","ID_PEDIDO_INTERNO"+ID);

                                        double Cantidad=0;
                                        double precioUnitario=0, precioUnit2=0;
                                        double Total=0;


                                        for (int j = 0; j < otp.getPedidos().get(i).getDetalle().size(); i++){
                                            try{
                                                Cantidad = Double.parseDouble(otp.getPedidos().get(i).getDetalle().get(0).getCantidad());
                                                precioUnitario = Double.parseDouble(otp.getPedidos().get(i).getDetalle().get(0).getValor());
                                                Total=Cantidad*precioUnitario;

                                            }catch (Exception e){
                                                Log.w("Detalle:=>>","Error: "+e.getMessage());
                                            }
                                          //  Toast.makeText(LoginActivity.this, "Obteniendo DETALLE Pedidos... Espere Por favor ID_APP: "+Long.toString(ID)+" ID PEdido"+ otp.getPedidos().get(i).getId_pedido().toString()+" factura:"+  otp.getPedidos().get(i).getNum_factura().toString()+" cod_alter "+ otp.getPedidos().get(i).getDetalle().get(j).getCodigo_alterno().toString()+" cod_inter "+ otp.getPedidos().get(i).getDetalle().get(j).getCodigo_interno().toString()+" linea: "+  "linea"+"descripcion "+  otp.getPedidos().get(i).getDetalle().get(j).getDescripcion().toString()+" cant "+ otp.getPedidos().get(i).getDetalle().get(j).getCantidad().toString()+" Precio "+ otp.getPedidos().get(i).getDetalle().get(j).getValor().toString()+" iva "+ Double.toString(Total*.12)+" Total "+ Double.toString(Total)+" estado "+ otp.getPedidos().get(i).getDetalle().get(j).getEstado().toString()+" Sincronizado "+ "S", Toast.LENGTH_SHORT).show();
                                            Log.w("Detalle:=>>", "Obteniendo DETALLE Pedidos... Espere Por favor ID_APP: "+Long.toString(ID)+" ID PEdido"+ otp.getPedidos().get(i).getId_pedido().toString()+" factura:"+  otp.getPedidos().get(i).getNum_factura().toString()+" cod_alter "+ otp.getPedidos().get(i).getDetalle().get(j).getCodigo_alterno().toString()+" cod_inter "+ otp.getPedidos().get(i).getDetalle().get(j).getCodigo_interno().toString()+" linea: "+  "linea"+"descripcion "+  otp.getPedidos().get(i).getDetalle().get(j).getDescripcion().toString()+" cant "+ otp.getPedidos().get(i).getDetalle().get(j).getCantidad().toString()+" Precio "+ otp.getPedidos().get(i).getDetalle().get(j).getValor().toString()+" iva "+ Double.toString(Total*.12)+" Total "+ Double.toString(Total)+" estado "+ otp.getPedidos().get(i).getDetalle().get(j).getEstado().toString()+" Sincronizado "+ "S");
                                            try {
                                                detallepedido = new DetallePedidos(Long.toString(ID),"0", "0", otp.getPedidos().get(i).getDetalle().get(j).getCodigo_alterno(), otp.getPedidos().get(i).getDetalle().get(j).getCodigo_interno(), "linea", otp.getPedidos().get(i).getDetalle().get(j).getDescripcion(), otp.getPedidos().get(i).getDetalle().get(j).getCantidad(), otp.getPedidos().get(i).getDetalle().get(j).getValor(), Double.toString(Total * .12), Double.toString(Total), "A", "N");
                                                ITEM = detallepedido.save();
                                            }catch (Exception e){
                                                Log.e("Detalle:=>>", "Error en el Insert"+e.getMessage());
                                            }
                                           /* MainActivity InsertDet = new MainActivity();
                                            idItem =InsertDet.registra_items_pedido(Long.toString(ID),"0","0",otp.getPedidos().get(i).getDetalle().get(j).getCodigo_alterno(),
                                                    otp.getPedidos().get(i).getDetalle().get(j).getCodigo_interno(),"",otp.getPedidos().get(i).getDetalle().get(j).getDescripcion(),Double.toString(Cantidad),Double.toString(precioUnitario),Double.toString(Total*.12),Double.toString(Total+(Total*.12)),"A","S");
                                                    */

                                            Toast.makeText(LoginActivity.this, "Detalle Pedido... Sincronizado"+ITEM, Toast.LENGTH_SHORT).show();
                                        }


                                    }


                                }else{
                                    Toast.makeText(LoginActivity.this, "No hay pedidos que sincronizar...", Toast.LENGTH_SHORT).show();
                                    Log.e("SyncAllMisPedidos", "No hay pedidos que sincronizar...");
                                }

                            } else {
                                Log.e("SyncAllMisPedidos", "Error en el webservice, success false");

                            }
                        } else {
                            Log.e("SyncAllMisPedidos", "Error de web service, no viene json");
                            //btn_sync.setEnabled(true);
                           // Toast.makeText(LoginActivity.this, "Problemas con el servicio,  comuniquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Log.e("SyncAllMisPedidos", "Error en el webservice " + err);

                    }
                } catch (Exception e) {


                   // Toast.makeText(LoginActivity.this, "Problemas con el servicio--->,  comuniquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();
                    Log.w("SyncAllMisPedidos", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());


                }
            }

            @Override
            public void onFailure(Call<SyncTodosPedidosWS.MisPedidos> call, Throwable t) {
                Log.w("SyncAllMisPedidos", "onFailure - "+t.getMessage());
                Toast.makeText(LoginActivity.this, "No hay pedidos que sincronizar...", Toast.LENGTH_SHORT).show();

            }
        });
    }




}
