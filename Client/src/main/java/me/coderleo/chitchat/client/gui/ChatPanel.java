package me.coderleo.chitchat.client.gui;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import me.coderleo.chitchat.client.ServerConnector;
import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.models.Conversation;
import me.coderleo.chitchat.common.models.Message;
import me.coderleo.chitchat.common.packets.client.conversation.PacketCSMessage;

import java.util.Calendar;
import java.util.Optional;

public class ChatPanel extends AnchorPane
{
    private TextField messageBox;
    private TextArea textArea;
    private BubblePane bubbleArea;

    private final Conversation conversation;

    /**
     * Construct the chat panel.
     *
     * @param conversation The conversation
     */
    public ChatPanel(final Conversation conversation)
    {
        this.conversation = conversation;
        conversation.addUser(new User(new String[] {"johndoe", "John", "2"}));
        conversation.addUser(new User(new String[] {"jacksmith", "Jack", "2"}));


        createMessageBox();
        createTextArea();
        createUserPanel();
    }

    @Override
    protected void layoutChildren()
    {
        super.layoutChildren();

        textArea.resize(getWidth(), textArea.getHeight() + 100);
        messageBox.resize(getWidth(), messageBox.getHeight());
    }

    private void createUserPanel()
    {
        BorderPane userPanel = new BorderPane();

        bubbleArea = new BubblePane(conversation);

        Button add = new Button("+");
        add.setStyle("-fx-border-radius: 0;");
        add.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add User");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the name of the user you want to add.");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(name -> {
                // do stuff
            });
        });

        userPanel.setLeft(bubbleArea);

        getChildren().add(userPanel);
        AnchorPane.setBottomAnchor(userPanel, 29.0);

        bubbleArea.update();
    }

    private void createTextArea()
    {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.getStyleClass().add("message-textarea");

        getChildren().add(textArea);
    }

    private void createMessageBox()
    {
        messageBox = new TextField();
        messageBox.setPromptText("Enter a message...");
        messageBox.setOnKeyPressed(this::handleKeyPress);
        messageBox.getStyleClass().add("message-box");

        if (!ServerConnector.getInstance().isConnected())
        {
            messageBox.setPromptText("Not connected to the ChitChat server");
            messageBox.setDisable(true);
        }

        getChildren().add(messageBox);
        AnchorPane.setBottomAnchor(messageBox, -.005d);
        AnchorPane.setLeftAnchor(messageBox, -.5d);
    }

    private void handleKeyPress(KeyEvent e)
    {
        if (e.getCode() == KeyCode.ENTER &&
                !messageBox.getText().trim().isEmpty())
        {
            ServerConnector.getInstance().sendPacket(new PacketCSMessage(messageBox.getText().trim(), conversation));
            messageBox.clear();
        }
    }

    public void messageReceived(final Message message)
    {
        Platform.runLater(() ->
        {
            String time = String.format("%s:%s %s",
                    message.getWhen().get(Calendar.HOUR_OF_DAY),
                    message.getWhen().get(Calendar.MINUTE),
                    message.getWhen().get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
            textArea.appendText("[" + time + "] " + message.getSender().getUsername() + ": " + message.getBody() + "\n");
        });
    }

    public void update()
    {

    }

    public Conversation getConversation()
    {
        return conversation;
    }

    public BubblePane getBubbleArea()
    {
        return bubbleArea;
    }
}
