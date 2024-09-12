package com.example.Model;

import java.util.Date;


public class TheseUniversitaire  extends Document  {
    
    private  String universite;
    private  String domaine;

    public TheseUniversitaire(){

    }

    public TheseUniversitaire(String titre, String auteur, Date datePublication, long nombresPages,String type ,String universite,String domaine) {
        super(titre, auteur, datePublication, nombresPages,type);
        this.universite = universite;
        this.domaine = domaine;

    }


    public TheseUniversitaire(Long id,String titre, String auteur, Date datePublication, long nombresPages,String type ,String universite,String domaine) {
        super(id,titre, auteur, datePublication, nombresPages,type);
        this.universite = universite;
        this.domaine = domaine;

    }


    public String getUniversite(){
        return this.universite;
    }

    public void setUniversite(String universite){
        this.universite = universite;
    }

    public String getDomaine(){
        return this.domaine;
    }

    public void setDomaine(String domaine){
        this.domaine = domaine;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + this.getId() +
                ", titre='" + this.getTitre() + '\'' +
                ", auteur='" + this.getAuteur() + '\'' +
                ", datePublication=" + this.getDatePublication() + '\'' +
                ", Nombres de pages=" + this.getNombresPages() + '\'' +
                ", Universite=" + this.getUniversite() + '\'' +
                ", domain=" + this.getDomaine() +
                '}';
    }


}
