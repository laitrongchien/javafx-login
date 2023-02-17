package controller;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.ConnectionUtil;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login_btn;
    @FXML
    private Hyperlink create_acc;

    // DATABASE TOOL
    private Connection connect;
    private PreparedStatement statement;
    private ResultSet result;

    public LoginController() {
        connect = ConnectionUtil.connectDb();
    }

    public void login(ActionEvent event) {
        if(this.isValidated())
        try {
            String user = username.getText();
            String pass = password.getText();
            String sql = "SELECT * FROM users WHERE username = ? and password = ?";
            statement = connect.prepareStatement(sql);
            //System.out.println(username.getText());
            statement.setString(1, user);
            statement.setString(2, pass);
            result = statement.executeQuery();

            if(result.next()) {
                // if username and password correct this area would run after the system check
                JOptionPane.showMessageDialog(null, "Login Successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
                login_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/views/page.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Admin Page");
                stage.show();

            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong username/password", "Error message", JOptionPane.ERROR_MESSAGE);
                username.requestFocus();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidated() {

        if (username.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Username text field cannot be blank", "Error message", JOptionPane.ERROR_MESSAGE);
            username.requestFocus();
        } else if (password.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Password text field cannot be blank", "Error message", JOptionPane.ERROR_MESSAGE);
            password.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    public void exit() {
        System.exit(0);
    }

    public void switchSignup() throws IOException {
            login_btn.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/views/signup.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("User Registration");
            stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
