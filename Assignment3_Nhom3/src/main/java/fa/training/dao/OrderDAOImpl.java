package fa.training.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderDAOImpl implements OrderDAO{
    @Override
    public List<Order> getAllOrdersByCustomerId(int customerId) {
        String sql = "SELECT * FROM order WHERE customerId = ?";
        Order order = null;
        List<Order> orderList = new ArrayList<>();
        try {
            Database database = new Database();
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                order = new Order(rs.getInt("orderId"), rs.getDate("date"), rs.getInt("customerId"),
                        rs.getInt("employeeId"), rs.getDouble("total"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }
    @Override
    public boolean addOrder(Order order) {
        String sql = "INSERT INTO `order`(`orderId`, `date`, `customerId`, `employeeId`, `total`) VALUES (?,?,?,?,?)";
        Database database = new Database();
        try{
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,order.getOrderId());
            preparedStatement.setDate(2, (Date) order.getOrderDate());
            preparedStatement.setInt(3,order.getCustomerId());
            preparedStatement.setInt(4,order.getEmployeeId());
            preparedStatement.setDouble(5,order.getTotal());
            return preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateOrderTotal(int orderId) {
        String sql = "UPDATE order SET total = ? Where orderId = ?";
        Database database = new Database();
        try {
            Connection con = database.getConnection();
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            Scanner sc = new Scanner(System.in);
            double total = sc.nextDouble();
            preparedStatement.setDouble(1,total);
            preparedStatement.setInt(2,orderId);
            int rowsAffected = preparedStatement.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
