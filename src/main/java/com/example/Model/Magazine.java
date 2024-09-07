package com.example.Model;


import java.util.Date;

public class Magazine extends Document {
    private Long numero;


    // public Magazine() {
    //     super("", "", new Date(), 0,"Magazine"); 
    //     this.numero = 0L;
    // }
    public Magazine(String titre, String auteur, Date datePublication, long nombresPages,String type,Long numero) {
        super(titre, auteur, datePublication, nombresPages,type);
        this.numero = numero;
    }

    public Long getNumero(){
        return this.numero;
    }

    public void setNumero(Long numero){
        this.numero = numero;
    }

   
}
