package com.darkin.electronicordersystem.UIComponents;

import com.darkin.electronicordersystem.Main;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;

import static java.lang.Double.MAX_VALUE;

public class OrderItemContainerGridPane extends GridPane {
    private Label quantityLabel;
    private Label productNameLabel;
    private ImageView productImageView;
    private Label unitPriceLabel;
    private Label totalPriceLabel;
    private final double PADDING = 50d;

    /**
     * Create a grid pane for the whole item of the order
     *
     * @param quantity
     * @param productName
     * @param imagePath
     * @param unitPrice
     * @param totalPrice
     */
    public OrderItemContainerGridPane(int quantity, String productName, String imagePath, double unitPrice, double totalPrice){
        this.quantityLabel = new Label(Integer.toString(quantity));
        this.productNameLabel = new Label(productName);
        this.unitPriceLabel = new Label(String.format("%s %.2f", Main.CURRENCY, unitPrice));
        this.unitPriceLabel = new Label(String.format("%s %.2f", Main.CURRENCY, unitPrice));
        this.totalPriceLabel = new Label(String.format("%s %.2f", Main.CURRENCY, totalPrice));

        //Image part
        File imageFile = new File(imagePath);
        productImageView = new ImageView(new Image(imageFile.toURI().toString()));

        setup();

    }

    private void setup(){
        BorderStroke basicStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                new CornerRadii(5),
                new BorderWidths(1)
        );
        this.setBorder(new Border(
                basicStroke,
                basicStroke,
                basicStroke,
                basicStroke
        ));

        //Grid Setup
        ColumnConstraints column1 = new ColumnConstraints(50d, 50d, 94d);
        ColumnConstraints column2 = new ColumnConstraints(50d, 105d, 150d);
        ColumnConstraints column3 = new ColumnConstraints(50d, 180d, USE_COMPUTED_SIZE);
        ColumnConstraints column4 = new ColumnConstraints(50d, 100d, USE_PREF_SIZE);
        ColumnConstraints column5 = new ColumnConstraints(50d, 100d, USE_PREF_SIZE);

        RowConstraints row = new RowConstraints(110d);
        row.setVgrow(Priority.SOMETIMES);

        this.getColumnConstraints().addAll(column1, column2, column3, column4, column5);
        this.getRowConstraints().add(row);

        //Setting up components
        GridPane.setHalignment(quantityLabel, HPos.CENTER);
        productImageView.setPreserveRatio(true);
        productImageView.setFitHeight(100d);
        productNameLabel.setPrefSize(165d, 92d);
        productNameLabel.setWrapText(true);
        productNameLabel.setAlignment(Pos.CENTER_LEFT);
        productNameLabel.setMaxWidth(MAX_VALUE);
        productNameLabel.setPadding(new Insets(0d, PADDING, 0d, PADDING));
        GridPane.setHalignment(productNameLabel, HPos.CENTER);
        GridPane.setHalignment(unitPriceLabel, HPos.CENTER);
        GridPane.setHalignment(totalPriceLabel, HPos.CENTER);
        GridPane.setHgrow(productNameLabel, Priority.ALWAYS);
        unitPriceLabel.setAlignment(Pos.CENTER_LEFT);
        totalPriceLabel.setAlignment(Pos.CENTER_LEFT);




        //Adding all components to GridPane
        this.add(quantityLabel, 0,0);
        this.add(productImageView, 1,0);
        this.add(productNameLabel, 2,0);
        this.add(unitPriceLabel, 3,0);
        this.add(totalPriceLabel, 4,0);

    }

}
