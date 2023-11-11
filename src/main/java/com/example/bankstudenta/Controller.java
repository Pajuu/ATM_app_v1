package com.example.bankstudenta;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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
    private void handleClicks(ActionEvent event){
        if(event.getSource() == btnLogowanie){
            pRegisterForm.setVisible(false);
            pLoginForm.setVisible(true);
            btnLogowanie.setSelected(true);
            btnRejestracja.setSelected(false);
        }
        else if(event.getSource() == btnRejestracja){
            pRegisterForm.setVisible(true);
            pLoginForm.setVisible(false);
            btnLogowanie.setSelected(false);
            btnRejestracja.setSelected(true);
        }
        else if(event.getSource() == LoginClose || event.getSource() == RegisterClose){
            System.exit(0);
        }
    }

}