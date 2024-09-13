package com.example.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.example.App;
import com.example.Model.Professor;
import com.example.Model.Student;
import com.example.Service.UserDaoImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class AddUserController {

    final UserDaoImpl userDaoImpl;


     @FXML
    private ToggleGroup group;

     @FXML
    private HBox radioButtonList;

     @FXML
    private RadioButton Student;

    @FXML
    private RadioButton Professor;

    @FXML 
    private Label labelUserName;

    @FXML
    private TextField userNameField;

    @FXML 
    private Label validationUserName;


    @FXML 
    private Label labelUserEmail;

    @FXML
    private TextField userEmailField;

    @FXML 
    private Label validationUserEmail;

    // 

    @FXML 
    private Label labelProfSpecialite;

    @FXML
    private TextField profSpecialiteField;

    @FXML 
    private Label validationProfSpecialite;
    // 

    @FXML 
    private Label labelStudentLevel;

    @FXML
    private TextField studentLevelFiled;

    @FXML 
    private Label validationStudentLevel;

    @FXML
    private Label validationUserType;

    public AddUserController(){
        this.userDaoImpl = new UserDaoImpl();
    }


    @FXML
    public void initialize() {
        Student.setOnAction(event -> {
            if (Student.isSelected()) {
                if(labelProfSpecialite.isVisible() || profSpecialiteField.isVisible()){
                    labelProfSpecialite.setVisible(false);
                    profSpecialiteField.setVisible(false);
                    validationProfSpecialite.setVisible(false);

                }

                labelStudentLevel.setVisible(true);
                studentLevelFiled.setVisible(true);

            }
        });

        Professor.setOnAction(event -> {
            if (Professor.isSelected()) {
                if(labelStudentLevel.isVisible() || studentLevelFiled.isVisible()){
                    labelStudentLevel.setVisible(false);
                    studentLevelFiled.setVisible(false);
                    validationStudentLevel.setVisible(false);

                }
                labelProfSpecialite.setVisible(true);
                profSpecialiteField.setVisible(true);
                
            }
        });


        userEmailField.textProperty().addListener((observable, oldValue, newValue) -> {
            validationUserEmail.setText("");
        });
    }

    @FXML
    private void handelSubmit(ActionEvent event) throws SQLException {
        System.out.println("sss");
        clearValidation();

        Toggle selectedToggle = group.getSelectedToggle();
        String userType = "";

        if (selectedToggle != null) {
            RadioButton selectedRadioButton = (RadioButton) selectedToggle;
            userType = selectedRadioButton.getText(); 
        }

        if(!additionalValidationInputs(userType) || !validateInputs()){
            System.out.println("azaz"+userEmailField.getText());

            return;
        }


        String userName = userNameField.getText();
        String userEmail = userEmailField.getText();

        Boolean added=false;

        String additional;
        if(userType.equals("Professor")){
            additional = profSpecialiteField.getText() ;
            Professor professor = new Professor();
            professor.setName(userName);
            professor.setEmail(userEmail);
            professor.seType("Professor");
            professor.setSpecialite(additional);

            added = this.userDaoImpl.addProfessor(professor);

            
        }else{
            additional = studentLevelFiled.getText();
            Student student = new Student();
            student.setName(userName);
            student.setEmail(userEmail);
            student.seType("Student");
            student.setNiveauEtude(additional);

            added = this.userDaoImpl.addStudent(student);

        }

        if (added) {
            showInformationAlert("Success","User Added Successfully","The"+ userType +" "+ " has been added to the database successfully.",Alert.AlertType.INFORMATION);
        }else{
            showInformationAlert("Error","Try again","",Alert.AlertType.ERROR);
            
        }



    }



    private void clearValidation(){
        validationUserEmail.setText("");
        validationUserName.setText("");
        validationUserType.setText("");
        validationStudentLevel.setText("");
        validationProfSpecialite.setText("");


    }

    private boolean validateInputs() throws SQLException {

        boolean isValid = true;

        validationUserEmail.setText("");
        validationUserName.setText("");
        validationUserType.setText("");
        validationStudentLevel.setText("");
        validationProfSpecialite.setText("");


        // Check if the username field is empty

        if (userNameField.getText().isEmpty()) {
            validationUserName.setText("User Name is required.");
            validationUserName.setTextFill(Color.RED);
            isValid = false;
        }


        if (userEmailField.getText().isEmpty()) {
            validationUserEmail.setText("Email is required.");
            validationUserEmail.setTextFill(Color.RED);
            isValid = false;
        } else if (this.userDaoImpl.emailIsAlreadyExist(userEmailField.getText())) {
            validationUserEmail.setText("Email already exists.");
            validationUserEmail.setTextFill(Color.RED);
            isValid = false;
            System.out.println("Email exists");
        } else {
            validationUserEmail.setText("");
            System.out.println("Email does not exist");
        }


        

          // Check if a user type is selected
        if (group.getSelectedToggle() == null) {
            validationUserType.setText("Please select a user type.");
            validationUserType.setTextFill(Color.RED);
            isValid = false; 
        }
       
       
        return isValid;
    }

    private boolean additionalValidationInputs(String userType){
        Boolean isValid = true;
        switch (userType) {
            case "Professor":
                if (profSpecialiteField.getText().isEmpty()) {
                    validationProfSpecialite.setText("Specialite is required.");
                    validationProfSpecialite.setTextFill(Color.RED);
                    isValid = false;
                }
                
                break;
            case "Student":
            if (studentLevelFiled.getText().isEmpty()) {
                validationStudentLevel.setText("Level is required.");
                validationStudentLevel.setTextFill(Color.RED);
                isValid = false;
            }
                
                break;
        
            default:
                return false;
        }

        return isValid;
    }

     public void showInformationAlert(String title,String header,String Context, Alert.AlertType type) {
        Alert alert = new Alert(type);
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

    public void clearTextInput(){
        userEmailField.setText("");
        userNameField.setText("");
        studentLevelFiled.setText("");
        profSpecialiteField.setText("");
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        App.setRoot("User/User");
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
    private void showSettings() throws IOException {
        // System.out.println("Switching");
         App.setRoot("Documents/addDocument");
    }
}
