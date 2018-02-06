package com.expriceit.maserven.entities;

/**
 * Created by stalyn on 8/1/2018.
 */
//@Table(name="personamodel")extends Model
public class PersonaModel  {
   // @Column(name="Nombre")
    public String Nombre;

    //@Column(name="Apellido")
    public String Apellido;

    public PersonaModel(){
        super();
    }

    public PersonaModel(String Nombre, String Apellido){
        super();
        this.Nombre=Nombre;
        this.Apellido=Apellido;

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }
}
