package com.expriceit.maserven.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 30/11/2017.
 */

public interface MisClientes {
    @GET("MisClientesWS")
    Call<MisClientes.getCliente> misClientesWS(@Query("usuario") String usuario,
                                                   @Query("identificacion") String identificacion);

    Call<MisClientes.getCliente> misClientesWS();

    public class getCliente {
        private String mensaje;
        private String codigo;
        private String identificacion;
        private String razon_social;
        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getIdentificacion() {
            return identificacion;
        }

        public void setIdentificacion(String identificacion) {
            this.identificacion = identificacion;
        }

        public String getRazon_social() {
            return razon_social;
        }

        public void setRazon_social(String razon_social) {
            this.razon_social = razon_social;
        }



    }

    @GET("RegistraClienteWS")
    Call<MisClientes.getRegCliente> registraClienteWS(@Query("usuario") String usuario,
                                               @Query("identificacion") String identificacion,
                                                      @Query("cliente") String nombre_cliente);
    Call<MisClientes.getRegCliente> registraClienteWS();
    public class getRegCliente {
        private String mensaje;
        private String codigo;
        private String identificacion;
        private String razon_social;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getIdentificacion() {
            return identificacion;
        }

        public void setIdentificacion(String identificacion) {
            this.identificacion = identificacion;
        }

        public String getRazon_social() {
            return razon_social;
        }

        public void setRazon_social(String razon_social) {
            this.razon_social = razon_social;
        }


    }
}
