package com.darkin.electronicordersystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Product;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductController{
    private Product product;

    @FXML
    private ImageView productImageView;
    @FXML
    private Label productnameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private VBox vBox;

    public void setData(Product product){
        this.product = product;
        productnameLabel.setText(product.getName());
//        Image image = new Image(getClass().getResourceAsStream(product.getImage_path()));
        File fileImage = new File(product.getImage_path());
        Image image = new Image(fileImage.toURI().toString());
        productImageView.setImage(image);
//        productImageView.fitWidthProperty().bind(vBox.widthProperty());

    }
}
