package me.coderleo.chitchat.client.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.coderleo.chitchat.client.ServerConnector;
import me.coderleo.chitchat.client.utils.ProgramImage;
import me.coderleo.chitchat.common.packets.client.auth.PacketLoginRequest;
import me.coderleo.chitchat.common.packets.client.auth.PacketRegisterRequest;

import static me.coderleo.chitchat.client.utils.JFXUtils.region;
import static me.coderleo.chitchat.client.utils.JFXUtils.spacer;

public class LoginPanel extends HBox
{
    private TextField username;
    private PasswordField password;
    private TextField ip;

    public LoginPanel()
    {
        ImageView logo = new ImageView(ProgramImage.LOGO.getImage());
        username = new TextField();
        password = new PasswordField();
        ip = new TextField();

        final Text actiontarget = new Text();

        username.setPromptText("Username");
        password.setPromptText("Password");
        ip.setPromptText("Server IP");

        HBox buttonPanel = new HBox();
        buttonPanel.setAlignment(Pos.CENTER);

        Button login = new Button("Login");
        login.setDisable(true);
        login.getStyleClass().add("chitchat-button");
        login.setStyle("-fx-background-color: #088e91; -fx-text-fill: #fff;");
        login.setOnAction(e ->
        {
            ServerConnector.getInstance().setIp(ip.getText());

            if (ServerConnector.getInstance().checkConnect())
            {
                ServerConnector.getInstance().sendPacket(new PacketLoginRequest(username.getText(), password.getText()));
                actiontarget.setText("");
            } else
            {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Could not connect.");
            }
        });

        Button register = new Button("Register");
        register.setDisable(true);
        register.getStyleClass().add("chitchat-button");
        register.setStyle("-fx-background-color: #088e91; -fx-text-fill: #fff;");
        register.setOnAction(e ->
        {
            ServerConnector.getInstance().setIp(ip.getText());

            if (ServerConnector.getInstance().checkConnect())
            {
                actiontarget.setText("");
                ServerConnector.getInstance().sendPacket(new PacketRegisterRequest(username.getText(), password.getText()));
            } else
            {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Could not connect.");
            }
        });

        username.textProperty().addListener((observable, oldValue, newValue) ->
                updateButtonToggle(login, register));

        password.textProperty().addListener((observable, oldValue, newValue) ->
                updateButtonToggle(login, register));

        ip.textProperty().addListener((observable, oldValue, newValue) ->
                updateButtonToggle(login, register));

        buttonPanel.getChildren().addAll(login, region(5, 0), register);

        VBox panel = new VBox();
        panel.setMaxSize(400, 400);
        panel.setAlignment(Pos.CENTER);
        panel.getChildren().addAll(region(0, 50), actiontarget, region(0, 15), logo, region(0, 5), username, region(0, 5), password, region(0, 5), ip, region(0, 5), buttonPanel);

        getChildren().addAll(spacer(true), panel, spacer(true));
    }

    private void updateButtonToggle(Button login, Button register)
    {
        boolean disable = username.getText().trim().isEmpty()
                || password.getText().trim().isEmpty()
                || ip.getText().trim().isEmpty()
                || (ip.getText().split(":").length != 2);
        login.setDisable(disable);
        register.setDisable(disable);
    }

    public void reset()
    {
        username.clear();
        password.clear();
        ip.clear();
    }
}
