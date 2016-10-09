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
import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.client.models.Conversation;
import me.coderleo.chitchat.common.CommonConstants;
import me.coderleo.chitchat.common.models.Message;
import me.coderleo.chitchat.common.packets.universal.PacketMessage;

import java.text.SimpleDateFormat;
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
        conversation.addUser(new User(new String[]{"johndoe", "John", "2"}));
        conversation.addUser(new User(new String[]{"jacksmith", "Jack", "2"}));

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
        add.setOnAction(e ->
        {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add User");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the name of the user you want to add.");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(name ->
            {
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
//        textArea.setWrapText(true);

        getChildren().add(textArea);
    }

    private void createMessageBox()
    {
        messageBox = new TextField();
        messageBox.setPromptText("Enter a message...");
        messageBox.setOnKeyPressed(this::handleKeyPress);
        messageBox.getStyleClass().add("message-box");

        messageBox.lengthProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.intValue() > oldValue.intValue())
            {
                if (messageBox.getText().length() >= CommonConstants.MAX_MESSAGE_LENGTH)
                {
                    messageBox.setText(messageBox.getText().substring(0, CommonConstants.MAX_MESSAGE_LENGTH));
                }
            }
        });

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
            ServerConnector.getInstance().sendPacket(
                    new PacketMessage(conversation, ConversationManager.getInstance().getLocalUser(), messageBox.getText().trim())
            );

            messageBox.clear();
        }
    }

    public void messageReceived(final Message message, boolean isSystem)
    {
        Platform.runLater(() ->
        {
            if (isSystem)
            {
                textArea.appendText("SYSTEM: " + message.getBody().trim() + "\n");
            } else
            {
                String time = new SimpleDateFormat("hh:mm a").format(message.getWhen().getTime());
                textArea.appendText("[" + time + "] " + message.getSender() + ": " + message.getBody().trim() + "\n");
            }
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
