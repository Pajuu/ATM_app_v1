package com.example.bankstudenta;

import java.io.FileWriter;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private float amount=0;
    private int fromIndex=0, toIndex=0;
    public Transaction() {

    }

    public Transaction(float amount, int fromIndex, int toIndex) {
        this.amount = amount;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }
    public boolean makeTransaction(){
        try{
            return this.addTransactionToDB(this);
        }catch (Exception e){
            System.out.println("Problem z transakcja: ");
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean addTransactionToDB(Transaction newTransaction){
        try{
            Connection connection = new DB_Connection().makeConnection();
            String query = "INSERT INTO transactions (from_index, to_index, amount, date) "
                    +"values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, newTransaction.fromIndex);
            preparedStatement.setInt(2,newTransaction.toIndex);
            preparedStatement.setFloat(3,newTransaction.amount);
            preparedStatement.setTimestamp(4, Timestamp.from(Instant.now()));

            preparedStatement.execute();

            connection.close();


            return true;
        }catch (Exception e) {
            System.out.println("Problem z transakcja: ");
            System.out.println(e.getMessage());

            return false;
        }
    }

    public List<String> getTransactionsHistoryByUser(User u){

        try{
            List<String> transactions = new ArrayList<>();

            Connection connection = new DB_Connection().makeConnection();
            String query = "SELECT \n" +
                    "t.amount, \n" +
                    "t.date, \n" +
                    "f_u .first_name as from_name, \n" +
                    "f_u .last_name as from_last_name,\n" +
                    "t_u .first_name as to_name, \n" +
                    "t_u .last_name as to_last_name\n" +
                    "FROM transactions as t\n" +
                    "Inner JOIN users as f_u ON t.from_id = f_u .id\n" +
                    "Inner JOIN users as t_u ON t.to_id = t_u .id\n" +
                    "WHERE t.from_id = "+u.getId()+" or t.from_id = "+u.getId();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                String record = resultSet.getString("date")+"   | OD: "+resultSet.getString("from_name")+" "+resultSet.getString("from_last_name")+
                        " ------> DO: "+resultSet.getString("to_name")+" "+resultSet.getString("to_last_name")+
                        " |     "+resultSet.getString("amount")+"zł ";


                transactions.add(record);
            }
            connection.close();
            return transactions;


        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();

        }

    }



    public float getAmount() {
        return amount;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "amount=" + amount +
                ", fromIndex=" + fromIndex +
                ", toIndex=" + toIndex +
                '}';
    }
}
