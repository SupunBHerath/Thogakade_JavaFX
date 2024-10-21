package service.custom.impl;

import dto.OrderDetail;
import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Item;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.ItemDao;
import service.custom.ItemService;
import util.DaoType;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    @Override
    public boolean addItem(Item item) {
        ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        ModelMapper mapper = new ModelMapper();
        return itemDao.save(mapper.map(item, ItemEntity.class));
    }

    @Override
    public boolean updateItem(Item item) {
        ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        ModelMapper mapper = new ModelMapper();
        return itemDao.update(mapper.map(item, ItemEntity.class));
    }

    @Override
    public Item searchItem(String itemCode) {
        ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        ModelMapper mapper = new ModelMapper();
        ItemEntity itemEntity = itemDao.search(itemCode);
        if (itemEntity == null) return null;
        return mapper.map(itemEntity, Item.class);
    }

    @Override
    public boolean deleteItem(String itemCode) {
        ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        return itemDao.delete(itemCode);
    }

    @Override
    public ObservableList<Item> getAllItem() {
        ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        ModelMapper mapper = new ModelMapper();
        List<ItemEntity> itemEntities = itemDao.findAll();
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        itemEntities.forEach(itemEntity -> {
            itemList.add(mapper.map(itemEntity, Item.class));
        });
        return itemList;
    }
    @Override
    public ObservableList<String> getAllItemIds() {
        ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        return itemDao.getAllId();
    }

    @Override
    public boolean updateStock(List<OrderDetail> orderDetails) {
        return false;
    }
}
