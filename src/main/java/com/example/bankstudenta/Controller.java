package com.example.bankstudenta;
import Transaction.Transaction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import User.User;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Controller implements Initializable {
    public Stage stage;
    public Scene scene;
    public Parent root;
    public static User user = new User();



    @FXML
    private Button LoginBtnZaloguj;

    @FXML
    private TextField LoginTHaslo;

    @FXML
    private TextField LoginTfNumerIndeksu;

    @FXML
    private Button RegisterBtnZarejestruj;

    @FXML
    private Button LoginClose;

    @FXML
    private Button RegisterClose;

    @FXML
    private TextField RegisterTfHaslo;

    @FXML
    private TextField RegisterTfImie;

    @FXML
    private TextField RegisterTfNazwisko;

    @FXML
    private TextField RegisterTfNumerIndeksu;

    @FXML
    private TextField RegisterTfPowtorzHaslo;

    @FXML
    private ToggleButton btnLogowanie;

    @FXML
    private ToggleButton btnRejestracja;

    @FXML
    private Pane pLoginForm;

    @FXML
    private Pane pRegisterForm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleClicks(ActionEvent event) throws IOException {
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
        this.user.addUserToDB(Integer.parseInt(RegisterTfNumerIndeksu.getText()), RegisterTfImie.getText(), RegisterTfNazwisko.getText(), RegisterTfHaslo.getText(), RegisterTfPowtorzHaslo.getText());
    }
    @FXML
    private void handleLogin(ActionEvent event) throws IOException{

        this.user.loginUser(Integer.parseInt(LoginTfNumerIndeksu.getText()), LoginTHaslo.getText());
        if (this.user.isLoggedIn()){
            final double[] xOffSet = {0};
            final double[] yOffSet = {0};


            root = FXMLLoader.load(getClass().getResource("screenAfterLogin.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffSet[0] = mouseEvent.getX();
                    yOffSet[0] = mouseEvent.getY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setX(mouseEvent.getScreenX() - xOffSet[0]);
                    stage.setY(mouseEvent.getScreenY() - yOffSet[0]);
                }
            });


            scene = new Scene(root);
            stage.setScene(scene);
            stage.setY(150);
            stage.setX(400);
            stage.show();

        }

    }

}