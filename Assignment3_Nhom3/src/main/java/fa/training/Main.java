package fa.training;

import fa.training.dao.CustomerDAO;
import fa.training.dao.CustomerDAOImpl;
import fa.training.dao.LineItemDAO;
import fa.training.dao.LineItemDAOImpl;
import fa.training.entities.Customer;
import fa.training.entities.LineItem;

public class Main {
    public static void main(String args[]) {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        LineItemDAO lineItemDAO = new LineItemDAOImpl();
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose option");
        int choice = sc.nextInt();
        switch (choice) {
            case 1 : customerDAO.getAllCustomers();
            break;
            case 2:
//                int id = sc.nextInt();
                orderDAO.getAllOrdersByCustomerId(1);
            break;
            case 3: lineItemDAO.getAllItemsByOrderId(0);
            break;
            case 4 : lineItemDAO.computeOrderTotal(0);
            break;
            case 5 :
                customerDAO.updateCustomer(new Customer(1,"Khang"));
                break;
            case 6: orderDAO.updateOrderTotal(1); // chưa chạy được
            break;
            case 7: lineItemDAO.addLineItem(new LineItem(3,5,34,87));
            break;
            case 8: orderDAO.addOrder(new Order(3, Date.valueOf("2023-02-10"),3,5,54));
            break;
            case 9: customerDAO.addCustomer(new Customer(4,"Hùng"));
            break;
            case 10 : customerDAO.deleteCustomer(4);
            break;
        }

    }
}
