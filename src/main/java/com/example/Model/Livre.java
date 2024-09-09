package com.example.Model;

import java.util.Date;

public class Livre extends Document {
    
    private  String isbn;

    // public Livre() {
    //     super("", "", new Date(), 0,"Livre"); 
    //     this.isbn = 0L;
    // }
    public Livre(String titre, String auteur, Date datePublication, long nombresPages,String type ,String isbn) {
        super(titre, auteur, datePublication, nombresPages,type);
        this.isbn = isbn;
    }


    public String getIsbn(){
        return this.isbn;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + this.getId() +
                ", titre='" + this.getTitre() + '\'' +
                ", auteur='" + this.getAuteur() + '\'' +
                ", datePublication=" + this.getNombresPages() +
                ", nombresPages=" + this.getIsbn() +
                ", isbn=" + isbn +
                '}';
    }
  
}
