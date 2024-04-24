package com.darkin.electronicordersystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private Button cancelButton, loginButton;
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
        File brandingFile = new File("assets/logo/main-logo.png");
        Image brandingImage =  new Image(brandingFile.toURI().toString());//do no what is this for.
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("assets/icons/codicon--account-white.png");
        Image lockImage =  new Image(lockFile.toURI().toString());//do no what is this for.
        lockImageView.setImage(lockImage);
    }
//TODO: Need validators
    public void loginButtonAction(ActionEvent event){
        if(usernameTextField.getText().isBlank() || enterPasswordField.getText().isBlank()){
            loginMessageLabel.setText("Enter your username or password");
        }else {
            if(validateLogin()) {
                createHomeForm();
                closeForm();
            }
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

    public void closeForm(){
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }
    //TODO: Separate all creation of forms and stages in one file
    public void createAccountForm(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
//            Parent root = (Parent) FXMLLoader.load(getClass().getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 650);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED); //this remove the window button (maximize, close, and minimize)
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createHomeForm(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
//            Parent root = (Parent) FXMLLoader.load(getClass().getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1315, 850);
            Stage stage = new Stage();
            stage.setTitle("E-Gizmo HOME");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            e.getCause();
        }
    }
//TODO: Separate any interface to the Database
    public boolean validateLogin(){
        //instancing connection to DB
        DatabaseConnection con = new DatabaseConnection();
        Connection connectDB = con.getConnection();

        String verifyLogin = "SELECT count(1) FROM account WHERE username='" + usernameTextField.getText() + "' AND password='" + enterPasswordField.getText() + "'";

        try{
            Statement st = connectDB.createStatement();
            ResultSet queryResult = st.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    System.out.println("Login tested");
                    loginMessageLabel.setText("Permission to enter"); //need a way to have a pseudo layout of the buying form
                    return true;
                }else{
                    loginMessageLabel.setText("Invalid Login. Please try again.");
                    return false;
                }
            }
        }catch (Exception e){
            e.getStackTrace();
            e.getCause();
        }
        return false;
    }

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
}