package com.example.appsesion.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyInfo implements Serializable {

    private int id_usr;
    private String usuario;
    private String password;
    private String correo;
    private String nombre;
    private String[] medios;

    private int gen;
    private String box1;
    private String box2;
    private String box3;
    private Boolean genero;
    private String fecha;
    private String cel;
    private String lugar;

    private int batiz;

    public int getId_usr(){return id_usr;}

    public void setId_usr(int id_usr){this.id_usr = id_usr;}
    public List<MyData> getContras() {
        return contras;
    }

    public void setContras(List<MyData> contras) {
        this.contras = contras;
    }

    private List<MyData> contras= new ArrayList<>();

    public String getBox1(){return box1;}
    public void setBox1(String box1){this.box1 = box1;}

    public String getBox2(){return box2;}
    public void setBox2(String box1){this.box2 = box2;}

    public String getBox3(){return box3;}
    public void setBox3(String box1){this.box3 = box3;}
    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getBatiz(){return batiz;}
    public void setBatiz(int batiz){this.batiz = batiz;}
    public MyInfo() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String[] getMedios() {
        return medios;
    }

    public void setMedios(String[] medios) {
        this.medios = medios;
    }

    public Boolean getGenero() {
        return genero;
    }

    public void setGenero(Boolean genero) {
        this.genero = genero;
    }

    public int getGen(){return gen;}

    public void setGen(int gen){this.gen = gen;}
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getNombre(){return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}
}
