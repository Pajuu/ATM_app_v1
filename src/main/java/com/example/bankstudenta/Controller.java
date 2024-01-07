package com.example.bankstudenta;
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

public class Controller implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private User u = new User();
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

    //Tekst skladajacy sie z imienia i indeksu z bazy danych
    @FXML
    private Text name_and_index;

    //aktualny stan konta z bazy danych
    @FXML
    private Text students_money;

    //tabelka z historia wszystkich transakcji
    @FXML
    private TableView<?> transaction_history_table;

    @FXML
    private Button AppBtnPayment;

    @FXML
    private Button AppBtnTransfer;

    @FXML
    private Button AppBtnWithdrawal;

    @FXML
    private Button AppBtnLogOut;

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
        this.u.addUserToDB(Integer.parseInt(RegisterTfNumerIndeksu.getText()), RegisterTfImie.getText(), RegisterTfNazwisko.getText(), RegisterTfHaslo.getText(), RegisterTfPowtorzHaslo.getText());
    }
    @FXML
    private void handleLogin(ActionEvent event) throws IOException{

        //this.u.loginUser(Integer.parseInt(LoginTfNumerIndeksu.getText()), LoginTHaslo.getText());

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

    @FXML
    private void handleLogOut(ActionEvent event) throws IOException{

        //this.u.loginUser(Integer.parseInt(LoginTfNumerIndeksu.getText()), LoginTHaslo.getText());

        final double[] xOffSet = {0};
        final double[] yOffSet = {0};

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainMenu.fxml")));
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
        stage.setY(110);
        stage.setX(500);
        stage.show();
    }
}