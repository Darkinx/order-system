package com.darkin.electronicordersystem.UIComponents;

import com.darkin.electronicordersystem.models.OrderItem;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OrderContainerVbox extends VBox {
    private final GridPane dateReferenceGridPane;
    private final Label dateTimeLabel;
    private final Label  refNoLabel;
    private VBox orderItemContainerVbox;
    private final AnchorPane totalContainerAnchorPane;
    private final Label totalPriceLabel;
    private final double SPACING = 20d;
    private final Font mainFont;
    private final double PADDING = 50d;

    public OrderContainerVbox(String dateTime, String refNo, double totalPrice, ObservableList<OrderItem> orderItems){
        this.dateReferenceGridPane = new GridPane();
        this.orderItemContainerVbox = new VBox(SPACING);
        this.dateTimeLabel = new Label("Date: " + dateTime);
        this.refNoLabel = new Label("Ref No: " + refNo);
        this.totalPriceLabel = new Label(String.format("TOTAL: %.2f", totalPrice) );
        this.totalContainerAnchorPane = new AnchorPane(totalPriceLabel);
        this.mainFont = new Font("Noto Sans Bold", 24d);
        setup();
        setupOrderItem(orderItems);
    }

    /**
     * Setup the layout of all the objects
     */
    private void setup(){
        this.setBackground(Background.fill(Color.WHITE));
        this.setPadding(new Insets(PADDING));
        VBox.setMargin(orderItemContainerVbox, new Insets(SPACING, 0d, SPACING, 0d));

        //Grid setup
        dateReferenceGridPane.add(dateTimeLabel, 0, 0);
        dateReferenceGridPane.add(refNoLabel, 1, 0);

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setHgrow(Priority.SOMETIMES);
        column2.setHgrow(Priority.SOMETIMES);
        column1.setMinWidth(10d);
        column2.setMinWidth(10d);
        column1.setPrefWidth(100d);
        column2.setPrefWidth(100d);
        column2.setHalignment(HPos.RIGHT);

        RowConstraints row = new RowConstraints(50d, 100d, USE_COMPUTED_SIZE);

        dateReferenceGridPane.getColumnConstraints().add(column1);
        dateReferenceGridPane.getColumnConstraints().add(column2);
        dateReferenceGridPane.getRowConstraints().add(row);

//        dateReferenceGridPane.setPadding(new Insets(PADDING, PADDING, 0d, PADDING));

        //Components setup
        dateTimeLabel.setFont(mainFont);
        refNoLabel.setFont(mainFont);
        totalPriceLabel.setFont(mainFont);

        //VBox preparations
        orderItemContainerVbox.setPrefSize(100d, 200d);
        this.setVgrow(orderItemContainerVbox, Priority.ALWAYS);
//        orderItemContainerVbox.setPadding(new Insets(0d, SPACING, 0d, SPACING));

        //Anchor preparation
        totalContainerAnchorPane.setPrefSize(600d, 50d);
        totalPriceLabel.setAlignment(Pos.CENTER_RIGHT);
        totalPriceLabel.setLayoutX(331d);
        totalPriceLabel.setLayoutY(3d);
        totalPriceLabel.setPrefSize(248d, 38d);
        AnchorPane.setRightAnchor(totalPriceLabel, 0d);


        //Adding Components
        this.getChildren().add(dateReferenceGridPane);
        this.getChildren().add(orderItemContainerVbox);
        this.getChildren().add(totalContainerAnchorPane);


    }

    //TODO setup the adding of orderitems
    private void setupOrderItem(ObservableList<OrderItem> orderItems){
        for (OrderItem orderItem: orderItems){
            double totalPrice = orderItem.getQuantity() * orderItem.getPrice();

            OrderItemContainerGridPane orderItemContainer = new OrderItemContainerGridPane(
                    orderItem.getQuantity(),
                    orderItem.getProductName(),
                    orderItem.getImagePath(),
                    orderItem.getPrice(),
                    totalPrice
            );

            orderItemContainerVbox.getChildren().add(orderItemContainer);
        }

    }
}
