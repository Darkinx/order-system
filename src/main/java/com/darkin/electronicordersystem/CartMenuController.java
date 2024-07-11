package com.darkin.electronicordersystem;

import com.darkin.electronicordersystem.UIComponents.ActionButtonTableCell;
import com.darkin.electronicordersystem.models.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.sql.SQLException;
import java.util.Optional;

public class CartMenuController {
    private double total = 0; //full total of the cart
    private ProductDAO cartDAO = new ProductDAO();
    private int userId;
    private static ObservableList<CartItem> cartItems;

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
    private TableColumn<CartItem, Item> quantityCol;
    @FXML
    private TableColumn<CartItem, String> imageCol;
    @FXML
    private TableColumn<CartItem, Double> totalUnitCol;
    @FXML
    private TableColumn<CartItem, Void> removeCol;


    public void setData(int userId){
        total = 0;
        totalCostLabel.setText(Main.CURRENCY + Double.toString(total));
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
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm and place the order? \nGrand Total = "+total, ButtonType.YES, ButtonType.NO);
//            alert.setX(Main.ORIGW/2 - alert.getWidth());
//            alert.setY(Main.ORIGH/2 - alert.getHeight());
//            alert.initStyle(StageStyle.UNIFIED);
//            alert.initModality(Modality.APPLICATION_MODAL);
//            System.out.printf("X: %f Y: %f", Main.ORIGW, Main.ORIGH);
//            alert.showAndWait();
//            alert.close();
            Optional<ButtonType> result = confirmationAlert("Confirm and place the ord          er? " +
                    "\nGrand Total = "+total);

            if (result.get() == ButtonType.YES) {
                Alert alertOK = new Alert((Alert.AlertType.NONE), "CHECKOUT COMPLETE !!", ButtonType.OK );
                alertOK.showAndWait();
                try {
                    String billNo = Integer.toString(cartItems.hashCode());
                    cartDAO.checkoutCart(userId, total, billNo);
                    total = 0;
                    totalCostLabel.setText(Main.CURRENCY + Double.toString(total));
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
        totalUnitCol.setCellValueFactory(cellData -> {
            cellData.getValue().totalPriceProperty().bind(
                    cellData.getValue().getQuantity().itemCountProperty().
                            multiply(cellData.getValue().getPrice()));
            return cellData.getValue().totalPriceProperty().asObject();
        });
        imageCol.setCellValueFactory(cellData -> cellData.getValue().image_pathProperty());
        quantityCol.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getQuantity()));

        imageCol.setCellFactory(col -> {
            TableCell<CartItem, String> cell = new TableCell<>();

            cell.itemProperty().addListener((observable, old, newVal) -> {
                if (newVal != null){
                    Node graphic = createProductGraphic(newVal);
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(graphic));

                }
                    });
            return cell;
        });

        quantityCol.setCellFactory(new Callback<TableColumn<CartItem, Item>, TableCell<CartItem, Item>>() {
            @Override
            public TableCell<CartItem, Item> call(TableColumn<CartItem, Item> param) {
                TableCell<CartItem, Item> cell = new TableCell<CartItem, Item>() {

                    private final Spinner<Integer> count;

                    private final SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory;
                    private final ChangeListener<Number> valueChangeListener;

                    {//TODO: Move to new Component setup
                        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
                        valueFactory.setMin(1);
                        count = new Spinner<>(valueFactory);
                        count.setVisible(false);
                        setGraphic(count);
                        valueChangeListener = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                            if(newValue.intValue() <= 1){
                                valueFactory.setValue(1);
                            }else{
                                valueFactory.setValue(newValue.intValue());
                            }
                                System.out.println("New value at Listener: " + newValue);
                                System.out.println("ItemCountValue at Listener: " + getItem().getItemCount());
                        };
//                        count.valueProperty().addListener(valueChangeListener);
                        count.valueProperty().addListener((obs, oldValue, newValue) -> {
                            if (getItem() != null) {
                                // write new value to table item
                                if(newValue.intValue() <= 1){
                                    getItem().setItemCount(1);
                                    count.getValueFactory().setValue(1); //This is the solution
                                }else{
                                    getItem().setItemCount(newValue.intValue());
                                    count.getValueFactory().setValue(newValue);
                                }
                                int quantityNow = getItem().getItemCount();
                                CartItem item = getTableView().getItems().get(getIndex());
                                try {
                                    cartDAO.updateQuantity(quantityNow, item.getId());
                                    CalculateTotal();
                                    //TODO Make the quantity spinner view not make to zero
                                    totalCostLabel.setText(Main.CURRENCY + Double.toString(total));
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        });
                    }
                    @Override
                    public void updateItem(Item item, boolean empty) {
                        // unbind old values
                        valueFactory.maxProperty().unbind();
                        if (getItem() != null) {
                            getItem().itemCountProperty().removeListener(valueChangeListener);
                        }
                        super.updateItem(item, empty);

                        // update according to new item
                        if (empty || item == null) {
                            count.setVisible(false);
                        } else {
                            item.itemCountProperty().addListener(valueChangeListener);
                            valueFactory.maxProperty().bind(item.itemMaxCountProperty());
                            valueFactory.setValue(item.getItemCount());
                            count.setVisible(true);
                        }
                        System.out.println("SpinnervalueAtUpdate: " + valueFactory.getValue());
                    }
                };
                return cell;
            }
        });

        File trashFile = new File("assets/icons/bi--trash.png");
        Image trashImage = new Image(trashFile.toURI().toString());
        removeCol.setCellFactory(ActionButtonTableCell.<CartItem, Void>forTableColumn(trashImage, c -> {
            Optional<ButtonType> result = confirmationAlert("Are you sure you want to delete this item?");
            if(result.get() == ButtonType.YES){
                try {
                    cartTable.getItems().remove(c);
                    cartDAO.removeItemFromCart(c.getId());
                    total -=c.getTotalPrice();
                    totalCostLabel.setText(Main.CURRENCY + Double.toString(total));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }));

    }

    private void getCartItems() throws SQLException, ClassNotFoundException{
        cartItems = FXCollections.observableArrayList();
        ObservableList<Product> products = cartDAO.getAllCart(userId);

        products.forEach(product -> {
//            System.out.println("Product id:" + product.getId());
            CartItem item = new CartItem();
            item.setId(product.getId());
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setImage_path(product.getImage_path());
            item.setQuantity(new Item(product.getQuantity(), product.getStock()));
            item.setTotalPrice(item.getPrice()*item.getQuantity().getItemCount());

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
    private void CalculateTotal(){
        total = 0d;
        for (CartItem item : cartTable.getItems()) {
            total += (totalUnitCol.getCellObservableValue(item).getValue());
        }
    }

    private Optional<ButtonType> confirmationAlert(String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
        alert.setX(Main.ORIGW/2 - alert.getWidth());
        alert.setY(Main.ORIGH/2 - alert.getHeight());
        alert.initStyle(StageStyle.UNIFIED);
        alert.initModality(Modality.APPLICATION_MODAL);
        System.out.printf("X: %f Y: %f", Main.ORIGW, Main.ORIGH);
        return alert.showAndWait();
    }

}
