package com.example.controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.App;
import com.example.Model.Document;
import com.example.Model.UserSession;
import com.example.Model.Users;
import com.example.Service.BibiloDaoImpl;
import com.example.Service.DocumentDaoImpl;
import com.example.Service.JournalScientifiqueDaoImpl;
import com.example.Service.LivreDaoImpl;
import com.example.Service.MagazineDaoImpl;
import com.example.Service.TheseUniversitaireDaoImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class AvailableDocument {
      final DocumentDaoImpl documentDaoImpl;
    final LivreDaoImpl livreDaoImpl;
    final MagazineDaoImpl magazineDaoImpl;
    final JournalScientifiqueDaoImpl journalScientifiqueDaoImpl;
    final TheseUniversitaireDaoImpl theseUniversitaireDaoImpl;
    final BibiloDaoImpl bibiloDaoImpl;





    

    @FXML
    private Button borrowDocuemntBtn;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Document> documentTable;

    @FXML
    private TableColumn<Document, Long> columnId;


    @FXML
    private TableColumn<Document, String> columnTitle;

    @FXML
    private TableColumn<Document, String> columnAuthor;

    @FXML
    private TableColumn<Document, Date> columnDatePublication;

    @FXML
    private TableColumn<Document, Long> columnNumOfPage;

    @FXML
    private TableColumn<Document, String> columnType;
    
    @FXML
    private Label lblNote;
    
    @FXML
    private Label lblError;
    
    private List<Document> listDocument = new ArrayList();
    
    private ObservableList<Document> observableListDocuments;

    @FXML
    private Button showUsersBtn;

    @FXML
    private Button showEmpruntBtn;

    @FXML
    private Button reserveDocumentBtn;



    


    public AvailableDocument(){
        this.documentDaoImpl = new DocumentDaoImpl();
        this.livreDaoImpl = new LivreDaoImpl();
        this.magazineDaoImpl = new MagazineDaoImpl();
        this.journalScientifiqueDaoImpl = new JournalScientifiqueDaoImpl();
        this.theseUniversitaireDaoImpl = new TheseUniversitaireDaoImpl();
        this.bibiloDaoImpl = new BibiloDaoImpl();
    }


    @FXML
    public void initialize() {

        Users connectedUser = UserSession.getUser();

        loadDocuments(false);
        // System.out.println(this.myData);

        if(connectedUser.getType_user().equals("Student") || connectedUser.getType_user().equals("Professor")){
            showUsersBtn.setVisible(false);
            showEmpruntBtn.setPrefHeight(116.0);
            showEmpruntBtn.setPrefWidth(146.0);
        }
        



        
    }

    public boolean loadDocuments(boolean cleanTable){
        
        try{
            List<Document> documents = documentDaoImpl.allDocuments();

           if(documents != null){
            if (cleanTable){
                cleanTable();
            }
            
            definingColumn();
        
            setListDocuments(documents);

            observableListDocuments = FXCollections.observableArrayList(listDocument);
          
            documentTable.setItems(observableListDocuments);
            
           }else{
            System.out.println("Error");
           }
        }catch(Exception e){
            System.out.println("loadDocuments1");
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
      
    public void loadDocuments(List<Document> arrayListDocuments){
         try{
            cleanTable();
            observableListDocuments = FXCollections.observableArrayList(arrayListDocuments);
            documentTable.setItems(observableListDocuments);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void definingColumn(){
        // if(this.columnId !=null && this.columnAuthor!=null && this.columnDatePublication!=null && this.columnNumOfPage!=null && this.columnTitle!=null&& this.columnType!=null){
            columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnTitle.setCellValueFactory(new PropertyValueFactory<>("titre"));
            columnAuthor.setCellValueFactory(new PropertyValueFactory<>("auteur"));
            columnDatePublication.setCellValueFactory(new PropertyValueFactory<>("datePublication"));
            columnNumOfPage.setCellValueFactory(new PropertyValueFactory<>("nombresPages"));
            columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        // }
       
    }

    private void cleanTable(){
        documentTable.getItems().clear();
    }


    public List<Document> getListDocuments() {
        return listDocument;
    }

    public void setListDocuments(List<Document> listDocument) {
        this.listDocument = listDocument;
    }
    
    @FXML
    public void borrowDocument(ActionEvent ev) throws SQLException {
        if (documentTable.getSelectionModel().getSelectedIndex() > -1) {
            long documentId = documentTable.getSelectionModel().getSelectedItem().getId();
            String documentName = documentTable.getSelectionModel().getSelectedItem().getTitre();
            String documentType = documentTable.getSelectionModel().getSelectedItem().getType();

            Users connectedUser = UserSession.getUser();
            String userType = connectedUser.getType_user();

            try {
                if (!documentDaoImpl.isAvailable(documentId)) {
                    showAlert(Alert.AlertType.WARNING, "Borrow Document", "Document Unavailable: " + documentName);
                    return;
                }

                if (!isUserEligibleForBorrowing(userType, documentType)) {
                    showAlert(Alert.AlertType.WARNING, "Borrow Document", getBorrowingRestrictionMessage(userType, documentType));
                    return;
                }

                borrowDocumentAndNotify(documentId, documentName, connectedUser);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void borrowDocumentAndNotify(long documentId, String documentName, Users connectedUser) throws SQLException {
        bibiloDaoImpl.emprunter(documentId, connectedUser.getId());
    
        Alert alert = showAlert(Alert.AlertType.INFORMATION, "Borrow Document", "Document Borrowed Successfully: " + documentName);
    
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("User acknowledged the message.");
            } else {
                System.out.println("User closed the alert or didn't click OK.");
            }
        });
    }

    @FXML
    void actionSearch(ActionEvent event) {
        System.out.println(txtSearch.getText());
        try {
            List<Document> documents;

                documents = documentDaoImpl.getDocumentByName(txtSearch.getText());
                documents.stream().forEach(doc->{
                    System.out.println(doc.getAuteur());
                });

            loadDocuments(documents);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }

    @FXML
    void keyPressed(KeyEvent event) {
        lblError.setText("");
    }

    @FXML
    private void reserveDocument() throws IOException {
        if (documentTable.getSelectionModel().getSelectedIndex() > -1) {
            long documentId = documentTable.getSelectionModel().getSelectedItem().getId();
            String documentName = documentTable.getSelectionModel().getSelectedItem().getTitre();
            
            Users connectedUser = UserSession.getUser();
    
            try {
                boolean isAvailable = documentDaoImpl.isAvailable(documentId);
    
                if (isAvailable) {
                    borrowDocumentAndNotify(documentId, documentName, connectedUser);
                } else {
                    handleReservation(documentId, documentName, connectedUser);
                }
            } catch (SQLException e) {
                e.printStackTrace();  
            }
        }
    }
    
    private boolean isUserEligibleForBorrowing(String userType, String documentType) {
        return !(documentType.equals("TheseUniversitaire") && !userType.equals("Professor")) &&
               !(documentType.equals("JournalScientifique") && userType.equals("Student"));
    }
    
    private String getBorrowingRestrictionMessage(String userType, String documentType) {
        if (documentType.equals("TheseUniversitaire") && !userType.equals("Professor")) {
            return "Borrowing Denied: Only Professors can borrow Thèses Universitaires.";
        } else if (documentType.equals("JournalScientifique") && userType.equals("Student")) {
            return "Borrowing Denied: Students cannot borrow Journaux Scientifiques.";
        }
        return "You are not eligible to borrow this document.";
    }
    
    private void handleReservation(long documentId, String documentName, Users connectedUser) throws SQLException {
        Boolean alreadyReserved = bibiloDaoImpl.checkReservation(documentId);
        
        if (alreadyReserved) {
            Alert alert = showAlert(Alert.AlertType.WARNING, "Reservation", "This document is already reserved.Please try later !");
            alert.showAndWait();
            return;
        }
    
        Boolean reservationSuccess = bibiloDaoImpl.Reserve(documentId, connectedUser.getId());
        
        if (reservationSuccess) {
            Alert alert = showAlert(Alert.AlertType.INFORMATION, "Reservation", "Document reserved successfully: " + documentName);
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    System.out.println("User acknowledged the reservation.");
                } else {
                    System.out.println("User closed the alert or didn't click OK.");
                }
            });
        } else {
            System.out.println("Error: Could not reserve the document.");
        }
    }
    
    

    private Alert showAlert(Alert.AlertType alertType, String title, String headerText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.show();
        return alert;
    }
    
    private Alert showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
        return alert;
    }


  
    @FXML
    private void showDocuments() throws IOException {
        App.setRoot("Documents/Documents");
    }

    @FXML
    private void showUsers(ActionEvent event) throws IOException {
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

    @FXML
    private void showReservation()throws IOException{
        App.setRoot("Documents/MyReservations");
    }


}
