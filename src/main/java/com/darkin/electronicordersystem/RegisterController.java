package com.darkin.electronicordersystem;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.darkin.electronicordersystem.models.User;
import com.darkin.electronicordersystem.models.UserDAO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {
    private final UserDAO userConn = new UserDAO();

    @FXML
    private ImageView profileImageView;
    @FXML
    private Button closeButton, registerButton;
    @FXML
    private Label registrationMessageLabel, passwordMessageLabel;
    @FXML
    private TextField firstnameTextField, lastnameTextField, usernameTextField, emailTextField, addressTextField;
    @FXML
    private PasswordField setPasswordField, confirmPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {// setting the branding image to the FXML layout
        File proFile = new File("assets/icons/line-md--account-white.png");
        Image profileImage =  new Image(proFile.toURI().toString());//do no what is this for.
        profileImageView.setImage(profileImage);

    }
//TODO: Need validators and checkers for E-mail etc.
    public void registerButtonOnAction(ActionEvent event){

        if(firstnameTextField.getText().isBlank() || lastnameTextField.getText().isBlank() || usernameTextField.getText().isBlank() || emailTextField.getText().isBlank() || addressTextField.getText().isBlank() || setPasswordField.getText().isBlank()|| confirmPasswordField.getText().isBlank()){
            registrationMessageLabel.setText("Missing input fields");
        }else {
            registrationMessageLabel.setText("");

            if(setPasswordField.getText().equals(confirmPasswordField.getText())) {
                passwordMessageLabel.setText("Passwords matched!");
                try {
                    userConn.registerUser(getQuery());
                    registrationMessageLabel.setText("Registered successfully");

                } catch (SQLException e) {
                    registrationMessageLabel.setText("Something's wrong");
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                loginAccountForm();
                closeForm();
            }else{
                passwordMessageLabel.setText("Password does not match");
            }

        }

    }

    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    private void loginAccountForm(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 400);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED); //this remove the window button (maximize, close, and minimize)
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            e.getCause();
        }
    }

    private void closeForm(){
        Stage stage =  (Stage) registerButton.getScene().getWindow();
        stage.close();
    }

    private User getQuery() {
        User user = new User();

        user.setUsername(usernameTextField.getText());
        user.setPassword(setPasswordField.getText());
        user.setFname(firstnameTextField.getText());
        user.setLname(lastnameTextField.getText());
        user.setAddress(addressTextField.getText());
        user.setEmail(emailTextField.getText());

        return user;
    }
}
