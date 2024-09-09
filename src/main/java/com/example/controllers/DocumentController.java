package com.example.controllers;

import com.example.App;
import com.example.Service.DocumentDaoImpl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.example.Model.*;; 


public class DocumentController {
    

    final DocumentDaoImpl documentDaoImpl;

    
    @FXML
    private ToggleGroup group;

    @FXML
    private Label labelTitleField;

    @FXML
    private Label labelAuthorField;

    @FXML
    private Label labelDatePublicationField;

    @FXML
    private Label labelNombrePageField;

    @FXML
    private Label labelDocTypeField;

    @FXML
    private Label labeldomaineRechercheField;

    @FXML
    private Label labelUniversity;

    @FXML
    private Label labeldomaineField;

    @FXML
    private HBox radioButtonList;

    @FXML
    private RadioButton Livre;

    @FXML
    private RadioButton Magazine;

    @FXML
    private RadioButton JournalScientifique;

    @FXML
    private RadioButton TheseUniversitaire;

    @FXML
    private TextField titleField;

    @FXML
    private TextField auteurhField;

    @FXML
    private DatePicker datePublicationField;

    @FXML
    private TextField pageCountField;

    @FXML
    private TextField domaineRechercheField;

    @FXML
    private TextField universiteField;

    @FXML
    private TextField domaineField;

    @FXML
    private TextField universityField;

    @FXML
    private TextField domainField;

    @FXML
    private Button submitButton;

    @FXML
    private Button nextButton;

     @FXML
    private Label validationTitleField; 

    @FXML
    private Label validationAuteurhField; 

    @FXML
    private Label validationDatePublicationField; 

    @FXML
    private Label validationPageCountField; 

    @FXML
    private Label validationDocumentType; 

    @FXML
    private Label validationdomaineRechercheField;

    @FXML
    private Label validationDomainField;

    @FXML
    private Label validationUniversityField;

    private String documentType;


    public DocumentController(){
        this.documentDaoImpl = new DocumentDaoImpl();
    }

    @FXML
    public void initialize() {

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





    // Method to handle the first form
    @FXML
    private void next(ActionEvent event) {
        clearValidation();



        // // Perform input validation
        if (!validateInputs()) {
            return; 
        }

        // Get values from the input fields
        String title = titleField.getText();
        String author = auteurhField.getText();
        String datePublication = datePublicationField.getValue() != null ? datePublicationField.getValue().toString() : "";
        String pageCount = pageCountField.getText();

        // // Get the selected radio button
        Toggle selectedToggle = group.getSelectedToggle();
        String documentType = "";

        if (selectedToggle != null) {
            RadioButton selectedRadioButton = (RadioButton) selectedToggle;
            documentType = selectedRadioButton.getText(); 
        }

        this.documentType = documentType;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); 
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(datePublication);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter a date in the format yyyy-MM-dd.");
        }

        switch (documentType) {
            case "Livre":
                Livre livre = new Livre(title, author, parsedDate,Integer.parseInt(pageCount) , documentType,   "");
                try {
                    if(this.documentDaoImpl.addLivre(livre)){
                        showInformationAlert("Success","Document Added Successfully","The book has been added to the database successfully.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
                break;
            case "Magazine":
                Magazine magazine = new Magazine(title, author, parsedDate,Integer.parseInt(pageCount) , documentType,   System.currentTimeMillis());
                try {
                    if(this.documentDaoImpl.addMagazine(magazine)){
                        showInformationAlert("Success","Document Added Successfully","The magazine has been added to the database successfully.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
            break;
            case "JournalScientifique":
                invisibleUi();
                labeldomaineRechercheField.setVisible(true);
                domaineRechercheField.setVisible(true);

                while (!validateInputsJournalSc()) {
                    return;
                }
                

                String domaineRecherche = domaineRechercheField.getText();


                JournalScientifique journalScientifique = new JournalScientifique(title, author, parsedDate,Integer.parseInt(pageCount) , documentType, domaineRecherche);


                try {
                    if(this.documentDaoImpl.addJournalScientifique(journalScientifique)){
                        showInformationAlert("Success","Document Added Successfully","The JournalScientifique has been added to the database successfully.");
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "ThèseUniversitaire":
                invisibleUi();
                labelUniversity.setVisible(true);
                universityField.setVisible(true);

                labeldomaineField.setVisible(true);
                domainField.setVisible(true);
                while (!validateInputTheseUniversitaire()) {
                    return;
                }

                String university = universityField.getText();
                String domain = domainField.getText();


                TheseUniversitaire theseUniversitaire = new TheseUniversitaire(title, author, parsedDate,Integer.parseInt(pageCount) , documentType, university,domain);


                try {
                    if(this.documentDaoImpl.addTheseUniversitaire(theseUniversitaire)){
                        showInformationAlert("Success","Document Added Successfully","The JournalScientifique has been added to the database successfully.");
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;




        
            default:
                break;
        }

        
        // Print or process the form data
        // System.out.println("Title: " + title);
        // System.out.println("Author: " + author);
        // System.out.println("Date Publication: " + datePublication);
        // System.out.println("Page Count: " + pageCount);
        // System.out.println("Document Type: " + documentType);


        


    }


    // @FXML
    // private void submit() throws IOException{
    //     if(this.documentType.equals("JournalScientifique")){
    //         if(!validateInputsJournalSc()){
    //             return;
    //         }
    //     }else if(this.documentType.equals("ThèseUniversitaire")){
    //         if(!validateInputTheseUniversitaire()){
    //             return;
    //         }
    //     }
    // }

    private boolean validateInputs() {

        boolean isValid = true;

        if (titleField.getText().isEmpty()) {
            validationTitleField.setText("Title is required.");
            validationTitleField.setTextFill(Color.RED);
            isValid = false;
        }

        // Check if the author field is empty
        if (auteurhField.getText().isEmpty()) {
            validationAuteurhField.setText("Author is required.");
            validationAuteurhField.setTextFill(Color.RED);
            isValid = false;
        }

        // Check if the date is selected
        if (datePublicationField.getValue() == null) {
            validationDatePublicationField.setText("Please select a publication date.");
            validationDatePublicationField.setTextFill(Color.RED);
            isValid = false;
        }

        // Check if page count is a valid number
        String pageCountText = pageCountField.getText();
        if (pageCountText.isEmpty()) {
            validationPageCountField.setText("Page count is required.");
            validationPageCountField.setTextFill(Color.RED);
            isValid = false;
        }
        try {
            int pageCount = Integer.parseInt(pageCountText);
            if (pageCount <= 0) {
                validationPageCountField.setText("Page count must be a positive number.");
                validationPageCountField.setTextFill(Color.RED);
                isValid = false;
            }
        } catch (NumberFormatException e) {
            validationPageCountField.setText("Page count must be a number.");
            validationPageCountField.setTextFill(Color.RED);
            isValid = false;
        }

        // Check if a document type is selected
        if (group.getSelectedToggle() == null) {
            validationDocumentType.setText("Please select a document type.");
            validationDocumentType.setTextFill(Color.RED);
            isValid = false;
        }

       
        return isValid;
    }

    private boolean validateInputsJournalSc(){
        boolean isValid = true;


        if (domaineRechercheField.getText().isEmpty()) {
            validationdomaineRechercheField.setText("Seach domain is required.");
            validationdomaineRechercheField.setTextFill(Color.RED);
            isValid = false;
        }

        return isValid;

        
    }


    private boolean validateInputTheseUniversitaire(){
        boolean isValid = true;

      
        if (universityField.getText().isEmpty()) {
            validationUniversityField.setText("University name is required.");
            validationUniversityField.setTextFill(Color.RED);
            isValid = false;
        }


        if (domainField.getText().isEmpty()) {
            validationDomainField.setText("Domain name is required.");
            validationDomainField.setTextFill(Color.RED);
            isValid = false;
        }
        return isValid;
        
    }

    private void clearValidation(){
        validationTitleField.setText("");
        validationAuteurhField.setText("");
        validationDatePublicationField.setText("");
        validationPageCountField.setText("");
        validationDocumentType.setText("");
        validationdomaineRechercheField.setText("");



    }




    private void clearTextInput(){
        titleField.setText("");
        auteurhField.setText("");
        pageCountField.setText("");
        validationPageCountField.setText("");
        domaineRechercheField.setText("");
        universiteField.setText("");
        domainField.setText("");
        universityField.setText("");
    }
   


    @FXML
    private void handleCancel() throws IOException {
         App.setRoot("documentCrud");
    }

    @FXML
    private void adddNewDocument() throws IOException {
         App.setRoot("addDocument");
    }

    @FXML
    private void updateNewDocuemnt() throws IOException {
         App.setRoot("home");
    }


    @FXML
    private void deleteNewDocument() throws IOException {
         App.setRoot("home");
    }

    @FXML
    private void displayDocuments() throws IOException {
         App.setRoot("home");
    }

    public  void invisibleUi(){
        titleField.setVisible(false);
        labelTitleField.setVisible(false);
        labelAuthorField.setVisible(false);
        labelDatePublicationField.setVisible(false);
        labelNombrePageField.setVisible(false);
        labelDocTypeField.setVisible(false);
        radioButtonList.setVisible(false);

        titleField.setVisible(false);
        auteurhField.setVisible(false);
        datePublicationField.setVisible(false);
        pageCountField.setVisible(false);
        domaineRechercheField.setVisible(false);

        // nextButton.setVisible(false);
        // submitButton.setVisible(true);

    }


    public void showInformationAlert(String title,String header,String Context) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);

    // Show the alert and wait for user response
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            System.out.println("User acknowledged the success message.");
            clearTextInput();
        } else {
            System.out.println("User closed the alert or didn't click OK.");
        }
    });
}
    





    
   

    
}
