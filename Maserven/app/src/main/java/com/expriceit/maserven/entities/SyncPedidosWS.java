package com.expriceit.maserven.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 7/2/2018.
 */

public interface SyncPedidosWS {

    /**
     * http://localhost:7777/MaservenWS/app/RegistraPedidosWS?=232&=0100707207001&=2&=112&=100&=0&=12&=bareca90@gmail.com
     */

    @GET("RegistraPedidosWS")
    Call<DatosPedidos> setDatosPedidosWS(@Query("num_pedido") String num_pedido,
                                      @Query("identificacion") String identificacion,
                                      @Query("numero_ped_app") String numero_ped_app,
                                      @Query("total_pedido") String total_pedido,
                                      @Query("subtotal") String subtotal,
                                      @Query("valor_dscto") String valor_dscto,
                                      @Query("valor_iva") String valor_iva,
                                      @Query("email") String emaila);
    Call<DatosPedidos> setDatosPedidosWS();


    /*
    * http://localhost:7777/MaservenWS/app/RegistraDetallePedidosWS?num_pedido=22&codigo_interno=01030050031&
    * codigo_alterno=SGI108&cantidad=3&precio=7&fecha_creacion=2018-02-07%2016:00:00&
    * fecha_actualizacion=2018-02-07%2016:00:00&estado=A&descuento=0
    * */


    @GET("RegistraDetallePedidosWS")
    Call<DatosDetPedidos> setDatosDetPedidosWS(@Query("num_pedido") String num_pedido,
                                         @Query("codigo_interno") String codigo_interno,
                                         @Query("codigo_alterno") String codigo_alterno,
                                         @Query("cantidad") String cantidad,
                                         @Query("precio") String precio,
                                         @Query("fecha_creacion") String fecha_creacion,
                                         @Query("fecha_actualizacion") String fecha_actualizacion,
                                         @Query("estado") String estado,
                                         @Query("descuento") String descuento);
    Call<DatosDetPedidos> setDatosDetPedidosWS();


    @GET("EnviarDocumento")
    Call<DatosEnviados> setDatosEnviadosWS(@Query("num_pedido") String num_pedido,
                                               @Query("nombre_archivo") String nombre_archivo,
                                               @Query("email") String email);
    Call<DatosEnviados> setDatosEnviadosWS();
    public class DatosPedidos {
        private String id_pedido;
        private String nombre_archivo;
        public String getId_pedido() {
            return id_pedido;
        }

        public void setId_pedido(String id_pedido) {
            this.id_pedido = id_pedido;
        }

        public String getNombre_archivo() {
            return nombre_archivo;
        }

        public void setNombre_archivo(String nombre_archivo) {
            this.nombre_archivo = nombre_archivo;
        }
    }
    public class DatosDetPedidos {
        private String id_pedido;
        public String getId_pedido() {
            return id_pedido;
        }

        public void setId_pedido(String id_pedido) {
            this.id_pedido = id_pedido;
        }



    }
    public class DatosEnviados {
        private String codigo;
        private String mensaje;

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
