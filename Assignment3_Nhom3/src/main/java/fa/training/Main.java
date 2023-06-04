package fa.training;

import fa.training.dao.CustomerDAO;
import fa.training.dao.CustomerDAOImpl;
import fa.training.dao.LineItemDAO;
import fa.training.dao.LineItemDAOImpl;
import fa.training.entities.Customer;
import fa.training.entities.LineItem;

public class Main {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        customerDAO.getAllCustomers();
//        customerDAO.addCustomer(new Customer(6,"Jonh swith"));
        LineItemDAO lineItemDAO = new LineItemDAOImpl();
        lineItemDAO.addLineItem(new LineItem(3,2,1,2500));
    }
}
