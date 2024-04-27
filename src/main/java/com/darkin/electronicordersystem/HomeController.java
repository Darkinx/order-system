package com.darkin.electronicordersystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Product;
import model.ProductDAO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private String username;
    private ObservableList<Product> products;
    private ProductDAO productDAO = new ProductDAO();

    @FXML
    private ImageView logoImageView;
    @FXML
    private Button cartButton;
    @FXML
    private Button userIconButton;
    @FXML
    private Button toolButton;
    @FXML
    private Button microcontrollerButton;
    @FXML
    private Button passiveButton;
    @FXML
    private Button sensorsButton;
    @FXML
    private Button displayButton;
    @FXML
    private Button wiringButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridPane;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        TODO: Add all the needed first run here
        try {
            products = productDAO.getAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Setup image
        File logoFile = new File("assets/logo/main-logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File cartFile = new File("assets/icons/iconoir--cart-white.png");
        Image cartImage = new Image(cartFile.toURI().toString());
        ImageView cartImageView = new ImageView(cartImage);
        cartImageView.setPreserveRatio(true);
        cartImageView.setFitWidth(50);
        cartButton.setGraphic(cartImageView);

        File userIconFile = new File("assets/icons/codicon--account-white.png");
        Image userIconImage = new Image(userIconFile.toURI().toString());
        ImageView userIconImageView = new ImageView(userIconImage);
        userIconImageView.setPreserveRatio(true);
        userIconImageView.setFitWidth(40);
        userIconButton.setGraphic(userIconImageView);


        //Setup the gridPane
        int col = 0, row =1;
        try{
            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("product.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductController productController = fxmlLoader.getController();
                productController.setData(products.get(i));

                if (col == 4) {
                    col = 0;
                    row++;
                }

                gridPane.add(anchorPane, col++, row); //(child,column,row)
                //set gridPane width
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                //set gridPane height
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));

            }

        }catch (IOException e){
            System.err.println("Error occurred: " + e);

        }


    }

    public void setUsername(String username){
        this.username = username;
    }
}
