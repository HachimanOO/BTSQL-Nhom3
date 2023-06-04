package fa.training.dao;

import fa.training.entities.LineItem;

import java.util.List;

public interface LineItemDAO {
    List<LineItem> getAllItemsByOrderId(int orderId);
    boolean addLineItem(LineItem item);
}