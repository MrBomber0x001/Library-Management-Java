package com.example.demofx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

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
                DBUtils.connectDb();
            }
        });

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "logged-in.fxml", "Logged in", null, null);
                System.out.println("clicked!");
                welcomeText.setText("Workinggggggggggg");
            }
        });
    }
}