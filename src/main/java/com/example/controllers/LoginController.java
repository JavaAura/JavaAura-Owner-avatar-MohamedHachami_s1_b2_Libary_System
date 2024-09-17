package com.example.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.example.App;
import com.example.Model.UserSession;
import com.example.Model.Users;
import com.example.Service.UserDaoImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

public class LoginController {

    final UserDaoImpl userDaoImpl;

    @FXML
    private TextField userEmailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label usernEmailValidation;

    @FXML
    private Label passwordValidation;

    public LoginController(){
        this.userDaoImpl = new UserDaoImpl();
    }

    @FXML
    void login(ActionEvent ev) throws SQLException, IOException{
        clearValidation();

        if(!validateInputs()){
            return;
        }

        String email = userEmailField.getText();
        String password = passwordField.getText();

        Users user = userDaoImpl.login(email, password);
        if(user != null){
            UserSession.setUser(user);
            Scene scene = loginButton.getScene();
            
            scene.setRoot(new FXMLLoader(App.class.getResource("Documents/Documents.fxml")).load());
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle(user.getType_user()+"Panel");
         
        }else{
            errorLabel.setText("Credentials are not correct. Please try agin !");
        }

        


    }

    @FXML
    void showRegisterStage(ActionEvent ev){

    }

    private void clearValidation(){
        errorLabel.setText("");
    }

    private boolean validateInputs() {
        boolean isValid = true;

        errorLabel.setText("");

        if (userEmailField.getText().isEmpty()) {
            usernEmailValidation.setText("Email is required");
            usernEmailValidation.setTextFill(Color.RED);
            isValid = false;
        }

        if(passwordField.getText().length() < 5 || passwordField.getText().length() > 25){
            passwordValidation.setText("Password text field cannot be less than 5 ");
            passwordValidation.setTextFill(Color.RED);
            isValid = false;
        }

        if (passwordField.getText().isEmpty()) {
            passwordValidation.setText("Password is required");
            passwordValidation.setTextFill(Color.RED);
            isValid = false;
        }
       

        return isValid;
    }
}

