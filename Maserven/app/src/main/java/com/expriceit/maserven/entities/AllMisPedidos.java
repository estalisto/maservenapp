package com.expriceit.maserven.entities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stalyn on 22/1/2018.
 */

public interface AllMisPedidos {

    @GET("ConsultaItemsWS")
    Call<getPedidos> consumeAllPedidosWS(@Query("usuario") String usuario);

    Call<getPedidos> consumeAllPedidosWS();


    public  class getPedidos{
        public List<getAllMisPedidos> pedidos;

        public List<getAllMisPedidos> getPedidos() {
            return pedidos;
        }

        public void setPedidos(List<getAllMisPedidos> pedidos) {
            this.pedidos = pedidos;
        }
    }
    public class getAllMisPedidos{
        public String num_pedido;
        public String num_factura;
        public String ident_cliente;
        public String nom_cliente;
        public String val_bruto;
        public String val_descuento;
        public String val_iva;
        public String val_total;
        public String estado;
        public String sincronizado;
        public String fecha_registro;
        public String fecha_sincronizacion;
        public String vendedor;
        public List<getAllDetallePedidos> detalle;

        public String getNum_pedido() {
            return num_pedido;
        }

        public void setNum_pedido(String num_pedido) {
            this.num_pedido = num_pedido;
        }

        public String getNum_factura() {
            return num_factura;
        }

        public void setNum_factura(String num_factura) {
            this.num_factura = num_factura;
        }

        public String getIdent_cliente() {
            return ident_cliente;
        }

        public void setIdent_cliente(String ident_cliente) {
            this.ident_cliente = ident_cliente;
        }

        public String getNom_cliente() {
            return nom_cliente;
        }

        public void setNom_cliente(String nom_cliente) {
            this.nom_cliente = nom_cliente;
        }

        public String getVal_bruto() {
            return val_bruto;
        }

        public void setVal_bruto(String val_bruto) {
            this.val_bruto = val_bruto;
        }

        public String getVal_descuento() {
            return val_descuento;
        }

        public void setVal_descuento(String val_descuento) {
            this.val_descuento = val_descuento;
        }

        public String getVal_iva() {
            return val_iva;
        }

        public void setVal_iva(String val_iva) {
            this.val_iva = val_iva;
        }

        public String getVal_total() {
            return val_total;
        }

        public void setVal_total(String val_total) {
            this.val_total = val_total;
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

        public String getFecha_registro() {
            return fecha_registro;
        }

        public void setFecha_registro(String fecha_registro) {
            this.fecha_registro = fecha_registro;
        }

        public String getFecha_sincronizacion() {
            return fecha_sincronizacion;
        }

        public void setFecha_sincronizacion(String fecha_sincronizacion) {
            this.fecha_sincronizacion = fecha_sincronizacion;
        }

        public String getVendedor() {
            return vendedor;
        }

        public void setVendedor(String vendedor) {
            this.vendedor = vendedor;
        }

        public List<getAllDetallePedidos> getDetalle() {
            return detalle;
        }

        public void setDetalle(List<getAllDetallePedidos> detalle) {
            this.detalle = detalle;
        }
    }
    public class getAllDetallePedidos{
    public String id_pedido;//creates column idpedido
    public String num_pedido;
    public String num_fact;
    public String cod_alter;
    public String cod_inter;
    public String linea;
    public String descripcion;
    public String cantidad;
    public String valor;
    public String iva;
    public String total;
    public String estado;
    public String sincronizado;

        public String getId_pedido() {
            return id_pedido;
        }

        public void setId_pedido(String id_pedido) {
            this.id_pedido = id_pedido;
        }

        public String getNum_pedido() {
            return num_pedido;
        }

        public void setNum_pedido(String num_pedido) {
            this.num_pedido = num_pedido;
        }

        public String getNum_fact() {
            return num_fact;
        }

        public void setNum_fact(String num_fact) {
            this.num_fact = num_fact;
        }

        public String getCod_alter() {
            return cod_alter;
        }

        public void setCod_alter(String cod_alter) {
            this.cod_alter = cod_alter;
        }

        public String getCod_inter() {
            return cod_inter;
        }

        public void setCod_inter(String cod_inter) {
            this.cod_inter = cod_inter;
        }

        public String getLinea() {
            return linea;
        }

        public void setLinea(String linea) {
            this.linea = linea;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
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
    }
}
