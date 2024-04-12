package com.darkin.electronicordersystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView; //the user profile icon one
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button signupButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {// setting the branding image to the FXML layout
        File brandingFile = new File("assets/logo/e-gizmo_white_h100-397x100.png");
        Image brandingImage =  new Image(brandingFile.toURI().toString());//do no what is this for.
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("assets/icons/codicon--account-white.png");
        Image lockImage =  new Image(lockFile.toURI().toString());//do no what is this for.
        lockImageView.setImage(lockImage);
    }

    public void loginButtonAction(ActionEvent even){
        if(usernameTextField.getText().isBlank() || enterPasswordField.getText().isBlank()){
            loginMessageLabel.setText("Enter your username or password");
        }else {
            loginMessageLabel.setText("You try to login.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event){//action handler for closing the window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void signupButtonOnAction(ActionEvent event){

        //close the login stage first
        Stage stage =  (Stage) signupButton.getScene().getWindow();
        stage.close();

        createAccountForm();
    }

    public void createAccountForm(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
//            Parent root = (Parent) FXMLLoader.load(getClass().getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 530);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED); //this remove the window button (maximize, close, and minimize)
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void validateLogin(){
        //instancing connection to DB
        DatabaseConnection con = new DatabaseConnection();
        Connection connectDB = con.getConnection();

        String verifyLogin = "SELECT count(1) FROM account WHERE username='" + usernameTextField.getText() + "' AND password='" + enterPasswordField.getText() + "'";

        try{
            Statement st = connectDB.createStatement();
            ResultSet queryResult = st.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    System.out.println("The test go here");
                    loginMessageLabel.setText("Permission to enter"); //need a way to have a pseudo layout of the buying form
                }else{
                    loginMessageLabel.setText("Invalid Login. Please try again.");
                }
            }
        }catch (Exception e){
            e.getStackTrace();
            e.getCause();
        }
    }

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
}