package com.example.controllers;

import java.io.IOException;

import com.example.App;


import javafx.fxml.FXML;

public class HomeController {

    public HomeController(){
        
    }

    @FXML
    private void switchToDocuemnt() throws IOException {
        // System.out.println("Switching");
         App.setRoot("documentCrud");
        System.out.println("switch to doc");
    }

    @FXML
    private void switchToUsers() throws IOException {
        // System.out.println("Switching");
         App.setRoot("secondary");
    }

    @FXML
    private void switchToEmprunt() throws IOException {
        // System.out.println("Switching");
         App.setRoot("secondary");
    }

    @FXML
    private void switchToReservation() throws IOException {
        // System.out.println("Switching");
         App.setRoot("secondary");
    }

}
