package com.darkin.electronicordersystem;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import com.darkin.electronicordersystem.models.CartItem;
import com.darkin.electronicordersystem.models.Product;
import com.darkin.electronicordersystem.models.ProductDAO;

import java.io.File;
import java.sql.SQLException;

public class CartMenuController {
    private double total = 0; //full total of the cart
    private ProductDAO cartDAO = new ProductDAO();
    private int userId;
    private static ObservableList<CartItem> cartItems;

    @FXML
    private Button backButton;
    @FXML
    private TableView<CartItem> cartTable;
    @FXML
    private Button checkOutButton;
    @FXML
    private Label totalCostLabel;
    @FXML
    private TableColumn<CartItem, String> itemNameCol;
    @FXML
    private TableColumn<CartItem, Double> priceCol;
    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;
    @FXML
    private TableColumn<CartItem, String> imageColumn;
    @FXML
    private TableColumn<CartItem, Double> totalUnitCol;
    @FXML
    private TableColumn<CartItem, Void> removeCol;


    public void setData(int userId){
        total = 0;
        this.userId = userId;
        initCols();
        try {
            getCartItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void checkOutOnAction(ActionEvent event){
        if(!(total == 0)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm and place the order? \nGrand Total = "+total, ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            alert.close();

            if (alert.getResult() == ButtonType.YES) {
                //TODO implement the query here

                Alert alertOK = new Alert((Alert.AlertType.NONE), "CHECKOUT COMPLETE !!", ButtonType.OK );
                alertOK.showAndWait();

                try {
                    getCartItems();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
//                alert.setAlertType(Alert.AlertType.NONE);
//                alert.setContentText("CHECKOUT COMPLETE !!");
//                alert.getButtonTypes().removeAll();
//                alert.setResult(ButtonType.OK);
//                alert.showAndWait();
//                alert.getButtonTypes().removeAll();
//                alert.setDialogPane();

//                Optional<ButtonType> result = alert.showAndWait();
//                ButtonType button = result.orElse(ButtonType.CANCEL);
//
//                if (button == ButtonType.OK) {
//                    System.out.println("Ok pressed");
//                } else {
//                    System.out.println("canceled");
//                }

            }
        }
    }


    private void initCols(){
        itemNameCol.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());//the asObject part is probably needed due to being an integer
        totalUnitCol.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().image_pathProperty());

        imageColumn.setCellFactory(col -> {
            TableCell<CartItem, String> cell = new TableCell<>();

            cell.itemProperty().addListener((observable, old, newVal) -> {
                if (newVal != null){
                    Node graphic = createProductGraphic(newVal);
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(graphic));

                }
                    });
            return cell;
        });

        quantityColumn.setCellFactory(col -> {
            TableCell<CartItem, Integer> cell = new TableCell<>();

            cell.itemProperty().addListener((observable, o, newVal) -> {
                if (newVal != null){
                    Node graphic = createQuantitySpinner(newVal);
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(graphic));

                }
            });
            return cell;
        });



//        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartItem, Integer>("quantitySpinner"));//what you put here is the name of the properties
//        imageColumn.setCellValueFactory(new PropertyValueFactory<CartItem, String>("productImage"));//put the variable here
//        removeCol.setCellValueFactory(new PropertyValueFactory<CartItem, Void>("remove"));
    }

    private void getCartItems() throws SQLException, ClassNotFoundException{
        cartItems = FXCollections.observableArrayList();
        ObservableList<Product> products = cartDAO.getAllCart(userId);

        products.forEach(product -> {
            System.out.println("Product id:" + product.getId());
            CartItem item = new CartItem();
            item.setId(product.getId());
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setImage_path(product.getImage_path());
            item.setQuantity(product.getQuantity());
            item.setTotalPrice(item.getPrice()*item.getQuantity());

            total += item.getTotalPrice();
            totalCostLabel.setText(Main.CURRENCY  + Double.toString(total));
            //TODO: Fetch the stock

//
//            item.setQuantitySpinner(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, item.getQuantity()));
////            item.setRemove(new Button("Del")); //Not needed since it is initialized on the constructor
//
//            //Button and Spinnerhandling
//            item.getQuantitySpinner().valueProperty().addListener((obs, oldValue, newVal) ->
//            {
//                System.out.println("New value: " + newVal);
//                try {
//                    cartDAO.updateQuantity(newVal, item.getId());
//                     item.setQuantity(newVal);
//                     total -= item.getTotalPrice();
//                     item.setTotalPrice(item.getPrice() * item.getQuantity());
//                     total += item.getTotalPrice();
//                     totalCostLabel.setText(Main.CURRENCY + Double.toString(total));
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//
//            item.getRemove().setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    //TODO have a way to remove the item on table
//                    cartTable.getItems().remove(item);
//                    //unsure implementation
//
//                    try {
//                        cartDAO.removeItemFromCart(item.getId());
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    } catch (ClassNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });

            cartItems.add(item);
        });
        cartTable.setItems(cartItems);
    }

    private Node createProductGraphic(String imagePath){
        HBox graphicCon = new HBox();
        graphicCon.setAlignment(Pos.CENTER);
        File fileImage = new File(imagePath);
        ImageView productImageView = new ImageView(fileImage.toURI().toString());
        productImageView.setFitWidth(200);
        productImageView.setPreserveRatio(true);
        graphicCon.getChildren().add(productImageView);
        return graphicCon;
    }

    private Node createQuantitySpinner(int quantity){
        Spinner<Integer> quantitySpinner = new Spinner<>(quantity, Integer.MAX_VALUE, 1);

        return quantitySpinner;
    }

}
