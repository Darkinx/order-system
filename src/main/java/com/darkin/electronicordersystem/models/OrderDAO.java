package com.darkin.electronicordersystem.models;

import com.darkin.electronicordersystem.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {
    private DatabaseConnection dbUtil = new DatabaseConnection();

    public ObservableList<Order> getOrders(int account_id) throws SQLException, ClassNotFoundException{
        String stmt = "SELECT id, order_date, total_price, ref_no FROM `order` WHERE `account_id`=" + account_id + ";";
        try {
            CachedRowSet tmpRowSet = dbUtil.selectQuery(stmt);
            ObservableList<Order> orderList = getOrderList(tmpRowSet);
            return orderList;
        }catch (SQLException e){
            System.err.println("SQL Select Operation failed: " + e);
            throw e;
        }
    }

    public ObservableList<OrderItem> getOrderItems(int order_id) throws SQLException, ClassNotFoundException{
        String stmt = "SELECT order_item.id, product.name, quantity, product.image_path, order_item.price FROM `order_item` INNER JOIN `product` ON `product_id`=product.id WHERE `order_id`=" + order_id + ";";
        try {
            CachedRowSet tmpRowSet = dbUtil.selectQuery(stmt);
            ObservableList<OrderItem> orderItems = getOrderItemsList(tmpRowSet);
            return orderItems;
        }catch (SQLException e){
            System.err.println("SQL Select Operation failed: " + e);
            throw e;
        }
    }

    private static ObservableList<OrderItem> getOrderItemsList(ResultSet rs) throws SQLException, ClassNotFoundException{
        ObservableList<OrderItem> orderItemLst = FXCollections.observableArrayList();
        while(rs.next()){
            OrderItem orderItem = new OrderItem();
            orderItem.setId(rs.getInt("id"));
            orderItem.setProductName(rs.getString("name"));
            orderItem.setQuantity(rs.getInt("quantity"));
            orderItem.setImagePath(rs.getString("image_path"));
            orderItem.setPrice(rs.getDouble("price"));
            orderItemLst.add(orderItem);
        }

        return orderItemLst;
    }
    private static ObservableList<Order> getOrderList(ResultSet rs) throws SQLException, ClassNotFoundException{
        ObservableList<Order> orderLst = FXCollections.observableArrayList();
        while(rs.next()){
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setOrder_date(rs.getString("order_date"));
            order.setRef_no(rs.getString("ref_no"));
            order.setTotal_price(rs.getDouble("total_price"));
            orderLst.add(order);
        }

        return orderLst;
    }

}
