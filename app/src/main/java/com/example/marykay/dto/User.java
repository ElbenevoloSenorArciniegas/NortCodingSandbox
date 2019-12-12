package com.example.marykay.dto;

import androidx.collection.SimpleArrayMap;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    public String nombre;
    public List<Revision> conformes;
    public List<Revision> noConformes;
    public List<Revision> pendientes;
    public List<Revision> canceladas;

    public User(String nombre, List<Revision> conformes, List<Revision> noConformes, List<Revision> pendientes, List<Revision> canceladas){
        this.nombre=nombre;
        this.conformes=conformes;
        this.noConformes=noConformes;
        this.pendientes=pendientes;
        this.canceladas=canceladas;
    }

    public User(){
        this.conformes=new ArrayList<>();
        this.noConformes=new ArrayList<>();
        this.pendientes=new ArrayList<>();
        this.canceladas=new ArrayList<>();
    }

}
