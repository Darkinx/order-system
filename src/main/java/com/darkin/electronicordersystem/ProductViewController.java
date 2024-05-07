package com.darkin.electronicordersystem;

import com.darkin.electronicordersystem.models.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.darkin.electronicordersystem.models.Product;
import com.darkin.electronicordersystem.models.ProductDAO;

import java.io.File;
import java.util.ResourceBundle;
import java.net.URL;

public class ProductViewController implements Initializable {
    private ProductDAO productDAO = new ProductDAO();
    private Product product;
    private MyListener cartListener;

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
    public void setData(Product product, MyListener listener){
        this.product = product;
        this.cartListener = listener;
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
        product.setQuantity(quantitySpinner.getValue().intValue());
        cartListener.onClickListener(product);
    }
}
