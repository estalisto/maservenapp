package com.expriceit.maserven.entities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 22/2/2018.
 */

public interface SyncTodosPedidosWS {




    @GET("ConsultarMisPedidosWS")
    Call<MisPedidos> getAllPedidosWS(@Query("email") String emaila);
  //  Call<MisPedidos> setDatosEnviadosWS();


public class MisPedidos{
    private List<MiPedido> pedidos;

    public List<MiPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<MiPedido> pedidos) {
        this.pedidos = pedidos;
    }
}

    public class  MiPedido{
        private String id_pedido;
        private String num_factura;
        private String identificacion_cliente;
        private String nombre_cliente;
        private String subtotal;
        private String descuento;
        private String iva;
        private String total;
        private String fecha_registro;
        private String estado;
        private String sincronizado;
        private List<MiDetallePedido> detalle;

        public String getId_pedido() {
            return id_pedido;
        }

        public void setId_pedido(String id_pedido) {
            this.id_pedido = id_pedido;
        }

        public String getNum_factura() {
            return num_factura;
        }

        public void setNum_factura(String num_factura) {
            this.num_factura = num_factura;
        }

        public String getIdentificacion_cliente() {
            return identificacion_cliente;
        }

        public void setIdentificacion_cliente(String identificacion_cliente) {
            this.identificacion_cliente = identificacion_cliente;
        }

        public String getNombre_cliente() {
            return nombre_cliente;
        }

        public void setNombre_cliente(String nombre_cliente) {
            this.nombre_cliente = nombre_cliente;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getDescuento() {
            return descuento;
        }

        public void setDescuento(String descuento) {
            this.descuento = descuento;
        }

        public String getIva() {
            return iva;
        }

        public void setIva(String iva) {
            this.iva = iva;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFecha_registro() {
            return fecha_registro;
        }

        public void setFecha_registro(String fecha_registro) {
            this.fecha_registro = fecha_registro;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getSincronizado() {
            return sincronizado;
        }

        public void setSincronizado(String sincronizado) {
            this.sincronizado = sincronizado;
        }

        public List<MiDetallePedido> getDetalle() {
            return detalle;
        }

        public void setDetalle(List<MiDetallePedido> detalle) {
            this.detalle = detalle;
        }
    }




    public class MiDetallePedido{

        private String id_pedido;
        private String codigo_interno;
        private String codigo_alterno;
        private String descripcion;
        private String valor;
        private String cantidad;
        private String total;
        private String estado;
        private String fecha_registro;

        public String getId_pedido() {
            return id_pedido;
        }

        public void setId_pedido(String id_pedido) {
            this.id_pedido = id_pedido;
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

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
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

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getFecha_registro() {
            return fecha_registro;
        }

        public void setFecha_registro(String fecha_registro) {
            this.fecha_registro = fecha_registro;
        }
    }




}
