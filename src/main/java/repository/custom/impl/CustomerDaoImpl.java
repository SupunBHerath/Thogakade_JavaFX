package repository.custom.impl;

import dto.Customer;
import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import repository.custom.CustomerDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity customer) {
        System.out.println("Repository : "+customer);
                String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";

        try {
           return CrudUtil.execute(
                   SQL,
                   customer.getId(),
                   customer.getTitle(),
                   customer.getName(),
                   customer.getDob(),
                   customer.getSalary(),
                   customer.getAddress(),
                   customer.getCity(),
                   customer.getProvince(),
                   customer.getPostalCode()
           );

        } catch (SQLException e) {

        }
        return false;

    }

    @Override
    public boolean update(CustomerEntity entity) {
        String sql = "Update customer SET CustName=?, CustTitle=?, DOB=?,  salary=?,  CustAddress=?, City=?, Province=?, PostalCode=? WHERE CustID=?";
        try {
            return (CrudUtil.execute(sql, entity.getName(), entity.getTitle(), entity.getDob(), entity.getSalary(), entity.getAddress(), entity.getCity(), entity.getProvince(), entity.getPostalCode(), entity.getId()));
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM Customer WHERE CustID= '" + id + "'";
        try {
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<CustomerEntity> findAll() {
        ObservableList<CustomerEntity> customerObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customer";
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                CustomerEntity customerEntity = new CustomerEntity(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postalCode"));
                customerObservableList.add(customerEntity);
            }
            return customerObservableList;
        } catch (SQLException e) {
            return customerObservableList;
        }

    }

    @Override
    public CustomerEntity search(String id) {
        String sql = "SELECT * FROM customer WHERE CustID=?";
        try {
            try (ResultSet resultSet = CrudUtil.execute(sql, id)) {
                if (resultSet.next()) {
                    return new CustomerEntity(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDate(4).toLocalDate(),
                            resultSet.getDouble(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9));
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public ObservableList<String> getAllId() {
        ObservableList<String> custIDList = FXCollections.observableArrayList();
        try {
            ResultSet resCustIDList = CrudUtil.execute("SELECT CustID FROM customer");
            while (resCustIDList.next()) {
                custIDList.add(resCustIDList.getString("CustID"));
            }
            return custIDList;
        } catch (SQLException e) {
            return custIDList;
        }
    }
}
