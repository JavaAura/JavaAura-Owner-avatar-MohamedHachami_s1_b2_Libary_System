package com.example.controllers;

import com.example.App;
import com.example.Model.Document;
import com.example.Model.UserSession;
import com.example.Model.Users;
import com.example.Service.BibiloDaoImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.*;

import java.sql.Date;
import java.sql.SQLException;
import java.io.IOException;



public class MyReservations {
    private final BibiloDaoImpl bibiloDaoImpl;
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
    private TableColumn<Document, String> columnType;


    @FXML
    private Button showUsersBtn;

    private List<Document> listDocument = new ArrayList();
    
    private ObservableList<Document> observableListDocuments;

    public MyReservations(){
        this.bibiloDaoImpl = new BibiloDaoImpl();
    }


      @FXML
    public void initialize() {

        Users connectedUser = UserSession.getUser();

        loadDocuments(false);
        if(connectedUser.getType_user().equals("Student") || connectedUser.getType_user().equals("Professor")){
            showUsersBtn.setVisible(false);
        }
        
    }

     public boolean loadDocuments(boolean cleanTable){
        Users connectedUser = UserSession.getUser();
        
        try{
            List<Document> documents = bibiloDaoImpl.myReservations(connectedUser.getId());

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
            System.out.println("loadDocuments2");
            e.printStackTrace();
        }
    }
    
    public void definingColumn(){
        // if(this.columnId !=null && this.columnAuthor!=null && this.columnDatePublication!=null && this.columnNumOfPage!=null && this.columnTitle!=null&& this.columnType!=null){
            columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnTitle.setCellValueFactory(new PropertyValueFactory<>("titre"));
            columnAuthor.setCellValueFactory(new PropertyValueFactory<>("auteur"));
            columnDatePublication.setCellValueFactory(new PropertyValueFactory<>("datePublication"));
            columnType.setCellValueFactory(new PropertyValueFactory<>("type"));

        // }
       
    }

   @FXML
    private void cancelReservation() throws IOException {
        if (documentTable.getSelectionModel().getSelectedIndex() > -1) {
            long documentId = documentTable.getSelectionModel().getSelectedItem().getId();
            Long userId = UserSession.getUser().getId();

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Cancel Reservation");
            confirmationAlert.setHeaderText("Are you sure you want to cancel the reservation?");
            confirmationAlert.setContentText("This action cannot be undone.");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        this.bibiloDaoImpl.cancelReservation(documentId, userId);

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Reservation Canceled");
                        successAlert.setHeaderText("Reservation canceled successfully.");
                        successAlert.setContentText("The reservation for the document has been canceled.");
                        successAlert.showAndWait();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Reservation canceling was aborted by the user.");
                }
            });
            loadDocuments(true);

        }
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


}
