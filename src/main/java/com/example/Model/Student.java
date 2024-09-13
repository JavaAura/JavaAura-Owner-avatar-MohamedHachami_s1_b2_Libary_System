package com.example.Model;


public class Student extends Users {

    private String niveau_etude;


    public Student() {}

    public Student(String name,String email , String type_user , String niveau_etude) {
        super(name, email, type_user);
        this.niveau_etude = niveau_etude;
    }

    public Student(Long id , String name,String email , String type_user , String niveau_etude) {
        super(id,name, email, type_user);
        this.niveau_etude = niveau_etude;
    }

    public String getNiveauEtude(){
        return this.niveau_etude;
    }


    public void setNiveauEtude(String niveau_etude){
        this.niveau_etude = niveau_etude;
    }

    
}
