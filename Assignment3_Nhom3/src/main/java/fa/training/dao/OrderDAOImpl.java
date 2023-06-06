package fa.training.dao;

import fa.training.entities.Order;
import org.example.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderDAOImpl implements OrderDAO{
    private Database database;
    public OrderDAOImpl(){
        this.database = new Database();
    }
    @Override
    public List<Order> getAllOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();

        try {
            // Tạo câu truy vấn SQL
            String query = "SELECT order_id, order_date, customer_id, employee_id, total FROM Orders WHERE customer_id = ?";

            // Tạo đối tượng PreparedStatement
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setInt(1, customerId);

            // Thực thi câu truy vấn
            ResultSet resultSet = statement.executeQuery();

            // Lặp qua tất cả các bản ghi đơn hàng
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                Date orderDate = resultSet.getDate("order_date");
                int employeeId = resultSet.getInt("employee_id");
                double total = resultSet.getDouble("total");

                Order order = new Order(orderId, orderDate, customerId, employeeId, total);
                orders.add(order);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }



    @Override
    public boolean addOrder(Order order) {
        try {
            String sql = "INSERT INTO Orders (order_id, order_date, customer_id, employee_id, total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = database.getConnection().prepareStatement(sql);
            statement.setInt(1, order.getOrderId());
            statement.setDate(2, (Date) order.getOrderDate());
            statement.setInt(3, order.getCustomerId());
            statement.setInt(4, order.getEmployeeId());
            statement.setDouble(5, order.getTotal());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double computeOrderTotal(int orderId) {
        double totalPrice = 0.0;
        String sql = "SELECT computeOrderTotal(?) AS total_price";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalPrice = resultSet.getDouble("total_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalPrice;
    }

    @Override
    public boolean updateOrderTotal(int orderId) {
        String sql = "UPDATE Orders SET total = ? WHERE order_id = ?";
        Database database = new Database();
        try {
            Connection con = database.getConnection();
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            Scanner sc = new Scanner(System.in);
            System.out.println("Nhập total order mới:");

            double total;
            while (!sc.hasNextDouble()) {
                System.out.println("Vui lòng nhập một giá trị số thực hợp lệ:");
                sc.next();
            }
            total = sc.nextDouble();

            preparedStatement.setDouble(1, total);
            preparedStatement.setInt(2, orderId);
            int rowsAffected = preparedStatement.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
