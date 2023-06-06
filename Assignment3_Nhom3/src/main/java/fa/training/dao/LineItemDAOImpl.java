package fa.training.dao;

import fa.training.entities.LineItem;
import org.example.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDAOImpl implements LineItemDAO{
    private Database database;
    public LineItemDAOImpl(){
        this.database = new Database();
    }
    @Override
    public List<LineItem> getAllItemsByOrderId(int orderId) {
        List<LineItem> lineItems = new ArrayList<>();
        try {
            String query = "SELECT order_id, product_id, quantity, price FROM LineItem WHERE order_id = ?";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");

                LineItem lineItem = new LineItem(orderId, productId, quantity, price);
                lineItems.add(lineItem);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lineItems;
    }

    @Override
    public boolean addLineItem(LineItem item) {
        String query = "INSERT INTO LineItem (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setInt(1, item.getOrderId());
            statement.setInt(2, item.getProductId());
            statement.setInt(3, item.getQuantity());
            statement.setDouble(4, item.getPrice());

                int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
