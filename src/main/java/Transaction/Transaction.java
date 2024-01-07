package Transaction;
import DB.DB_Connection;
import java.sql.*;
import java.time.Instant;

public class Transaction {
    private float amount=0;
    private int fromIndex=0, toIndex=0;

    //Constructors
    public Transaction() {
    }
    public Transaction(float amount, int fromIndex, int toIndex) {
        this.amount = amount;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    //Methods
    public void addTransactionToDB(){
        try{
            Connection connection = new DB_Connection().makeConnection();
            String query = "INSERT INTO transactions (from_id, to_id, amount, date) "
                    +"values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.fromIndex);
            preparedStatement.setInt(2,this.toIndex);
            preparedStatement.setFloat(3,this.amount);
            preparedStatement.setTimestamp(4, Timestamp.from(Instant.now()));
            preparedStatement.execute();
            connection.close();
        }catch (Exception e) {
            System.out.println("Problem z transakcja: ");
            System.out.println(e.getMessage());
        }
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
