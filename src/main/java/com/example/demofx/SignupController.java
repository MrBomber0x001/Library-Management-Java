package com.example.demofx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private Button btn_signup_signup;
    @FXML
    private Button btn_signup_login;
    @FXML
    private TextField tf_signup_username;
    @FXML
    private TextField tf_signup_password;
    @FXML
    private TextField tf_signup_email;

    @FXML
    private RadioButton rd_female;
    @FXML
    private RadioButton rd_male;

    @FXML
    private Label l_signup_working;
    @Override
    public void initialize(URL location, ResourceBundle resources){

        ToggleGroup toggleGroup = new ToggleGroup();
        rd_female.setToggleGroup(toggleGroup);
        rd_male.setToggleGroup(toggleGroup);

        rd_male.setSelected(true);
        btn_signup_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String toggleSelectedGender = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                l_signup_working.setText(toggleSelectedGender);
                System.out.println("Selected: " + toggleSelectedGender);
                l_signup_working.setText("working!");
                String username = tf_signup_username.getText().trim();
                String email = tf_signup_email.getText().trim();
                String password = tf_signup_password.getText().trim();

                DBUtils.signupUser(event, username, email, password, toggleSelectedGender);
                if(!username.isEmpty() && password.isEmpty() && !email.isEmpty()){
                    System.out.println("got here: 1");
                    DBUtils.signupUser(event, username, email, password, toggleSelectedGender);
                }
            }
        });

        btn_signup_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "hello-view.fxml", "Log in!", null, null, null);
            }
        });
    }
}
