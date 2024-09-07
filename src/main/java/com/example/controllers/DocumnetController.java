package com.example.controllers;

import com.example.App;
import com.example.Service.DocumentDaoImpl;

import javafx.fxml.FXML;

import java.io.IOException;

public class DocumnetController {
    

    final DocumentDaoImpl documentDaoImpl;

    public DocumnetController(){
        this.documentDaoImpl = new DocumentDaoImpl();
    }


     @FXML
    private void adddNewDocument() throws IOException {
        // System.out.println("Switching");
         App.setRoot("home");
         
    }

    @FXML
    private void updateNewDocuemnt() throws IOException {
        // System.out.println("Switching");
         App.setRoot("home");
    }

    @FXML
    private void deleteNewDocument() throws IOException {
        // System.out.println("Switching");
         App.setRoot("home");
    }

    @FXML
    private void displayDocuments() throws IOException {
        // System.out.println("Switching");
         App.setRoot("home");
    }

    
}
