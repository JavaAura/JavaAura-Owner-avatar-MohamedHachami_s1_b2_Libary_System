package com.example.Model;

import java.util.Date;

public class JournalScientifique  extends Document  {
    

     
    private  String domaineRecherche;

    public JournalScientifique() {
       
    }

    public JournalScientifique(String titre, String auteur, Date datePublication, long nombresPages,String type ,String domaineRecherche) {
        super(titre, auteur, datePublication, nombresPages,type);
        this.domaineRecherche = domaineRecherche;
    }


    public JournalScientifique(Long id,String titre, String auteur, Date datePublication, long nombresPages,String type ,String domaineRecherche) {
        super(id,titre, auteur, datePublication, nombresPages,type);
        this.domaineRecherche = domaineRecherche;
    }


    public String getdomaineRecherche(){
        return this.domaineRecherche;
    }

    public void setdomaineRecherche(String domaineRecherche){
        this.domaineRecherche = domaineRecherche;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + this.getId() +
                ", titre='" + this.getTitre() + '\'' +
                ", auteur='" + this.getAuteur() + '\'' +
                ", datePublication=" + this.getDatePublication() +
                ", domaineRecherche=" + this.getdomaineRecherche() +
                ", nombres de pages=" + this.getNombresPages() +
                '}';
    }
  
}
