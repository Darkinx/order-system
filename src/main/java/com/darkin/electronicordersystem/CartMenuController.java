package com.darkin.electronicordersystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import model.ProductDAO;

import java.util.Optional;

public class CartMenuController {
    private double total = 0; //full total of the cart
    private ProductDAO cartDAO = new ProductDAO();

    @FXML
    private Button backButton;

    @FXML
    private TableView<?> cartTable;

    @FXML
    private Button checkOutButton;




    public void checkOutOnAction(ActionEvent event){
        if(!(total == 0)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm and place the order? \nGrand Total = "+total, ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                //TODO implement the query here
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("CHECKOUT COMPLETE !!");
                Optional<ButtonType> result = alert.showAndWait();
//            if(!result.isPresent())
//                        // alert is exited, no button has been pressed.
//            else if(result.get() == ButtonType.OK)
//                        //oke button is pressed
//            else if(result.get() == ButtonType.CANCEL)
//                        // cancel button is pressed
            }
        }
    }

}
