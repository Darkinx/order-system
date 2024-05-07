package com.darkin.electronicordersystem;

import com.darkin.electronicordersystem.UIComponents.OrderContainerVbox;
import com.darkin.electronicordersystem.models.Order;
import com.darkin.electronicordersystem.models.OrderDAO;
import com.darkin.electronicordersystem.models.OrderItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class OrderHistoryController {
    private OrderDAO orderDAO = new OrderDAO();
    private int userId;
    private ObservableList<Order> orders = FXCollections.observableArrayList();

    @FXML
    private VBox orderContainerVbox;

    void Initialize(){
         orders = getOrders();
         orderContainerVbox.getChildren().clear();
         for (Order order : orders){
             ObservableList<OrderItem> orderItems = getOrderItems(order.getId());
             OrderContainerVbox orderContainer = new OrderContainerVbox(
                     order.getOrder_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                     order.getRef_no(),
                     order.getTotal_price(),
                     orderItems
             );
             orderContainerVbox.getChildren().add(orderContainer);
         }

    }

    void setData(int userId){
        this.userId = userId;
    }

    private ObservableList<Order> getOrders(){
        ObservableList<Order> orders = FXCollections.observableArrayList();
        try {
            orders = orderDAO.getOrders(userId);
        }catch (SQLException e){
            System.err.println("SQL Error on fetching order items: " + e);
        }catch (ClassNotFoundException e){
            System.err.println("Class exception error on fetching order items: " + e);
        }
        return  orders;
    }

    private ObservableList<OrderItem> getOrderItems(int orderId){
        ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
        try {
            orderItems = orderDAO.getOrderItems(orderId);
        }catch (SQLException e){
            System.err.println("SQL Error on fetching orders: " + e);
        }catch (ClassNotFoundException e){
            System.err.println("Class exception error on fetching orders: " + e);
        }
        return  orderItems;
    }
}