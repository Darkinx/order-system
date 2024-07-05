package com.darkin.electronicordersystem;

import com.darkin.electronicordersystem.models.MyListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import com.darkin.electronicordersystem.models.Product;
import com.darkin.electronicordersystem.models.ProductDAO;
import com.darkin.electronicordersystem.models.User;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private User user = new User();
    private ObservableList<Product> products;
    private ProductDAO productDAO = new ProductDAO();
    private MyListener myListener;
    private MyListener cartListener;
    private AnchorPane productViewAnchorPane;
    private VBox cartMenuVbox;
    private AnchorPane profileAnchorPane; //Still not decided
    private ScrollPane orderHistoryScrollPane;
    private ObservableList<AnchorPane> anchorList;
    private VBox accountUpdateVbox;

    //Controllers setup
    private ProductViewController productViewController;
    private CartMenuController cartMenuController;
    private OrderHistoryController orderHistoryController;
    private AccountUpdateController accountUpdateController;

    @FXML
    private ImageView logoImageView;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button cartButton;
    @FXML
    private Label userNameLabel;
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
    private TilePane productPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private ScrollPane productScrollPane;
    @FXML
    private HBox itemFilterHbox;
    @FXML
    private  GridPane headerGridPane;
    @FXML
    private  Button backButton;
    @FXML
    private  Label headerLabel;
    @FXML
    private MenuButton userIconButton;
    @FXML
    private MenuItem accountSettingMenuItem;
    @FXML
    private MenuItem orderHistoryMenuItem;
    @FXML
    private MenuItem logoutMenuItem;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        TODO: Add all the needed first run here

        products = getAllProducts();

        if(products.size() > 0){
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) {
                    selectProduct(product);
                }
            };

            cartListener = new MyListener() {
                @Override
                public void onClickListener(Product product) {
                    addToCart(product);
                }
            };
        }
        //Setup image
        File logoFile = new File("assets/logo/main-logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
        mainMenuButton.setGraphic(logoImageView);
        mainMenuButton.setBackground(Background.fill(Color.TRANSPARENT));

        File cartFile = new File("assets/icons/iconoir--cart-white.png");
        Image cartImage = new Image(cartFile.toURI().toString());
        ImageView cartImageView = new ImageView(cartImage);
        cartImageView.setPreserveRatio(true);
        cartImageView.setFitWidth(50);
        cartButton.setGraphic(cartImageView);

        //FIXME: Need to change for MenuButton
        File userIconFile = new File("assets/icons/codicon--account-white.png");
        Image userIconImage = new Image(userIconFile.toURI().toString());
        ImageView userIconImageView = new ImageView(userIconImage);
        userIconImageView.setPreserveRatio(true);
        userIconImageView.setFitWidth(50);
        userIconButton.setGraphic(userIconImageView);

        //Initialize different Panel from FXMLLOADER
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            FXMLLoader fxmlLoader2 = new FXMLLoader();
            FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("fxml/orderHistory.fxml"));
            FXMLLoader fxmlLoader4 = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/cartMenu.fxml"));
            cartMenuVbox = fxmlLoader.load();
            cartMenuController = fxmlLoader.getController();
            fxmlLoader2.setLocation(getClass().getResource("fxml/productView.fxml"));
            productViewAnchorPane = fxmlLoader2.load();
            productViewController = fxmlLoader2.getController();
            orderHistoryScrollPane = fxmlLoader3.load();
            orderHistoryController = fxmlLoader3.getController();
            fxmlLoader4.setLocation(getClass().getResource("fxml/account-update-view.fxml"));
            accountUpdateVbox = fxmlLoader4.load();
            accountUpdateController = fxmlLoader4.getController();

        }catch (IOException e){
            System.err.println("Error occurred: " + e);
            e.printStackTrace();
        }

        //Setting up StackPane
        stackPane.getChildren().clear();
        stackPane.getChildren().add(accountUpdateVbox);
        stackPane.getChildren().add(orderHistoryScrollPane);
        stackPane.getChildren().add(cartMenuVbox);
        stackPane.getChildren().add(productViewAnchorPane);
        stackPane.getChildren().add(productScrollPane);

        stackPane.alignmentProperty().set(Pos.CENTER);
        StackPane.setAlignment(productPane, Pos.CENTER);

        //Setup the ProductPane
        setupProductPane();

    }
    public void mainMenuAction(ActionEvent event){
        if(!itemFilterHbox.isVisible()) {
            itemFilterHbox.setVisible(true);
            headerGridPane.setVisible(false);
            productScrollPane.toFront();
        }
        //TODO: Still buggy setup, needed a new thread for fetching data and rendering it
        //TODO: Need hashCode checking to check if everything is matched, if not, then re-render
//        if (products.containsAll(getAllProducts())){
//            Alert alert = new Alert(Alert.AlertType.NONE, "welp, it is different" , ButtonType.OK);
//            alert.showAndWait();
//            setupProductPane(); //resolved first the problem of the lag due to query, use threads
//        }
        products = getAllProducts();
        setupProductPane();

    }
    public void cartButtonAction(ActionEvent event){
        if(!headerGridPane.isVisible()){
            headerGridPane.setVisible(true);
            itemFilterHbox.setVisible(false);
        }
        headerLabel.setText("Cart");
        cartMenuVbox.toFront();
        cartMenuController.setData(user.getId());
        System.out.println("cart button clicked");

    }
    //TODO: Need to add a dropdown menu
    public void orderHistoryOnAction(ActionEvent event){
        if(!headerGridPane.isVisible()){
            headerGridPane.setVisible(true);
            itemFilterHbox.setVisible(false);
        }
        headerLabel.setText("Order History");
        orderHistoryController.setData(user.getId());
        orderHistoryController.Initialize();
        orderHistoryScrollPane.toFront();
    }

    public void accountSettingOnAction (ActionEvent event){
        if(!headerGridPane.isVisible()){
            headerGridPane.setVisible(true);
            itemFilterHbox.setVisible(false);
        }
        headerLabel.setText("Account Update");
        accountUpdateController.setData(user);
        accountUpdateController.initialize();
        accountUpdateVbox.toFront();
    }

    public void logoutOnAction (ActionEvent event){
        Alert confirm = new Alert(Alert.AlertType.WARNING, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();
        if(result.get() == ButtonType.YES) {
            try {
                user = null;
                Stage currStage = (Stage) backButton.getScene().getWindow();
                currStage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/login.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 520, 400);
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e){
                e.printStackTrace();
                e.getCause();
            }
        }
    }


    //TODO: Added way to unfocus and disable the node
    public void backButtonAction(ActionEvent event){
        ObservableList<Node> childs = this.stackPane.getChildren();
        if (childs.size() > 1) {
            Node topNode = childs.get(childs.size()-1);
            topNode.toBack();
            if(childs.get(childs.size()-1) == productScrollPane){
                if(!itemFilterHbox.isVisible()){
                    itemFilterHbox.setVisible(true);
                    headerGridPane.setVisible(false);
                }else{
                    headerGridPane.setVisible(false);
                }
            }
        }
    }
    public void setUser(User user){
        this.user = user;
        userNameLabel.setText(user.getUsername()); //this is extra
    }
    private void selectProduct(Product product){
        productViewController.setData(product, cartListener);
        if(!headerGridPane.isVisible()) {
            headerGridPane.setVisible(true);
            itemFilterHbox.setVisible(false);
            headerLabel.setText(product.getCategory());
        }
        productViewAnchorPane.toFront();
    }
    private void setupProductPane(){
        productPane.getChildren().clear();
        productPane.setAlignment(Pos.CENTER);
        productPane.setTileAlignment(Pos.CENTER);
        try{
            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("fxml/product.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductController productController = fxmlLoader.getController();
                productController.setData(products.get(i), myListener);

                
                productPane.getChildren().add(anchorPane); //(child,column,row)
                //set gridPane width
                productPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                productPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                productPane.setMaxWidth(Region.USE_PREF_SIZE);

                //set gridPane height
                productPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                productPane.setPrefHeight(Region.USE_COMPUTED_SIZE);

            }

        }catch (IOException e){
            System.err.println("Error occurred: " + e);

        }
    }
    private void addToCart(Product product){
        System.out.println("addToCart button pressed");
        try {
            productDAO.addCart(user.getId(), product.getId(), product.getQuantity());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<Product> getAllProducts(){
        try {
            return productDAO.getAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO: Create the filter category system for the toggle button
    //This will be the general selector of the category system.
    private void filterCategory(){
        //DO THIS ASAP
    }
}