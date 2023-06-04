package fa.training.dao;

import fa.training.entities.LineItem;
import org.example.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LineItemDAOImpl implements LineItemDAO{
    private Database database;
    public LineItemDAOImpl(){
        this.database = new Database();
    }
    @Override
    public List<LineItem> getAllItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM lineitem WHERE order_id = ?";
            try {
                PreparedStatement statement = database.getConnection().prepareStatement(sql);
                statement.setInt(1, orderId);
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    LineItem lineItem = new LineItem(rs.getInt("order_id"), rs.getInt("product_id"), rs.getInt("quantity"), rs.getDouble("price"));
                    lineItemList.add(lineItem);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return lineItemList;
    }

    @Override
    public boolean addLineItem(LineItem item) {
        try {
            String sql = "INSERT INTO LineItem (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = database.getConnection().prepareStatement(sql);
            statement.setInt(1, item.getOrderId());
            statement.setInt(2, item.getProductId());
            statement.setInt(3, item.getQuantity());
            statement.setDouble(4, item.getPrice());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
