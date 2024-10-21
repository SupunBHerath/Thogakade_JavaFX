package service.custom.impl;

import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Customer;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.SuperDao;
import repository.custom.CustomerDao;
import service.custom.CustomerService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.save(entity);

    }

    @Override
    public boolean updateCustomer(Customer customer) {
        CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.update(entity);
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        return customerDao.delete(id);
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        ModelMapper mapper = new ModelMapper();
        List<CustomerEntity> customerEntities = customerDao.findAll();
        ObservableList<Customer> customerDTOList = FXCollections.observableArrayList();
        customerEntities.forEach(customerEntity -> {
            Customer customerDTO = mapper.map(customerEntity, Customer.class);
            customerDTOList.add(customerDTO);
        });
        return customerDTOList;
    }
    @Override
    public ObservableList<String> getAllCustomerIDs() {
        CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        return customerDao.getAllId();
    }
}
