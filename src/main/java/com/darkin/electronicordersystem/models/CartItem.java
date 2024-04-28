package com.darkin.electronicordersystem.models;

import javafx.beans.property.*;


public class CartItem {
    private IntegerProperty id;
    private StringProperty productName;
    private DoubleProperty price;
//    ImageView productImage;
    private StringProperty image_path;
    private Item quantity;
    private DoubleProperty totalPrice;
//    Button remove;
//    SpinnerValueFactory.IntegerSpinnerValueFactory quantitySpinner;

    public CartItem(){
        this.id = new SimpleIntegerProperty(); //this what I forgot.
        this.productName = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.image_path = new SimpleStringProperty();
//        this.quantity = new SimpleIntegerProperty();
        this.totalPrice = new SimpleDoubleProperty();

    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }


    public Item getQuantity() {
        return quantity;
    }

    public void setQuantity(Item quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getImage_path() {
        return image_path.get();
    }

    public StringProperty image_pathProperty() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path.set(image_path);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }




//    public void setProductImage(String imagePath) {
//        File fileImage = new File(imagePath);
//        Image productImage = new Image(fileImage.toURI().toString());
//        ImageView productImageView = new ImageView(productImage);
//        this.productImage = productImageView;
//    }

}
