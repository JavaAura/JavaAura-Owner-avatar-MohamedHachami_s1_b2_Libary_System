package com.example.Model;

public class Professor extends Users {
    
    private String specialite;


    public Professor() {}

    public Professor(String name,String email , String type_user , String specialite) {
        super(name, email, type_user);
        this.specialite = specialite;
    }

    public Professor(Long id , String name,String email , String type_user , String specialite) {
        super(id,name, email, type_user);
        this.specialite = specialite;
    }

    public String getSpecialite(){
        return this.specialite;
    }


    public void setSpecialite(String specialite){
        this.specialite = specialite;
    }

    

}
