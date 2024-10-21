package controller.order;

import dto.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {

    public static boolean addOrderDetail(List<OrderDetail> orderDetails) throws SQLException {
        for (OrderDetail orderDetail : orderDetails){
             boolean isOrderDetailAdd = addOrderDetail(orderDetail);
             if (!isOrderDetailAdd){
                 return false;
             }
        }
        return true;
    }

    public static boolean addOrderDetail(OrderDetail orderDetail) throws SQLException {
       return CrudUtil.execute("INSERT INTO orderdetail VALUES(?,?,?,?)",
                orderDetail.getOrderId(),
                orderDetail.getItemCode(),
                orderDetail.getQty(),
                orderDetail.getDiscount()
        );
    }


}
