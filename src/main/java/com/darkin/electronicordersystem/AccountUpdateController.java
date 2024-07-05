package  com.darkin.electronicordersystem;

import com.darkin.electronicordersystem.models.User;
import com.darkin.electronicordersystem.models.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class AccountUpdateController {
    private UserDAO userDAO = new UserDAO();
    private User currUser = new User();

    @FXML
    private VBox accountVbox;
    @FXML
    private TextField addressTextField;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private  PasswordField confirmPasswordField;
    @FXML
    private Button updateButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private VBox passwordVbox;
    @FXML
    private Label warningLabel;
    @FXML
    private Label warningPasswordLabel;

    @FXML
    void initialize(){
        usernameTextField.setText(currUser.getUsername());
        firstNameTextField.setText(currUser.getFname());
        lastNameTextField.setText(currUser.getLname());
        addressTextField.setText(currUser.getAddress());
        passwordField.setText(currUser.getPassword());
        emailTextField.setText(currUser.getEmail());

        usernameTextField.setDisable(true);
        firstNameTextField.setDisable(true);
        lastNameTextField.setDisable(true);
        addressTextField.setDisable(true);
        passwordVbox.setDisable(true);
        passwordField.setText("");
        emailTextField.setDisable(true);



        cancelButton.setVisible(false);
    }

    public void setData(User user){
        this.currUser = user;
        initialize();
    }

    //TODO: Need to go back to the main panel
    @FXML
    void cancelButtonAction(ActionEvent event) {

    }


    //TODO: Make this more responsive update
    @FXML
    void updateButtonAction(ActionEvent event) {
        //TODO need alert
        System.out.println("Account Updated");
        User tmpUserInfo = new User();
        tmpUserInfo.setUsername(usernameTextField.getText());
        tmpUserInfo.setFname(firstNameTextField.getText());
        tmpUserInfo.setLname(lastNameTextField.getText());
        tmpUserInfo.setPassword(passwordField.getText());
        tmpUserInfo.setAddress(addressTextField.getText());
        tmpUserInfo.setEmail(emailTextField.getText());

        try{
            userDAO.updateUserInfo(tmpUserInfo);
        }catch (SQLException e){
            System.err.println("Error on updating " + currUser.getUsername() + " info: " + e);
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            System.err.println("Error on updating " + currUser.getUsername() + " info: " + e);
            e.printStackTrace();
        }

    }
}
