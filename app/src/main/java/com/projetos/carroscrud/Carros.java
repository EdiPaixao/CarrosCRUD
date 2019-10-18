package com.projetos.carroscrud;

import java.io.Serializable;

public class Carros implements Serializable {

    private String id;
    private String modelo;
    private String marca;
    private String placa;
    private String ano;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }


    public Carros(String modelo, String marca, String placa, String ano) {
        this.modelo = modelo;
        this.marca = marca;
        this.placa = placa;
        this.ano = ano;
    }



    @Override
    public String toString() {
        return marca + " - " + modelo;
    }

    public String getDados(){
        return "Marca" + marca + "\n Modelo" + modelo + "\n Placa" + placa + "\n Ano" + ano;
    }


}
