package com.example.appsesion.json;

import java.io.Serializable;

public class MyData implements Serializable {
    private String pass;

    private int id_pass;
    private int image;
    private String usuario;
    private int id_usr;

    public int getId_pass() { return id_pass; }

    public void setId_pass(int id_pass){this.id_pass = id_pass;}

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getId_usr(){return id_usr;}

    public void setId_usr(int id_usr){this.id_usr = id_usr;}
}
