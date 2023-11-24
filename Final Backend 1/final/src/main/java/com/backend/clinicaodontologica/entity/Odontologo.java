package com.backend.clinicaodontologica.entity;

import javax.persistence.*;

@Entity
@Table(name = "ODONTOLOGOS")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String matricula;
    @Column(length = 50)
    private String nombre;
    @Column(length = 50)
    private String apellido;


    public Odontologo(String numeroMatricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroMatricula() {
        return matricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Id: " + id + " - Nombre: " + nombre + " - Apellido: " + apellido + " - Matricula: " + matricula;
    }
}

