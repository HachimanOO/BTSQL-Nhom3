package fa.training.dao;

import fa.training.entities.Order;
import org.example.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO{
    private Database database;
    public OrderDAOImpl(){
        this.database = new Database();
    }
    @Override
    public List<Order> getAllOrdersByCustomerId(int customerId) {
        return null;
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

    @Override
    public boolean updateOrderTotal(int orderId) {
        return false;
    }
}
