package com.example.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.example.App;
import com.example.Model.Document;
import com.example.Model.UserSession;
import com.example.Model.Users;
import com.example.Service.BibiloDaoImpl;
import com.example.Service.DocumentDaoImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReturnDocumentController {
    final DocumentDaoImpl documentDaoImpl;
    final BibiloDaoImpl bibiloDaoImpl;

    

    @FXML
    private Button showUsersBtn;

    @FXML
    private Button showEmpruntBtn;

    @FXML
    private TextField documentIdField;

    @FXML 
    private Label validationdocumentIdField;


    public ReturnDocumentController(){
        this.documentDaoImpl = new DocumentDaoImpl();
        this.bibiloDaoImpl = new BibiloDaoImpl();
    }

    @FXML
    public void initialize() {

        Users connectedUser = UserSession.getUser();


        if(connectedUser.getType_user().equals("Student") || connectedUser.getType_user().equals("Professor")){
            showUsersBtn.setVisible(false);
            showEmpruntBtn.setPrefHeight(116.0);
            showEmpruntBtn.setPrefWidth(146.0);
        }
    }

    @FXML
    private void handleReturn() throws IOException, SQLException {
        String documentId = documentIdField.getText();
        validationdocumentIdField.setText("");

        if (documentId.isEmpty()) {
            validationdocumentIdField.setText("Enter the document ID");
            return;
        }

        try {
            long id = Long.parseLong(documentId);
            Document document = this.documentDaoImpl.getDocumentById(id);

            if (document == null) {
                validationdocumentIdField.setText("No document found with the given ID");
                return;
            }

            boolean isAvailable = this.documentDaoImpl.isAvailable(id);

            if (isAvailable) {
                validationdocumentIdField.setText("This document is already available and not currently borrowed.");
                return;
            }

            Users user = UserSession.getUser();
            boolean validEmprunt = this.bibiloDaoImpl.validEmprunt(id, user.getId());

            if (validEmprunt) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Return Document - Confirmation");
                confirmationAlert.setHeaderText("Are you sure you want to return the document?");

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            this.bibiloDaoImpl.Return(id, user.getId());
                            showAlert(Alert.AlertType.INFORMATION, "Return Document", "Document returned successfully.");
                        } catch (SQLException e) {
                            e.printStackTrace(); 
                        }
                    } else {
                        System.out.println("User closed the alert or didn't click OK.");
                    }
                });
            } else {
                validationdocumentIdField.setText("No valid borrowing record found for this document.");
            }

        } catch (NumberFormatException e) {
            // Handle case where the document ID is not a valid long
            validationdocumentIdField.setText("The document ID is not valid.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    @FXML
    private void showDocuments() throws IOException {
        // System.out.println("Switching");
         App.setRoot("Documents/Documents");
    }

    @FXML
    private void showUsers(ActionEvent event) throws IOException {
        // System.out.println("Switching");
        App.setRoot("User/User");
    }

    @FXML
    private void showAvailableDocuments() throws IOException {
         App.setRoot("Documents/AvailableDocument");
    }


    @FXML
    private void adddNewDocument() throws IOException {
        App.setRoot("Documents/addDocument");
    }

    @FXML
    private void returnDocument() throws IOException {
        App.setRoot("Documents/returnDocument");
    }



}
