package com.example.demofx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Button btn_login;

    @FXML
    private Button btn_signup;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("clicked");
                welcomeText.setText("Working");
//                DBUtils.connectDb();
                DBUtils.logInUser(actionEvent, tf_username.getText(), tf_password.getText());
            }
        });

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "signup.fxml", "Logged in", null, null);
                System.out.println("clicked!");
                welcomeText.setText("Workinggggggggggg");
            }
        });
    }
}