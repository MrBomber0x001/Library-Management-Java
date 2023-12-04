package com.example.demofx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    @FXML
    private Button btn_logout;
    @FXML
    private Label l_logout;
    @FXML
    private Label l_age;
    @FXML
    private Button btn_add_book;
    @FXML
    private Button btn_update_book;
    @FXML
    private Button btn_delete_book;
    @FXML
    private Label l_welcome;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "hello-view.fxml", "log in", null, null);
            }
        });


        btn_add_book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                l_welcome.setText("working!");
            }
        });

        btn_delete_book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                l_welcome.setText("Working!!!!!");
            }
        });

        btn_update_book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                l_welcome.setText("Working!!!!!!!!!!!!!!!");
            }
        });
    }


    public void setUserInformation(String username, int age){
        l_logout.setText("Welcome" + username + "!");
        l_age.setText("Your age is:" +  age);
    }

    public void addBook(){

    }

    public void deleteBook(){

    }

    public void updateBook(){

    }
}
