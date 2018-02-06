package com.expriceit.maserven.mismodelos;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

/**
 * Created by stalyn on 9/1/2018.
 */

public class DetallePedidos  extends SugarRecord {





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


    public DetallePedidos() {
    }

    public DetallePedidos(String id_pedido, String num_pedido, String num_fact, String cod_alter, String cod_inter, String linea, String descripcion,String cantidad, String valor, String iva, String total, String estado, String sincronizado) {
        this.id_pedido = id_pedido;
        this.num_pedido = num_pedido;
        this.num_fact = num_fact;
        this.cod_alter = cod_alter;
        this.cod_inter = cod_inter;
        this.linea = linea;
        this.descripcion = descripcion;
        this.cantidad=cantidad;
        this.valor = valor;
        this.iva = iva;
        this.total = total;
        this.estado = estado;
        this.sincronizado = sincronizado;
    }

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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
