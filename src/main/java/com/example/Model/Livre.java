package com.example.Model;

import java.util.Date;

public class Livre extends Document {
    
    private  Long isbn;

    // public Livre() {
    //     super("", "", new Date(), 0,"Livre"); 
    //     this.isbn = 0L;
    // }
    public Livre(String titre, String auteur, Date datePublication, long nombresPages,String type ,Long isbn) {
        super(titre, auteur, datePublication, nombresPages,type);
        this.isbn = isbn;
    }


    public Long getIsbn(){
        return this.isbn;
    }

    public void setIsbn(Long isbn){
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
