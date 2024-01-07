package com.example.bankstudenta;
import Transaction.Transaction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import User.User;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerAfterLogin extends Controller implements Initializable  {
    @FXML
    private Text name_and_index, students_money;

    @FXML
    private TableView<historyRecord> transaction_history_table;

    @FXML
    private TableColumn historyDateCol, historyToCol, historyAmountCol;

    @FXML
    private TextField WithdrawText, DepositText, transferToIndeks, transferAmount;


    private void showClientInfo(){
        DecimalFormat df = new DecimalFormat("#.00");

        this.name_and_index.setText(user.getFirst_name()+" "+user.getIndex());
        this.students_money.setText( df.format(user.getBalance())+" zł");
        this.transaction_history_table.getItems().clear();

        historyDateCol.setCellValueFactory(new PropertyValueFactory<historyRecord, String>("date"));
        historyAmountCol.setCellValueFactory(new PropertyValueFactory<historyRecord, String>("amount"));
        historyToCol.setCellValueFactory(new PropertyValueFactory<historyRecord, String>("to"));

        for (String[] element:user.getUserTransferHistory() ) {
            historyRecord r = new historyRecord(element[1] + " " + element[2], element[3] + " " + element[4], element[5], element[0], element[6], element[7]);
            this.transaction_history_table.getItems().add(r);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showClientInfo();
    }

    @FXML
    private void handleWithdraw() {
        try{
            float kwota = Float.parseFloat(WithdrawText.getText());
            if(kwota> user.getBalance()) { throw new RuntimeException("Brak środków na koncie!");}

            Transaction t = new Transaction(kwota, user.getId(), 0);
            t.addTransactionToDB();
            WithdrawText.setText("");
            user.getUserBalanceFromDB();
            showClientInfo();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleDeposit() {
        float kwota = Float.parseFloat(DepositText.getText());
        Transaction t = new Transaction(kwota, 0,  user.getId());
        t.addTransactionToDB();
        DepositText.setText("");
        user.getUserBalanceFromDB();
        showClientInfo();
    }

    @FXML
    private void handleTransfer() {
        try{
            float kwota = Float.parseFloat(transferAmount.getText());
            int toID = new User().getIdFromDB(Integer.parseInt(transferToIndeks.getText()));

            if(kwota> user.getBalance()) { throw new RuntimeException("Brak środków na koncie!");}
            if (toID == -500){ throw new RuntimeException("Nie znaleziono użytkownika o takim indeksie!");}

            Transaction t = new Transaction(kwota, user.getId(), toID);
            t.addTransactionToDB();
            transferAmount.setText("");
            transferToIndeks.setText("");
            user.getUserBalanceFromDB();
            showClientInfo();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleLogOut(ActionEvent event) throws IOException {
        user = new User();
        if (!user.isLoggedIn()) {
            final double[] xOffSet = {0};
            final double[] yOffSet = {0};

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainMenu.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    public static class historyRecord{
        private final String from;
        private final String to;
        private final String amount;
        private final String date;
        private final String fromId;
        private final String toId;

        public historyRecord(String from, String to, String amount, String date, String fromId, String toId) {

            this.from = from;
            this.to = to;
            this.amount = amount;
            this.date = date;
            this.fromId = fromId;
            this.toId = toId;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            if(toId.equals(String.valueOf(user.getId())) && !fromId.equals("0")){
                return "Przelew od "+from;
            }
            else if (fromId.equals("0")){
                return "Wpłata";
            } else if (toId.equals("0")) {
                return "Wypłata";
            } else {
                return "Przelew do "+to;
            }
        }

        public String getAmount() {
            DecimalFormat df = new DecimalFormat("#.00");
            if (Integer.parseInt(fromId) == user.getId()){

                return df.format(Float.parseFloat(amount)*-1)+" zł";
            }
            return df.format(Float.parseFloat(amount))+" zł";
        }

        public String getDate() {
            LocalDate a = LocalDate.parse(date.substring(0,date.length()-9));

            return String.valueOf(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(a));
        }
    }
}
