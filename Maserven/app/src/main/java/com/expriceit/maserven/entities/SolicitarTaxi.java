package com.expriceit.maserven.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 30/8/2017.
 */

public interface SolicitarTaxi {

    @GET("despachador/Transaccion/ITransaccion_MIR.php")
   Call<getSolicitarTaxi> solicitarTaxiWS(@Query("id_empresa") String id_empresa,
                                          @Query("idcliente") String idcliente,
                                          @Query("latitud") String latitud,
                                          @Query("longitud") String longitud,
                                          @Query("origen") String origen,
                                          @Query("id_clientesDir") String id_clientesDir,
                                          @Query("dir_cliente") String dir_cliente);

    Call<getSolicitarTaxi> solicitarTaxiWS();
    public class getSolicitarTaxi{
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
