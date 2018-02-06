package com.expriceit.maserven.entities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 16/1/2018.
 */

public interface AllConsultaClientesWS {

    @GET("AllConsultaClientesWS")
    Call<DatosCliente> consumeClientesWS(@Query("tipo") String tipo,
                                      @Query("usuario") String usuario,
                                      @Query("identificacion") String identificacion,
                                      @Query("nombre_cliente") String nombre_cliente);

    Call<DatosCliente> consumeClientesWS();


    public class DatosCliente {
        private List<getClientes> data;

        public List<getClientes> getData() {
            return data;
        }

        public void setData(List<getClientes> data) {
            this.data = data;
        }

    }

    public class getClientes{
        private String codigo;
        private String identificacion;
        private String razon_social;

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
