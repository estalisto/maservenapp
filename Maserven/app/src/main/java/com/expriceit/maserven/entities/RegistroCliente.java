package com.expriceit.maserven.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 15/9/2017.
 */

public interface RegistroCliente {

    @GET("app/clientes/resgitra_clientes.php")
    Call<getRegistraCliente> RegistraClienteWS(@Query("id_empresa") String id_empresa,
                                               @Query("id_empresa_cliente") String id_empresa_cliente,
                                               @Query("nombres") String nombres,
                                               @Query("apellidos") String apellidos,
                                               @Query("cedula_ruc") String cedula_ruc,
                                               @Query("telefonos") String telefonos,
                                               @Query("celular") String celular,
                                               @Query("correo") String correo,
                                               @Query("clave") String clave,
                                               @Query("origen") String origen);

    Call<getRegistraCliente> RegistraCliente();
    public class getRegistraCliente{
        private String respuesta;
        private String codigo;

        public String getRespuesta() {
            return respuesta;
        }

        public void setRespuesta(String respuesta) {
            this.respuesta = respuesta;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }
    }

}
