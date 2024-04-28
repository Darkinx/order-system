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
import com.darkin.electronicordersystem.models.User;
import com.darkin.electronicordersystem.models.UserDAO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private UserDAO userConn = new UserDAO();
    private User user;

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


    public void loginButtonAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        loginMessageLabel.setText("");
        if(usernameTextField.getText().isBlank() || enterPasswordField.getText().isBlank()){
            loginMessageLabel.setText("Enter your username or password");
        }else {
            String username = usernameTextField.getText();
            String password = enterPasswordField.getText();
            if(userConn.validateLogin(username, password)) {
                System.out.println("Login tested");
                loginMessageLabel.setText("Permission to enter"); //need a way to have a pseudo layout of the buying form
                try{
                    user = userConn.getUserInfo(username, password);
                    loginMessageLabel.setText("Login successfully!");
                }catch (SQLException e){
                    System.err.println("Error occur while fetching user info: " + e);
                    loginMessageLabel.setText("Error occurred while logging in!");
                    throw e;
                }

                createHomeForm();
                closeForm();
            }else{
                loginMessageLabel.setText("Invalid Login. Please try again.");
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
    //TODO: Still don't know how to perform this.
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
            Scene scene = new Scene(fxmlLoader.load(), 1315, 750);
            Stage stage = new Stage();
            stage.setTitle("E-Gizmo HOME");
            stage.setScene(scene);

            HomeController hmControl = fxmlLoader.getController();
            hmControl.setUser(user);

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            e.getCause();
        }
    }

}