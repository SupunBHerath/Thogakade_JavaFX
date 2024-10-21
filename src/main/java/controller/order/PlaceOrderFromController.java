package controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import dto.*;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.ItemService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFromController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtStock;

    @FXML
    private JFXTextField txtUnitPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) -> {
            loadItemData(newValue);
        });
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) -> {
            loadCustomerData(newValue);
        });


        loadDateAndTime();
        loadCustomerIds();
        loadItemCodes();
    }

    ObservableList<CartTM> cart = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Integer qty = Integer.parseInt(txtQty.getText());

        Double total = unitPrice * qty;
        cart.add(
                new CartTM(
                        cmbItemCode.getValue(),
                        txtDescription.getText(),
                        qty,
                        unitPrice,
                        total
                )
        );
        tblCart.setItems(cart);
        calcNetTotal();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = txtOrderId.getText();
        String customerId = cmbCustomerId.getValue();
        String orderDate = lblDate.getText();

        LocalDate now = LocalDate.now();

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (CartTM cartTM : cart){
            String itemCode = cartTM.getItemCode();
            Integer qty = cartTM.getQty();
            orderDetails.add(new OrderDetail(orderId,itemCode,qty,0.0));
        }

        try {
            if(new OrderController().placeOrder(new Order(orderId,now,customerId,orderDetails))) {
                new Alert(Alert.AlertType.INFORMATION, "Order Placed!!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // ---------------------------------------------

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(
                    now.getHour() + " : " + now.getMinute() + " : " + now.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void loadCustomerIds() {
        CustomerService service = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
         cmbCustomerId.setItems(service.getAllCustomerIDs());
    }

    private void loadItemCodes() {
        ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

        cmbItemCode.setItems(itemService.getAllItemIds());
    }

    private void loadItemData(String itemCode) {
        ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

        Item item = itemService.searchItem(itemCode);

        txtDescription.setText(item.getDescription());
        txtStock.setText(item.getQty().toString());
        txtUnitPrice.setText(item.getUnitPrice().toString());
    }

    private void loadCustomerData(String customerId) {
        CustomerService service = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
        Customer customer =service.searchCustomer(customerId);

        txtName.setText(customer.getName());
        txtCity.setText(customer.getCity());
        txtSalary.setText(customer.getSalary().toString());
    }


    private void calcNetTotal() {
        Double total = 0.0;

        for (CartTM cartTM : cart) {
            total += cartTM.getTotal();
        }

        lblNetTotal.setText(total.toString());


    }


    public void btnCommitOnAction(ActionEvent actionEvent) throws SQLException {
        DBConnection.getInstance().getConnection().commit();
    }
}
