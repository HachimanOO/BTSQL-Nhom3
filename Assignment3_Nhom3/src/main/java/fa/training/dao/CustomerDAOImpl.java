package fa.training.dao;

import fa.training.entities.Customer;
import fa.training.main.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO{
    private Database database;
    public CustomerDAOImpl(){
        this.database =new Database();
    }


    static final List<Customer> customerList = new ArrayList<>();


    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO `customer`(`customerId`, `customerName`) VALUES (?,?)";
        Database database = new Database();
        try {
            Connection con = database.getConnection();
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,customer.getCustomerId());
            preparedStatement.setString(2,customer.getCustomerName());
            int rowsAffected = preparedStatement.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            return rowsAffected > 0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        String sql ="DELETE FROM `customer` WHERE customerId = ?";
        Database database = new Database();
        try {
            Connection con = database.getConnection();
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,customerId);
            int rowsAffected = preparedStatement.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Customer> getAllCustomers(){
        String sql = "SELECT * FROM customer";
        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("customerId"), rs.getString("customerName"));
                customerList.add(customer);
            }
            for (Customer customer : customerList) {
                System.out.println(customer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }
    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET customerName = ? WHERE customerId = ?";
        try {
            Connection con = database.getConnection();
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, customer.getCustomerName());
            statement.setInt(2, customer.getCustomerId());
            int rowsAffected = statement.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
