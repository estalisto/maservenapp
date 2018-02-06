package com.expriceit.maserven.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 2/8/2017.
 */

public interface AccesoUsuario {
    @GET("UsuarioServicios")
    Call<getAcceso> consumeLoginWS(@Query("usuario") String usuario,
                                   @Query("clave") String clave);

    Call<getAcceso> consumeLoginWS();
    public class getAcceso{
        private String mensaje;
        private String codigo;
        private String cargo;
        private String nombre;
        private String email;

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

        public String getCargo() {
            return cargo;
        }

        public void setCargo(String cargo) {
            this.cargo = cargo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }






    }

}
