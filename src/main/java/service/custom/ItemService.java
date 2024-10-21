package service.custom;

import dto.OrderDetail;
import javafx.collections.ObservableList;
import dto.Item;
import service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {
    boolean addItem(Item item);

    boolean updateItem(Item item);

    Item searchItem(String itemCode);

    boolean deleteItem(String itemCode);

    ObservableList<Item> getAllItem();

    ObservableList<String> getAllItemIds();

    boolean updateStock(List<OrderDetail> orderDetails);
}
