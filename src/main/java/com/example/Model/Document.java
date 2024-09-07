package com.example.Model;

import java.util.Date;

public abstract class Document {
    private Long id;
    private String titre;
    private String auteur;
    private Date datePublication;
    private long nombresPages;
    private String type;



    public Document(){
        
    }

    public Document(String titre, String auteur, Date datePublication, long nombresPages ,String type) {
        this.titre = titre;
        this.auteur = auteur;
        this.datePublication = datePublication;
        this.nombresPages = nombresPages;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public long getNombresPages() {
        return nombresPages;
    }

    public void setNombresPages(long nombresPages) {
        this.nombresPages = nombresPages;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
