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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {
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
                registerUser();
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

    public void registerUser(){
        //instancing connection to DB
        DatabaseConnection con = new DatabaseConnection();
        Connection connectDB = con.getConnection();

        String insertQuery = getQuery();

        try{
            Statement st = connectDB.createStatement();
            st.executeUpdate(insertQuery);

            registrationMessageLabel.setText("Registered successfully");

        }catch (Exception e){
            e.getStackTrace();
            e.getCause();
        }
    }

    private String getQuery() {
        String uname = usernameTextField.getText();
        String pass = setPasswordField.getText();
        String fname = firstnameTextField.getText();
        String lname = lastnameTextField.getText();
        String address = addressTextField.getText();
        String email = emailTextField.getText();

        String insertField = "INSERT INTO account (username, password, fname, lname, address, email) VALUES ";
        String insertValues = String.format("('%s','%s','%s','%s','%s','%s')",uname,pass,fname,lname,address,email);
        String insertQuery = insertField + insertValues;
        return insertQuery;
    }


}
