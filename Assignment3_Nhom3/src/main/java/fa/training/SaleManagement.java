package fa.training;

import fa.training.dao.*;
import fa.training.entities.Customer;
import fa.training.entities.LineItem;
import fa.training.entities.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import java.util.List;
import java.util.Scanner;

public class SaleManagement {
    private static CustomerDAO customerDAO = new CustomerDAOImpl();
    private static OrderDAO orderDAO = new OrderDAOImpl();
    private static LineItemDAO lineItemDAO = new LineItemDAOImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("---- Quản lý Đơn hàng ----");
            System.out.println("1. Hiển thị danh sách khách hàng");
            System.out.println("2. Hiển thị danh sách đơn hàng của khách hàng");
            System.out.println("3. Hiển thị danh sách sản phẩm trong đơn hàng");
            System.out.println("4. Tính tổng giá trị đơn hàng");
            System.out.println("5. Thêm khách hàng mới");
            System.out.println("6. Xóa khách hàng");
            System.out.println("7. Cập nhật thông tin khách hàng");
            System.out.println("8. Tạo đơn hàng mới");
            System.out.println("9. Thêm sản phẩm vào đơn hàng");
            System.out.println("10. Cập nhật tổng giá trị đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    getAllCustomers();
                    break;
                case 2:
                    System.out.print("Nhập ID khách hàng: ");
                    int customerId = scanner.nextInt();
                    getAllOrdersByCustomerId(customerId);
                    break;
                case 3:
                    System.out.print("Nhập ID đơn hàng: ");
                    int orderId = scanner.nextInt();
                    getAllLineItemsByOrderId(orderId);
                    break;
                case 4:
                    System.out.print("Nhập ID đơn hàng: ");
                    int orderId2 = scanner.nextInt();
                    computeOrderTotal(orderId2);
                    break;
                case 5:
                    addCustomer();
                    break;
                case 6:
                    System.out.print("Nhập ID khách hàng: ");
                    int customerId2 = scanner.nextInt();
                    deleteCustomer(customerId2);
                    break;
                case 7:
                    updateCustomer();
                    break;
                case 8:
                    addOrder();
                    break;
                case 9:
                    addLineItem();
                    break;
                case 10:
                    System.out.print("Nhập ID đơn hàng: ");
                    int orderId3 = scanner.nextInt();
                    updateOrderTotal(orderId3);
                    break;
                case 0:
                    System.out.println("Đã thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void getAllCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getCustomerId());
            System.out.println("Tên khách hàng: " + customer.getCustomerName());
            System.out.println("--------------------------------");
        }
    }

    private static void getAllOrdersByCustomerId(int customerId) {
        List<Order> orders = orderDAO.getAllOrdersByCustomerId(customerId);
        for (Order order : orders) {
            System.out.println("ID: " + order.getOrderId());
            System.out.println("Ngày đặt hàng: " + order.getOrderDate());
            System.out.println("ID khách hàng: " + order.getCustomerId());
            System.out.println("ID nhân viên: " + order.getEmployeeId());
            System.out.println("Tổng giá trị: " + order.getTotal());
            System.out.println("--------------------------------");
        }
    }

    private static void getAllLineItemsByOrderId(int orderId) {
        List<LineItem> lineItems = lineItemDAO.getAllItemsByOrderId(orderId);
        for (LineItem item : lineItems) {
            System.out.println("ID đơn hàng: " + item.getOrderId());
            System.out.println("ID sản phẩm: " + item.getProductId());
            System.out.println("Số lượng: " + item.getQuantity());
            System.out.println("Giá: " + item.getPrice());
            System.out.println("--------------------------------");
        }
    }

    private static void computeOrderTotal(int orderId) {
        double total = orderDAO.computeOrderTotal(orderId);
        System.out.println("Tổng giá trị đơn hàng: " + total);
    }

    private static void addCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập ID khách hàng: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng trống sau khi đọc số
        System.out.print("Nhập tên khách hàng: ");
        String customerName = scanner.nextLine();

        Customer customer = new Customer(customerId, customerName);
        boolean success = customerDAO.addCustomer(customer);
        if (!success) {
            System.out.println("Thêm khách hàng thành công.");
        } else {
            System.out.println("Thêm khách hàng thất bại.");
        }
    }

    private static void deleteCustomer(int customerId) {
        boolean success = customerDAO.deleteCustomer(customerId);
        if (success) {
            System.out.println("Xóa khách hàng thành công.");
        } else {
            System.out.println("Xóa khách hàng thất bại.");
        }
    }

    private static void updateCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập ID khách hàng: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng trống sau khi đọc số
        System.out.print("Nhập tên khách hàng mới: ");
        String customerName = scanner.nextLine();

        Customer customer = new Customer(customerId, customerName);
        boolean success = customerDAO.updateCustomer(customer);
        if (success) {
            System.out.println("Cập nhật thông tin khách hàng thành công.");
        } else {
            System.out.println("Cập nhật thông tin khách hàng thất bại.");
        }
    }

    private static void addOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập ID đơn hàng: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng trống sau khi đọc số
        System.out.print("Nhập ngày đặt hàng (yyyy-MM-dd): ");
        String orderDateStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilOrderDate = dateFormat.parse(orderDateStr);
            java.sql.Date sqlOrderDate = new java.sql.Date(utilOrderDate.getTime());
            System.out.print("Nhập ID khách hàng: ");
            int customerId = scanner.nextInt();
            System.out.print("Nhập ID nhân viên: ");
            int employeeId = scanner.nextInt();
            System.out.print("Nhập tổng giá trị: ");
            double total = scanner.nextDouble();
            Order order = new Order(orderId, sqlOrderDate, customerId, employeeId, total);

            boolean success = orderDAO.addOrder(order);
            if (success) {
                System.out.println("Thêm đơn hàng thành công.");
            } else {
                System.out.println("Thêm đơn hàng thất bại.");
            }
        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ.");
        }
    }

    private static void addLineItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập ID đơn hàng: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nhập ID sản phẩm: ");
        int productId = scanner.nextInt();
        System.out.print("Nhập số lượng: ");
        int quantity = scanner.nextInt();
        System.out.print("Nhập giá: ");
        double price = scanner.nextDouble();

        // Đọc bỏ dòng trống sau khi đọc price
        scanner.nextLine();

        LineItem item = new LineItem(orderId, productId, quantity, price);
        boolean success = lineItemDAO.addLineItem(item);
        if (success) {
            System.out.println("Thêm sản phẩm vào đơn hàng thành công.");
        } else {
            System.out.println("Thêm sản phẩm vào đơn hàng thất bại.");
        }
    }


    private static void updateOrderTotal(int orderId) {
        boolean success = orderDAO.updateOrderTotal(orderId);
        if (success) {
            System.out.println("Cập nhật tổng giá trị đơn hàng thành công.");
        } else {
            System.out.println("Cập nhật tổng giá trị đơn hàng thất bại.");
        }
    }
}
