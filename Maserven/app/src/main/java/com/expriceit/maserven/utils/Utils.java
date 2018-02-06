package com.expriceit.maserven.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Stalyn on 10/07/2016.
 */
public class Utils {
    public static final String PIN = "PIN";
    public static String NOMBRE_PREFERENCIA = "APP";
    public static String USUARIO = "USUARIO";
    public static String PASSWORD = "PASSWORD";
    public static String CLAVE_SECRETA = "CLAVE_SECRETA";
    public static String PERIODO = "PERIODO";
    public static  void generarAlerta(Activity act , String Title , String msm)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(act);
        alert.setTitle("" + Title);
        alert.setMessage("" + msm);
        alert.setPositiveButton("OK", null);
        alert.show();
    }

   /* public static void savePin(String pines, String fecha_registro){
        PinEntities pin = new PinEntities();
        pin.setPin(pines);
        pin.setFecha_registro(fecha_registro);
        try {
            TaxiApplication.getApplication().getmPinDao().create(pin);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public static boolean isOnline(Activity act)
    {
        ConnectivityManager cm =
                (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
