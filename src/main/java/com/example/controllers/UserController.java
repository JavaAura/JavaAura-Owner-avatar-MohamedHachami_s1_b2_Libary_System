package com.example.controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.App;
import com.example.Model.JournalScientifique;
import com.example.Model.Livre;
import com.example.Model.Magazine;
import com.example.Model.Professor;
import com.example.Model.Student;
import com.example.Model.TheseUniversitaire;
import com.example.Model.Users;
import com.example.Service.UserDaoImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class UserController {

    final UserDaoImpl userDaoImpl;
    

     @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Users> usersTable;

    @FXML
    private TableColumn<Users, Long> columnId;


    @FXML
    private TableColumn<Users, String> columnName;

    @FXML
    private TableColumn<Users, String> columnEmail;

    @FXML
    private TableColumn<Users, String> columnTypeOfUser;
    
    @FXML
    private Label lblNote;
    
    @FXML
    private Label lblError;
    
    private List<Users> listUsers = new ArrayList();
    
    private ObservableList<Users> observableListUsers;



    @FXML
    public void initialize() {

        loadUsers(false);

    



        
    }


    public UserController(){
        this.userDaoImpl = new UserDaoImpl();

    }

    public boolean loadUsers(boolean cleanTable){
        
        try{
            List<Users> users = userDaoImpl.getListUsers();

           if(users != null){
            if (cleanTable){
                cleanTable();
            }
            
            definingColumn();
        
            setListUsers(users);

            observableListUsers = FXCollections.observableArrayList(listUsers);
          
            usersTable.setItems(observableListUsers);
            
           }else{
            System.out.println("Error");
           }
        }catch(Exception e){
            showInformationAlert("Error" , "" , "An error occurred while retrieving data",Alert.AlertType.ERROR);
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
      
    public void loadUsers(List<Users> arrayListUsers ){
         try{
            cleanTable();
            observableListUsers = FXCollections.observableArrayList(arrayListUsers);
            usersTable.setItems(observableListUsers);
        }catch(Exception e){
            e.printStackTrace();
            showInformationAlert("Error" , "" , "An error occurred while retrieving data",Alert.AlertType.ERROR);
        }
    }
    
    public void definingColumn(){
            columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            columnTypeOfUser.setCellValueFactory(new PropertyValueFactory<>("type_user"));
    }

    private void cleanTable(){
        usersTable.getItems().clear();
    }


    public List<Users> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<Users> listUsers) {
        this.listUsers = listUsers ;
    }


    @FXML
    private void updateUser(ActionEvent event) throws SQLException {
        System.out.println("here");
    
        if (usersTable.getSelectionModel().getSelectedIndex() > -1) {
            long userId = usersTable.getSelectionModel().getSelectedItem().getId();
            String userName = usersTable.getSelectionModel().getSelectedItem().getName();
            String userEmail = usersTable.getSelectionModel().getSelectedItem().getEmail();
            String userType = usersTable.getSelectionModel().getSelectedItem().getType_user();
    
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("User Information");
            alert.setHeaderText("Edit User " + userId);
    
            TextField nameField = new TextField();
            nameField.setPromptText("Enter Name");
            nameField.setText(userName);
    
            TextField emailField = new TextField();
            emailField.setPromptText("Enter Email");
            emailField.setText(userEmail);
    
         
    
            Label nameLabel = new Label("Name:");
            Label emailLabel = new Label("Email:");
            Label userTypeLabel = new Label("User type:");
    
            // Additonal for Student
            Label levelLabel = new Label("Level:");
            TextField levelField = new TextField();
            levelField.setPromptText("Enter Level");
    
            // Additonal for Professor
            Label specialtyLabel = new Label("Specialty:");
            TextField specialtyField = new TextField();
            specialtyField.setPromptText("Enter Specialty");
    
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));
    
            gridPane.add(nameLabel, 0, 0);
            gridPane.add(nameField, 1, 0);
    
            gridPane.add(emailLabel, 0, 1);
            gridPane.add(emailField, 1, 1);
    
            
            switch (userType) {
                case "Professor":
                    Professor professor = userDaoImpl.getProfessorById(userId);
                    specialtyField.setText(professor.getSpecialite());
                    gridPane.add(specialtyLabel, 0, 5);
                    gridPane.add(specialtyField, 1, 5);
                    
                    break;
                case "Student":
                    Student student = userDaoImpl.getStudentById(userId);
                    levelField.setText(student.getNiveauEtude());
                    gridPane.add(levelLabel, 0, 5);
                    gridPane.add(levelField, 1, 5);


                 break;

                
                default:
                    break;
            }


            alert.getDialogPane().setContent(gridPane);
    
            alert.getDialogPane().setMinWidth(500);
            alert.getDialogPane().setMinHeight(500);

              // Show the alert and wait for user input
              alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Handle the OK button click
                    String updatedName = nameField.getText();
                    String updatedEmail = emailField.getText();
                    String additional;
                    Boolean added = false;
                    if(userType.equals("Student")){
                        additional = levelField.getText();
                        Student student = new Student();
                        student.setId(userId);
                        student.setName(updatedName);
                        student.setEmail(updatedEmail);
                        student.setNiveauEtude(additional);
                        try {
                            added = userDaoImpl.updateStudent(student);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }else{
                        additional = specialtyField.getText();
                        Professor professor = new Professor();
                        professor.setId(userId);
                        professor.setName(updatedName);
                        professor.setEmail(updatedEmail);
                        professor.setSpecialite(additional);

                        try {
                            added = userDaoImpl.updateProfessor(professor);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                
                    
                    if(added){
                        showInformationAlert("Successs","User Updated Successfully","The modification has been added to the database successfully.", Alert.AlertType.INFORMATION);
                    }else{
                        showInformationAlert("Failed","", "" ,Alert.AlertType.ERROR);
                    }

                }
            });
            loadUsers(true);

        } else {
            System.out.println("Nothing selected");
        }
    }
    


    @FXML
    void deleteUser(ActionEvent event){

    }

    @FXML
    void actionSearch(ActionEvent event) {
        try {
            List<Users> users;

                users = userDaoImpl.getUserByName(txtSearch.getText());
               

            loadUsers(users);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }

    @FXML
    void keyPressed(KeyEvent event) {
        lblError.setText("");
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
    void addNewUser(ActionEvent event) throws IOException{
        App.setRoot("User/AddUser");
    }

    @FXML
    private void showSettings() throws IOException {
        // System.out.println("Switching");
         App.setRoot("Documents/addDocument");
    }

}
