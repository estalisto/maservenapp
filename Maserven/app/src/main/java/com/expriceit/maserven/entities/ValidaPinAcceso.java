package com.expriceit.maserven.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 30/11/2017.
 */

public interface ValidaPinAcceso {

    @GET("ValidaPinAccesoWS")
    Call<ValidaPinAcceso.getPinAcceso> validaPinWS(@Query("usuario") String usuario,
                                                 @Query("clave_pin") String clave_pin);

    Call<ValidaPinAcceso.getPinAcceso> validaPinWS();
    public class getPinAcceso{
        private String mensaje;
        private String codigo;
        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }



        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }





    }
}
