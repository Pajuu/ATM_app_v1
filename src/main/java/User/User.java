package User;
import DB.DB_Connection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    private int id =  -1;
    private int index = -1;
    private float balance = 0;
    private String first_name, last_name;

    //Constructors
    public User() {
    }

    //Methods
    public void addUserToDB(int index, String first_name, String last_name ,String p1, String p2){
        try{
            Connection connection = new DB_Connection().makeConnection();
            if(!p1.equals(p2)) throw new RuntimeException("Podane hasła musza być identyczne!");
            String hashed = BCrypt.hashpw(p1, BCrypt.gensalt());
            String query = "INSERT INTO users (student_id, first_name, last_name, password) "
                    +"values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, index);
            preparedStatement.setString(2,first_name);
            preparedStatement.setString(3,last_name);
            preparedStatement.setString(4,hashed);
            preparedStatement.execute();
            connection.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loginUser(int index, String password){
        try{
            Connection connection = new DB_Connection().makeConnection();
            String query = "SELECT `id`, `first_name`, `last_name`, `student_id`,`password` FROM users WHERE `student_id` = "+index+" ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                if (BCrypt.checkpw(password, resultSet.getString("password"))) {
                    this.id = resultSet.getInt("id");
                    this.first_name = resultSet.getString("first_name");
                    this.last_name = resultSet.getString("last_name");
                    this.index = resultSet.getInt("student_id");
                    this.balance = this.getUserBalanceFromDB();
                }
                connection.close();
            }else{
                connection.close();
                throw new Exception("Zły indeks lub hasło!");
            }
        }catch (Exception e){
            System.out.println( e.getMessage());
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
            return null;
        }
    }

    public List<String[]> getUserTransferHistory() {
        try{
            if(!this.isLoggedIn()) throw new Exception("Niezalogowany");
            List<String[]> transactions = new ArrayList<>();
            Connection connection = new DB_Connection().makeConnection();
            String query = "SELECT \n" +
                    "t.amount, \n" +
                    "t.date, \n" +
                    "f_u .id as from_id, \n" +
                    "f_u .first_name as from_name, \n" +
                    "f_u .last_name as from_last_name,\n" +
                    "t_u .id as to_id, \n" +
                    "t_u .first_name as to_name, \n" +
                    "t_u .last_name as to_last_name\n" +
                    "FROM transactions as t\n" +
                    "Inner JOIN users as f_u ON t.from_id = f_u .id\n" +
                    "Inner JOIN users as t_u ON t.to_id = t_u .id\n" +
                    "WHERE t.from_id = "+this.getId()+" or t.to_id = "+this.getId()+"\n" +
                    "ORDER BY t.date";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                String[] record = {rs.getString("date"), rs.getString("from_name"),rs.getString("from_last_name"), rs.getString("to_name"),rs.getString("to_last_name"),rs.getString("amount"), rs.getString("from_id"),rs.getString("to_id"),};
                transactions.add(record);
            }
            connection.close();
            Collections.reverse(transactions);
            return transactions;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public int getIdFromDB(int student_id){
        int result;
        try{
            Connection connection = new DB_Connection().makeConnection();
            String query = "SELECT id FROM users WHERE student_id ="+student_id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                result = resultSet.getInt("id");
                return result;
            }
            connection.close();

            throw new RuntimeException("Nie znaleziono użytkownika o takim indeksie (User.java)");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return -500;
        }
    }

    //getters
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
                "id=" + id +
                ", index=" + index +
                ", balance=" + balance +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
