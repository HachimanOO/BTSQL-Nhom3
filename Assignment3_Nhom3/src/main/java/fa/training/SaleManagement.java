package fa.training;

import fa.training.dao.CustomerDAO;
import fa.training.dao.CustomerDAOImpl;
import fa.training.dao.LineItemDAO;
import fa.training.dao.LineItemDAOImpl;
import fa.training.entities.Customer;
import fa.training.entities.LineItem;

public class SaleManagement {
//     public static void main(String[] args) {
//         CustomerDAO customerDAO = new CustomerDAOImpl();
//         customerDAO.getAllCustomers();
// //        customerDAO.addCustomer(new Customer(6,"Jonh swith"));
//         LineItemDAO lineItemDAO = new LineItemDAOImpl();
//         lineItemDAO.addLineItem(new LineItem(3,2,1,2500));
//     }
    public static void main(String[] args) {
        getAllItemsByOrderId();
        //getAllOrdersByCustomerId();
        //addCustomer();
        //deleteCustomer();
    }
    public static void getAllItemsByOrderId() {
        LineItemDAOImpl lineItemDAO = new LineItemDAOImpl();
        List<LineItem> lineItemList = lineItemDAO.getAllItemsByOrderId(905);
        for (LineItem lineItem : lineItemList){
            System.out.println(lineItem);
        }
    }

    public static void getAllOrdersByCustomerId() {
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        List<Order> orderList = orderDAO.getAllOrdersByCustomerId(4);
        for (Order order : orderList){
            System.out.println(order);
        }
    }

    public static void addCustomer() {
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        System.out.println(customerDAO.addCustomer(new Customer(5, "Linh")));
    }

    public static void deleteCustomer() {
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        System.out.println(customerDAO.deleteCustomer(5));
    }
}
