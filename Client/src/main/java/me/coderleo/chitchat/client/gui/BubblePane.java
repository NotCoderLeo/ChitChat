package me.coderleo.chitchat.client.gui;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import me.coderleo.chitchat.common.models.AbstractConversation;

import static me.coderleo.chitchat.client.utils.JFXUtils.region;
import static me.coderleo.chitchat.client.utils.JFXUtils.runAndWait;

class BubblePane extends HBox
{
    private final AbstractConversation conversation;

    public BubblePane(AbstractConversation conversation)
    {
        this.conversation = conversation;
    }

    /**
     * Update the bubble pane.
     */
    public void update()
    {
        runAndWait(() -> {
            getChildren().clear();

            conversation.getMembers().forEach(u -> {
                HBox border = new HBox();
                Text label = new Text(u);

                border.getChildren().add(label);
                getChildren().add(border);

                border.setStyle("-fx-border-color: black; -fx-background-color: red;");
                getChildren().add(region(5, 0));
            });
        });
    }
}
