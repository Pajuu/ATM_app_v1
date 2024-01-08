package com.example.bankstudenta;

import User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Stage stage;
    public Scene scene;
    public Parent root;
    public static User user = new User();

    @FXML
    private TextField LoginTHaslo, LoginTfNumerIndeksu;

    @FXML
    private Button LoginClose,RegisterClose;

    @FXML
    private TextField RegisterTfHaslo, RegisterTfImie, RegisterTfNazwisko,RegisterTfNumerIndeksu,RegisterTfPowtorzHaslo;

    @FXML
    private ToggleButton btnLogowanie, btnRejestracja;

    @FXML
    private Pane pRegisterForm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleClicks(ActionEvent event) {
        if(event.getSource() == btnLogowanie){
            pRegisterForm.toBack();
            btnLogowanie.setSelected(true);
            btnRejestracja.setSelected(false);
        }
        else if(event.getSource() == btnRejestracja){
            pRegisterForm.toFront();
            btnLogowanie.setSelected(false);
            btnRejestracja.setSelected(true);
        }
        else if(event.getSource() == LoginClose || event.getSource() == RegisterClose){
            System.exit(0);
        }
    }

    @FXML
    private void handleRegistration() {
        user.addUserToDB(Integer.parseInt(RegisterTfNumerIndeksu.getText()), RegisterTfImie.getText(), RegisterTfNazwisko.getText(), RegisterTfHaslo.getText(), RegisterTfPowtorzHaslo.getText());
        RegisterTfNumerIndeksu.setText("");
        RegisterTfImie.setText("");
        RegisterTfNazwisko.setText("");
        RegisterTfHaslo.setText("");
        RegisterTfPowtorzHaslo.setText("");
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException{
        user.loginUser(Integer.parseInt(LoginTfNumerIndeksu.getText()), LoginTHaslo.getText());
        if (user.isLoggedIn()){
            final double[] xOffSet = {0};
            final double[] yOffSet = {0};


            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("screenAfterLogin.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            root.setOnMousePressed(mouseEvent -> {
                xOffSet[0] = mouseEvent.getX();
                yOffSet[0] = mouseEvent.getY();
            });
            root.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getScreenX() - xOffSet[0]);
                stage.setY(mouseEvent.getScreenY() - yOffSet[0]);
            });

            scene = new Scene(root);
            stage.setScene(scene);
            stage.setY(200);
            stage.setX(550);
            stage.show();

        }
        LoginTfNumerIndeksu.setText("");
        LoginTHaslo.setText("");
    }
}