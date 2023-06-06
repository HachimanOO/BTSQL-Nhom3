package fa.training.dao;

import fa.training.entities.Customer;
import org.example.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;

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
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
        return listCustomer;
    }

    public boolean addCustomer(Customer customer) {
        try {
            // Tạo đối tượng CallableStatement để gọi Stored Procedure
            CallableStatement statement = database.getConnection().prepareCall("{CALL add_customer(?, ?)}");

            // Thiết lập giá trị cho các tham số
            statement.setInt(1, customer.getCustomerId());
            statement.setString(2, customer.getCustomerName());

            // Thực thi Stored Procedure
            boolean result = statement.execute();

            statement.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean deleteCustomer(int customerId) {
        try (
             CallableStatement statement = database.getConnection().prepareCall("{CALL deleteCustomer(?)}")) {
            // Set input parameter
            statement.setInt(1, customerId);

            // Execute the stored procedure
            boolean success = statement.execute();

            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try (
             CallableStatement statement = database.getConnection().prepareCall("{CALL update_customer(?, ?)}")) {
            // Set input parameters
            statement.setInt(1, customer.getCustomerId());
            statement.setString(2, customer.getCustomerName());

            // Execute the stored procedure
            boolean success = statement.executeUpdate() > 0;

            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        CustomerDAO test = new CustomerDAOImpl();
        test.getAllCustomers();
    }
}
