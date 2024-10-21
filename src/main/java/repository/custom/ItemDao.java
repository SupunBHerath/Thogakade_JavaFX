package repository.custom;

import entity.ItemEntity;
import repository.CrudRepository;

public interface ItemDao extends CrudRepository<ItemEntity,String> {
}
