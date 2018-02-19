package com.expriceit.maserven.mismodelos;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by stalyn on 9/1/2018.
 */

    public class Clientes  extends SugarRecord {

    public String codigo;
    public String idetificacion;
    public String nombre_cliente;

    public Clientes(String codigo, String idetificacion, String nombre_cliente) {
        this.codigo = codigo;
        this.idetificacion = idetificacion;
        this.nombre_cliente = nombre_cliente;
    }

    public Clientes() {

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIdetificacion() {
        return idetificacion;
    }

    public void setIdetificacion(String idetificacion) {
        this.idetificacion = idetificacion;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }
}
