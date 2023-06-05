package fa.training.dao;

import fa.training.entities.LineItem;
import fa.training.main.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LineItemDAOImpl implements LineItemDAO{
    @Override
    public List<LineItem> getAllItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM item Where orderId = ?";
        LineItem lineItem = null;
        List<LineItem> lineItemList = new ArrayList<>();
        Database database = new Database();
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,orderId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                lineItem = new LineItem(rs.getInt("orderId"), rs.getInt("productId"),
                        rs.getInt("quantity"),rs.getDouble("price"));
                lineItemList.add(lineItem);
                System.out.println(lineItemList);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lineItemList;
    }
    public Double computeOrderTotal(int orderId) {
        String sql = "SELECT * FROM item Where orderId = ?";
        LineItem lineItem = null;
        List<LineItem> lineItemList = new ArrayList<>();
        Database database = new Database();
        double total = 0;
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lineItem = new LineItem(rs.getInt("orderId"), rs.getInt("productId"),
                        rs.getInt("quantity"), rs.getDouble("price"));
                total = rs.getInt("quantity") * rs.getDouble("price");
                System.out.println("orderId: " +rs.getInt("orderId") + "\n" +"productId: " +
                        rs.getInt("productId") +"\n" + "total_price: " +  total);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return total;
    }
    @Override
    public boolean addLineItem(LineItem item) {
        String sql = "INSERT INTO `item`(`orderId`, `productId`, `quantity`, `price`) VALUES (?,?,?,?)";
        Database database = new Database();
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,item.getOrderId());
            preparedStatement.setInt(2,item.getProductId());
            preparedStatement.setInt(3,item.getQuantity());
            preparedStatement.setDouble(4,item.getPrice());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
