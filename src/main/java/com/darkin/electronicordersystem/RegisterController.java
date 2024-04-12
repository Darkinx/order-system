package com.darkin.electronicordersystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {
    @FXML
    private ImageView profileImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {// setting the branding image to the FXML layout
        File proFile = new File("assets/icons/line-md--account-white.png");
        Image profileImage =  new Image(proFile.toURI().toString());//do no what is this for.
        profileImageView.setImage(profileImage);

    }
}
