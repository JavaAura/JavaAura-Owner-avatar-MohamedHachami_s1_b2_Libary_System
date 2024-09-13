package com.example.Model;


public abstract class Users {
    private Long id;
    private String name;
    private String email;
    private String type_user;


    public Users (){

    }

    public Users (String name , String email , String type_user){
        this.name = name;
        this.email = email; 
        this.type_user = type_user;
    }

    public Users (Long id ,String name , String email , String type_user){
        this.id = id;
        this.name = name;
        this.email = email; 
        this.type_user = type_user;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }


    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getType_user(){
        return this.type_user;
    }

    public void setType_user(String type_user){
        this.type_user = type_user;
    }

}
