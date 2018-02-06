package com.expriceit.maserven.entities;

/**
 * Created by stalyn on 4/12/2017.
 */

public class Productos {
    private String descripcion;
    private String codigo_interno;
    private String codigo_alterno;
    private String pvp;
    private String stock;

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



}
