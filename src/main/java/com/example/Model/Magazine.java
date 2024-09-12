package com.example.Model;


import java.util.Date;

public class Magazine extends Document {
    private String numero;


    public Magazine() {
       
    }

    public Magazine(String titre, String auteur, Date datePublication, long nombresPages,String type,String numero) {
        super(titre, auteur, datePublication, nombresPages,type);
        this.numero = numero;
    }


    public Magazine(Long id,String titre, String auteur, Date datePublication, long nombresPages,String type,String numero) {
        super(id,titre, auteur, datePublication, nombresPages,type);
        this.numero = numero;
    }

    public String getNumero(){
        return this.numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }

   
}
