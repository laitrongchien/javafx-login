package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.ConnectionUtil;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML private TextField su_username;
    @FXML private PasswordField su_password;
    @FXML private TextField su_email;
    @FXML private TextField su_name;
    @FXML private TextField su_phone;
    @FXML private Button signup_btn;
    @FXML private Hyperlink login_acc;



    // DATABASE TOOL
    private Connection connect;
    private PreparedStatement statement;
    private ResultSet result;

    public SignupController() {
        connect = ConnectionUtil.connectDb();
    }

    public void signup(ActionEvent event) {
        //connect = connectDb();
        if(this.isValidated())
        try {
            String sql = "INSERT INTO users (username, password, email, name, phone) VALUES (?, ?, ?, ?, ?)";
            statement = connect.prepareStatement(sql);
            statement.setString(1, su_username.getText());
            statement.setString(2, su_password.getText());
            statement.setString(3, su_email.getText());
            statement.setString(4, su_name.getText());
            statement.setString(5, su_phone.getText());

            if(statement.executeUpdate() > 0) {
                this.clearForm();
                JOptionPane.showMessageDialog(null, "Sign up successfully", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Something went wrong", "Error message", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAlreadyRegistered() {
        boolean usernameExist = false;

        String query = "select * from users WHERE username = ?";
        try {
            statement = connect.prepareStatement(query);
            statement.setString(1, su_username.getText());
            result = statement.executeQuery();
            if (result.next()) {
                usernameExist = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return usernameExist;
    }

    private boolean isValidated() {

        if (su_name.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Name text field cannot be blank", "Error message", JOptionPane.ERROR_MESSAGE);
            su_name.requestFocus();
        } else if (su_email.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Email text field cannot be blank", "Error message", JOptionPane.ERROR_MESSAGE);
            su_email.requestFocus();
        } else if (su_phone.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Phone text field cannot be blank", "Error message", JOptionPane.ERROR_MESSAGE);
            su_phone.requestFocus();
        } else if (su_username.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Username text field cannot be blank", "Error message", JOptionPane.ERROR_MESSAGE);

        } else if (su_password.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Password field cannot be blank", "Error message", JOptionPane.ERROR_MESSAGE);

        } else if (su_password.getText().length() < 5 || su_password.getText().length() > 25) {
            JOptionPane.showMessageDialog(null, "Password field cannot be less than 5 and greator than 25 characters", "Error message", JOptionPane.ERROR_MESSAGE);

        } else if (isAlreadyRegistered()) {
            JOptionPane.showMessageDialog(null, "The username is already taken by someone else", "Error message", JOptionPane.ERROR_MESSAGE);
            su_username.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    private boolean clearForm() {
        su_name.clear();
        su_email.clear();
        su_phone.clear();
        su_username.clear();
        su_password.clear();
        return true;
    }

    public void exit() {
        System.exit(0);
    }

    public void switchLogin() throws IOException {
        signup_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}