package com.example.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// import java.sql.Date; 

import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.time.ZoneId;

import com.example.App;
import com.example.Model.Document;
import com.example.Service.DocumentDaoImpl;
import com.example.Service.JournalScientifiqueDaoImpl;
import com.example.Service.LivreDaoImpl;
import com.example.Service.MagazineDaoImpl;
import com.example.Service.TheseUniversitaireDaoImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;


public class DocumentController {
    

    final DocumentDaoImpl documentDaoImpl;
    final LivreDaoImpl livreDaoImpl;
    final MagazineDaoImpl magazineDaoImpl;
    final JournalScientifiqueDaoImpl journalScientifiqueDaoImpl;
    final TheseUniversitaireDaoImpl theseUniversitaireDaoImpl;




    

    @FXML
    private Button goToAddNewDocument;


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


    


    public DocumentController(){
        this.documentDaoImpl = new DocumentDaoImpl();
        this.livreDaoImpl = new LivreDaoImpl();
        this.magazineDaoImpl = new MagazineDaoImpl();
        this.journalScientifiqueDaoImpl = new JournalScientifiqueDaoImpl();
        this.theseUniversitaireDaoImpl = new TheseUniversitaireDaoImpl();

    }

    @FXML
    public void initialize() {

        loadDocuments(false);

        ToggleGroup group = new ToggleGroup();
        RadioButton livre = new RadioButton("select first");
        livre.setToggleGroup(group);
        livre.setSelected(true);
        RadioButton Magazine = new RadioButton("select second");
        Magazine.setToggleGroup(group);

        RadioButton JournalScientifique = new RadioButton("select second");
        JournalScientifique.setToggleGroup(group);

        RadioButton TheseUniversitaire = new RadioButton("select second");
        TheseUniversitaire.setToggleGroup(group);



        
    }

    public boolean loadDocuments(boolean cleanTable){
        
        try{
            List<Document> documents = documentDaoImpl.allDocuments();

           if(documents != null){
            if (cleanTable){
                cleanTable();
            }
            
            definingColumn();
        
            setListDocuments(documentDaoImpl.allDocuments());

            observableListDocuments = FXCollections.observableArrayList(listDocument);
          
            documentTable.setItems(observableListDocuments);
            
           }else{
            System.out.println("Error");
           }
        }catch(Exception e){
            showInformationAlert("Error" , "" , "An error occurred while retrieving data",Alert.AlertType.ERROR);
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
            showInformationAlert("Error" , "" , "An error occurred while retrieving data",Alert.AlertType.ERROR);
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
    private void handleCancel() throws IOException {
        App.setRoot("documentCrud");
    }

   

    @FXML
    private void updateNewDocuemnt() throws IOException {
         if (documentTable.getSelectionModel().getSelectedIndex() > -1){
            long documentId = documentTable.getSelectionModel().getSelectedItem().getId();
            String documentTitle = documentTable.getSelectionModel().getSelectedItem().getTitre();
            String documentAuthor = documentTable.getSelectionModel().getSelectedItem().getAuteur();
            String documentNumofPage = Long.toString(documentTable.getSelectionModel().getSelectedItem().getNombresPages());
            String documentType =  documentTable.getSelectionModel().getSelectedItem().getType();
            Date documentDateOfPub = documentTable.getSelectionModel().getSelectedItem().getDatePublication();


       
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book Information");
        alert.setHeaderText("Edit Book "+documentId);
        
        // Create the input fields
        TextField titleField = new TextField();
        titleField.setPromptText("Enter title");
        titleField.setText(documentTitle);
        
        TextField authorField = new TextField();
        authorField.setPromptText("Enter author");
        authorField.setText(documentAuthor);

        TextField pagesField = new TextField();
        pagesField.setPromptText("Enter number of pages");
        pagesField.setText(documentNumofPage);

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(localDate);

        TextField typeField = new TextField();
        typeField.setPromptText("Enter type");

        // Create labels for the fields
        Label titleLabel = new Label("Title:");
        Label authorLabel = new Label("Author:");
        Label pagesLabel = new Label("Number of Pages:");
        Label dateLabel = new Label("Date of Publication:");
        Label typeLabel = new Label("Type:");

        // Use a GridPane to organize the input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));  // Padding around the grid

        // Add labels and text fields to the grid
        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleField, 1, 0);

        gridPane.add(authorLabel, 0, 1);
        gridPane.add(authorField, 1, 1);

        gridPane.add(pagesLabel, 0, 2);
        gridPane.add(pagesField, 1, 2);

        gridPane.add(dateLabel, 0, 3);
        gridPane.add(datePicker, 1, 3);

        gridPane.add(typeLabel, 0, 4);
        gridPane.add(typeField, 1, 4);

        // Set the grid pane as the content of the dialog
        alert.getDialogPane().setContent(gridPane);

        // Apply custom CSS styling to the dialog (optional)
        // alert.getDialogPane().getStylesheets().add(getClass().getResource("dialog-style.css").toExternalForm());

        // Set a fixed width for the dialog (optional)
        alert.getDialogPane().setMinWidth(400);

        // Show the alert and wait for user interaction
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Retrieve the text from the TextField when OK is clicked
               
            }
        });

               
        }else{ 
            if (listDocument.isEmpty()){
                showInformationAlert("Error", null, "There are no records to delete", Alert.AlertType.ERROR);
            }else{
                showInformationAlert("Error", "Select a record", "To delete you need to select a record from the table", Alert.AlertType.ERROR);
            }
        }

        // App.setRoot("EditDocument");

    }


    @FXML
    private void deleteNewDocument() throws IOException {

        if (documentTable.getSelectionModel().getSelectedIndex() > -1){
       
            Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType btnYes = new ButtonType("Yes");
            ButtonType btnNoAnswer = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            
            dialogoExe.setTitle("Attention!");
            dialogoExe.setHeaderText("Inform if you want to delete");
            dialogoExe.setContentText("Delete " + documentTable.getSelectionModel().getSelectedItem().getTitre() + "?");
            dialogoExe.getButtonTypes().setAll(btnYes, btnNoAnswer);
            String type =  documentTable.getSelectionModel().getSelectedItem().getType() ;
            long documentId = documentTable.getSelectionModel().getSelectedItem().getId();
            System.out.println(type);
            
            dialogoExe.showAndWait().ifPresent(b -> {

                  if (b == btnYes) {
                    // DAO.getInstance().delete(personTable.getSelectionModel().getSelectedItem());
                    switch (type) {
                        case "Livre":
                            try {
                                livreDaoImpl.deleteLivre(documentId);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Magazine":
                            try {
                                magazineDaoImpl.deleteMagazine(documentId);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "JournalScientifique":
                            try {
                                journalScientifiqueDaoImpl.deleteJournalScientifiqueDao(documentId);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "TheseUniversitaire":
                            try {
                                theseUniversitaireDaoImpl.deleteTheseUniversitaire(documentId);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            break;
                    }
                    loadDocuments(true);

                }
               
            });
        }else{ 
            if (listDocument.isEmpty()){
                showInformationAlert("Error", null, "There are no records to delete", Alert.AlertType.ERROR);
            }else{
                showInformationAlert("Error", "Select a record", "To delete you need to select a record from the table", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void displayDocuments() throws IOException {
         App.setRoot("home");
    }

  

    public void showInformationAlert(String title,String header,String Context, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);

        // Show the alert and wait for user response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("User acknowledged the success message.");
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
    private void showDocuments() throws IOException {
        // System.out.println("Switching");
         App.setRoot("documentCrud");
    }

    @FXML
    private void adddNewDocument(ActionEvent event) throws IOException {
        // System.out.println("Switching");
         App.setRoot("addDocument");
    }

    @FXML
    private void showSettings() throws IOException {
        // System.out.println("Switching");
         App.setRoot("addDocument");
    }


}
