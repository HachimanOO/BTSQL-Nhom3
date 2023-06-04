package fa.training.dao;

import fa.training.entities.Customer;
import org.example.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO{
    private Database database;
    public CustomerDAOImpl(){
        this.database = new Database();
    }
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> listCustomer = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Customer cus = new Customer(rs.getInt("customer_id"),rs.getString("customer_name"));
                 listCustomer.add(cus);
            }
            for(Customer  cus : listCustomer ) {
                System.out.println(cus);
            }
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
        return listCustomer;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (customer_id, customer_name) VALUES (?, ?)";
        try {
            PreparedStatement statement = database.getConnection().prepareStatement(sql);
            statement.setInt(1, customer.getCustomerId());
            statement.setString(2, customer.getCustomerName());
            System.out.println("Added success");
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public boolean deleteCustomer(int customerId) {
        String sql = "{call delete_customer(?)}";
        try {
            CallableStatement callableStatement = (CallableStatement) database.getConnection().prepareCall(sql);
            callableStatement.setInt(1, customerId);
            return callableStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    public static void main(String[] args) {
        CustomerDAO test = new CustomerDAOImpl();
        test.getAllCustomers();
    }
}
