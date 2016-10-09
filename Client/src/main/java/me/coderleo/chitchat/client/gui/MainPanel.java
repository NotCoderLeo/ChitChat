package me.coderleo.chitchat.client.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import me.coderleo.chitchat.client.Main;
import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.client.models.Conversation;

import java.util.Random;

public class MainPanel extends BorderPane
{
    private ChatPanel chatPanel;
    private Menu menu;
    private MenuBar menuBar;

    private Conversation currentConversation;

    private final Random random = new Random();
    private ListView<Conversation> conversationList;

    public MainPanel()
    {
        super();

        this.init();
        this.createMenu();
    }

    private void init()
    {
        setStyle("-fx-background-color: lightgray");

        setTop(createHeaderBox());
        setLeft(createConversationList());
    }

    private HBox createHeaderBox()
    {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.DARKGRAY.deriveColor(.5, .5, 1, 1));
        shadow.setOffsetY(1.5);

        HBox header = new HBox();
        header.setSpacing(10);
        header.setPadding(new Insets(12.5));
        header.setStyle("-fx-background-color: #e77748; -fx-border-color: transparent; -fx-border-width: -1; -fx-border-radius: 0;");

        Text title = new Text("ChitChat");
        title.setFont(Font.font("Lucida Grande", FontWeight.BOLD, FontPosture.REGULAR, 22));
        title.setFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.setEffect(shadow);
        header.getChildren().add(title);

        header.getChildren().forEach(n -> n.setVisible(true));

        return header;
    }

    private VBox createConversationList()
    {
        VBox box = new VBox();
        VBox.setVgrow(box, Priority.ALWAYS);

        conversationList = new ListView<>();

        conversationList.setItems(FXCollections.observableArrayList(ConversationManager.getInstance().getConversations()));

        conversationList.setCellFactory(new Callback<ListView<Conversation>, ListCell<Conversation>>()
        {
            @Override
            public ListCell<Conversation> call(ListView<Conversation> param)
            {
                return new ListCell<Conversation>()
                {
                    @Override
                    protected void updateItem(Conversation item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        if (item != null)
                        {
                            setText(item.getName());
                            setGraphic(new Circle(4, Color.GREEN));
                        } else
                            setText("");
                    }
                };
            }
        });

        conversationList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                setCenter(newValue.getChatPanel());
                Main.getInstance().setTitle("ChitChat - " + newValue.getName() + " [Members: " + newValue.getMembers().size() + "]");
            }
        });

        VBox.setVgrow(conversationList, Priority.ALWAYS);

        box.getChildren().addAll(conversationList);

        return box;
    }

    private void createMenu()
    {
        menuBar = new MenuBar();

        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac"))
            menuBar.useSystemMenuBarProperty().set(true);

        menu = new Menu("File");
        MenuItem test = new MenuItem("Test");
        test.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));

        menu.getItems().add(test);

        menuBar.getMenus().add(menu);

        getChildren().remove(menuBar);
        getChildren().add(menuBar);
    }

    public ChatPanel getChatPanel()
    {
        return chatPanel;
    }

    public void conversationAdded(final Conversation conversation)
    {
        Platform.runLater(() -> conversationList.getItems().add(conversation));
    }

    public void conversationRemoved(final Conversation conversation)
    {
        Platform.runLater(() -> conversationList.getItems().remove(conversation));
    }
}
