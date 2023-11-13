package com.example.bankstudenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private int index = -1;
    private float balance;
    private String password, first_name, last_name;

    public User(int id) {

    }

    private User(int index, float balance, String password, String first_name, String last_name) {
        this.index = index;
        this.balance = balance;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }



    public void registerForm(){
        int index = 9999;
        String first_name = "Jan";
        String last_name = "Kowalski";
        String p1 = "test2";
        String p2 = "test2";
        addUserToDB(new User(index, 0, p1, first_name, last_name), p2);
    }

    private void addUserToDB(User newUser, String password2){
        try{
            Connection connection = new DB_Connection().makeConnection();
            if(!newUser.password.equals(password2)) throw new RuntimeException("Podane hasła musza być identyczne!");

            String query = "INSERT INTO users (student_id, first_name, last_name, password) "
                    +"values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, newUser.index);
            preparedStatement.setString(2,newUser.first_name);
            preparedStatement.setString(3,newUser.last_name);
            preparedStatement.setString(4,password2);

            preparedStatement.execute();

            connection.close();



        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User loginUser(int index, String password){
        try{
            Connection connection = new DB_Connection().makeConnection();
            String query = "SELECT `first_name`, `last_name`, `student_id` FROM users WHERE `student_id` = "+index+" AND `password` = '"+password+"' ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                System.out.println(resultSet.getString("first_name"));
                System.out.println(resultSet.getString("last_name"));
                System.out.println(resultSet.getString("student_id"));
            }
            connection.close();
            return new User();

        }catch (Exception e){
            System.out.println( e.getMessage());
            return new User();

        }


    }

    public Float getUserBalanceFromDB(){
        try{
            Connection connection = new DB_Connection().makeConnection();
            String query = "SELECT SUM(IF(from_id="+this.getId()+", amount*-1, amount)) as amount \n" +
                    "FROM transactions WHERE from_id = "+this.getId()+" or to_id = "+this.getId();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                this.balance = resultSet.getFloat("amount");
            }
            connection.close();

            return this.balance;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public User(){
    }
    public int getIndex() {
        return index;
    }

    public float getBalance() {
        return balance;
    }

    public String getFirst_name() {
        return first_name;
    }

    public boolean isLoggedIn(){
        return this.index!=-1;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "index=" + index +
                ", balance=" + balance +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
