package controller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.Item;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.ItemService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFromController implements Initializable {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPacSize;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblItems;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtUnitPrice;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPacSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadTable();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, item, newValue) -> {
            if (newValue != null) {
                setValueToText(newValue);
            }
        });
    }

    private void setValueToText(Item newValue) {
        txtItemCode.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtPackSize.setText(newValue.getPackSize());
        txtUnitPrice.setText(newValue.getUnitPrice().toString());
        txtQty.setText(newValue.getQty().toString());
    }

    private void loadTable() {
        ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
        ObservableList<Item> items = itemService.getAllItem();
        tblItems.setItems(items);
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
        if (itemService.addItem(
                new Item(
                        txtItemCode.getText(),
                        txtDescription.getText(),
                        txtPackSize.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQty.getText())
                ))) {
            new Alert(Alert.AlertType.INFORMATION, "Item Added!!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item Not Added!!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

        if (itemService.deleteItem(txtItemCode.getText())) {
            new Alert(Alert.AlertType.INFORMATION, txtItemCode.getText() + " Item Deleted").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, txtItemCode.getText() + " Item Not Deleted").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
        Item item = itemService.searchItem(txtItemCode.getText());
        if (item != null) {
            setValueToText(item);
        } else {
            new Alert(Alert.AlertType.ERROR, txtItemCode.getText() + " Item Not Found").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
        if (itemService.updateItem(new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Item not Updated!").show();
        }
    }


    public void btnCustomerFromOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/customer_from.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
