package com.expriceit.maserven.entities;

/**
 * Created by stalyn on 13/12/2017.
 */

public interface ItemListPedidos {

    public class getItems{
        private String descripcion;
        private String codigo_interno;
        private String codigo_alterno;
        private String pvp;
        private String cantidad;
        private String total;
        private String idItem;

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

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getIdItem() {
            return idItem;
        }

        public void setIdItem(String idItem) {
            this.idItem = idItem;
        }
    }
}
