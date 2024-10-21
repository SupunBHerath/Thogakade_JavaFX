package service.custom;

import javafx.collections.ObservableList;
import dto.Customer;
import service.SuperService;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(String id);
    boolean deleteCustomer(String id);
    ObservableList<Customer> getAllCustomers();
    ObservableList<String> getAllCustomerIDs();
}
