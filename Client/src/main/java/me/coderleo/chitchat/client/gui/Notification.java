package me.coderleo.chitchat.client.gui;

import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import me.coderleo.chitchat.client.models.Message;

import java.awt.*;

public class Notification extends FlowPane
{
    public static void showNotification(Message message)
    {
        final Notification notification = new Notification(message);

        final Stage stage = new Stage();
        Scene scene = new Scene(notification);
        stage.setScene(scene);

        stage.setX(Screen.getPrimary().getVisualBounds().getWidth());
        stage.setY(Screen.getPrimary().getVisualBounds().getHeight());

        stage.show();
        Toolkit.getDefaultToolkit().beep();

//        new Timer().schedule(new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                Platform.runLater(stage::close);
//            }
//        }, 0, 10000);
    }

    private Notification(Message message)
    {
        Text label = new Text("(test) test: hello world");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 10));
        getChildren().add(label);

        setOpacity(.5);
        setPrefSize(label.getWrappingWidth() + 50, 100);
    }
}