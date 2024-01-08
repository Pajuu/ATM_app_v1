package com.example.bankstudenta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private double xOffSet = 0, yOffSet = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainMenu.fxml")));
        root.setOnMousePressed(mouseEvent -> {
            xOffSet = mouseEvent.getX();
            yOffSet = mouseEvent.getY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - xOffSet);
            primaryStage.setY(mouseEvent.getScreenY() - yOffSet);
        });


        primaryStage.setTitle("BankStudenta");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene s = new Scene(root);
        primaryStage.setScene(s);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

    }

    public static void main(String[] args) {
        launch();
    }
}