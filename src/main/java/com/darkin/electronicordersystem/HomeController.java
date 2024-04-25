package com.darkin.electronicordersystem;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private String username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        TODO: Add all the needed first run here
    }

    public void setUsername(String username){
        this.username = username;
    }
}
