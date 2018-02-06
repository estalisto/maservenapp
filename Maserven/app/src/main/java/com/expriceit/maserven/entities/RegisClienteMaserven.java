package com.expriceit.maserven.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 14/12/2017.
 */

public interface RegisClienteMaserven {

    @GET("UsuarioServicios")
    Call<getCliente> consumeDatosClienteWS(@Query("usuario") String usuario,
                                           @Query("identificacion") String identificacion,
                                           @Query("razon_social") String razon_social);

    Call<getCliente> consumeDatosClienteWS();
    public class getCliente{
        private String codigo;
        private String identificacion;
        private String razonsocial;
        private String mensaje;
        private String codigo_error;

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

        public String getRazonsocial() {
            return razonsocial;
        }

        public void setRazonsocial(String razonsocial) {
            this.razonsocial = razonsocial;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getCodigo_error() {
            return codigo_error;
        }

        public void setCodigo_error(String codigo_error) {
            this.codigo_error = codigo_error;
        }
    }
}
