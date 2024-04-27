package com.darkin.electronicordersystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import model.Product;

import java.io.File;

public class ProductController{
    private Product product;
    private MyListener myListener;

    @FXML
    private ImageView productImageView;
    @FXML
    private Label productnameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private VBox vBox;
    @FXML
    private void pressed(MouseEvent mouseEvent){
        myListener.onClickListener(product);
    }

    public void setData(Product product, MyListener listener){
        this.product = product;
        this.myListener = listener;
        productnameLabel.setText(product.getName());
//        Image image = new Image(getClass().getResourceAsStream(product.getImage_path()));
        File fileImage = new File(product.getImage_path());
        Image image = new Image(fileImage.toURI().toString());
        priceLabel.setText(Main.CURRENCY + product.getPrice());
        productImageView.setImage(image);
//        productImageView.fitWidthProperty().bind(vBox.widthProperty());

    }


}
