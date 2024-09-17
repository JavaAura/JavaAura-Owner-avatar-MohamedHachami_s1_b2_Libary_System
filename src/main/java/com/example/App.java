package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;




/**
 * JavaFX App
 */
public class App extends Application {


    private static Scene scene;


    private Stage stage;

   

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        scene = new Scene(loadFXML("Auth/login"), 1666, 1500);


        // scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        this.stage.setTitle("BookTrack");
        this.stage.setScene(scene);
        this.stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();
    }



    

}