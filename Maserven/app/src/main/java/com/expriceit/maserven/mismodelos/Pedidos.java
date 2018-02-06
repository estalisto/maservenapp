package com.expriceit.maserven.mismodelos;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stalyn on 9/1/2018.
 */

public class Pedidos extends SugarRecord {

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

    public Pedidos() {
    }

    public Pedidos(String num_pedido, String num_factura, String ident_cliente, String nom_cliente, String val_bruto, String val_descuento, String val_iva, String val_total, String estado, String sincronizado, String fecha_registro, String vendedor,String fecha_sincronizacion) {
        this.num_pedido = num_pedido;
        this.num_factura = num_factura;
        this.ident_cliente = ident_cliente;
        this.nom_cliente = nom_cliente;
        this.val_bruto = val_bruto;
        this.val_descuento = val_descuento;
        this.val_iva = val_iva;
        this.val_total = val_total;
        this.estado = estado;
        this.sincronizado = sincronizado;
        this.fecha_registro = fecha_registro;
        this.vendedor = vendedor;
        this.fecha_sincronizacion = fecha_sincronizacion;
    }

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

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getFecha_sincronizacion() {
        return fecha_sincronizacion;
    }

    public void setFecha_sincronizacion(String fecha_sincronizacion) {
        this.fecha_sincronizacion = fecha_sincronizacion;
    }
}
