package com.darkin.electronicordersystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Product;
import model.ProductDAO;
import model.User;

import java.io.File;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.net.URL;

public class ProductViewController implements Initializable {
    private ProductDAO productDAO = new ProductDAO();
    private Product product;
    private User user;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button backButton;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView productImageView;

    @FXML
    private Label productnameLabel;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        TODO need to input everything
        //TODO setup Spinner

    }

    //TODO: Set max value of Spinner to stock
    public void setData(Product product){
        this.product = product;
        productnameLabel.setText(product.getName());
        descriptionLabel.setText(product.getDescription());
        priceLabel.setText(Main.CURRENCY + product.getPrice());

//        URL imageURL = getClass().getResource(product.getImage_path()); //this won't work
        File imageFile = new File(product.getImage_path());
        Image productImage = new Image(imageFile.toURI().toString());
        productImageView.setImage(productImage);
        productImageView.setPreserveRatio(true);
        productImageView.setFitWidth(500);

        //spinner setup
        //https://stackoverflow.com/questions/31248983/how-to-create-an-integer-spinner-with-fxml#31250792
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, product.getStock(), 1));
    }



    public void backButton(ActionEvent event){

    }

    public void addToCartAction(ActionEvent event){
        System.out.println("addToCart button pressed");
        try {
            productDAO.addCart(user.getId(), product.getId(), quantitySpinner.getValue());
            System.out.println("addToCart button pressed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
