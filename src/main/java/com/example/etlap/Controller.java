package com.example.etlap;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Controller {
    protected Stage stage;

    public Stage getStage() {
        return stage;
    }

    protected void errorWrite(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(e.getClass().toString());
        alert.setContentText(e.getMessage());
        Timer t = new Timer();
        t.schedule(new TimerTask(){
            @Override
            public void run() {
                Platform.runLater(alert::show);
            }
        }, 500);
    }
    protected void alert(String message){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(message);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }
    protected boolean alertWait(String message){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(message);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> result =alert.showAndWait();
        return result.get() == ButtonType.YES;
    }
    protected boolean confirm(String s){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(s);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    public static Controller newWindow(String fxmlName, String title, int width, int height) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MenuApplication.class.getResource(fxmlName));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(title);
        stage.setScene(scene);
        Controller controller = fxmlLoader.getController();
        controller.stage = stage;
        return controller;
    }

}
