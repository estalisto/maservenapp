package com.expriceit.maserven.entities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 5/12/2017.
 */

public interface Items {

    @GET("ConsultaItemsWS")
    Call<DatosItem> consumeItemsWS(@Query("tipo") String tipo,
                                  @Query("cod_int") String cod_interno,
                                  @Query("cod_alt") String cod_alterno,
                                  @Query("descripcion") String descripcion,
                                  @Query("linea") String linea);

    Call<DatosItem> consumeItemsWS();
/*http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=DES&cod_int=&cod_alt=&descripcion=bro&linea=
* http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=COI&cod_int=01020020014&cod_alt=&descripcion=&linea=*/

    public class DatosItem {
        private List<getItems> data;
        private String mensaje;
        private String codigo_error;

        public List<getItems> getData() {
            return data;
        }

        public void setData(List<getItems> data) {
            this.data = data;
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
    public class getItems{
        private String descripcion;
        private String codigo_interno;
        private String codigo_alterno;
        private String pvp;
        private String stock;
/*        private String mensaje;
        private String codigo_error;*/

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getCodigo_interno() {
            return codigo_interno;
        }

        public void setCodigo_interno(String codigo_interno) {
            this.codigo_interno = codigo_interno;
        }

        public String getCodigo_alterno() {
            return codigo_alterno;
        }

        public void setCodigo_alterno(String codigo_alterno) {
            this.codigo_alterno = codigo_alterno;
        }

        public String getPvp() {
            return pvp;
        }

        public void setPvp(String pvp) {
            this.pvp = pvp;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

/*        public String getMensaje() {
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
        }*/
    }
}
