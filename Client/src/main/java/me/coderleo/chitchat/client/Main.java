package me.coderleo.chitchat.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.coderleo.chitchat.client.gui.LoginPanel;
import me.coderleo.chitchat.client.gui.MainPanel;

import static me.coderleo.chitchat.client.utils.JFXUtils.runAndWait;

public class Main extends Application
{
    private static Main instance;
    private MainPanel mainPanel;

    public static Main getInstance()
    {
        return instance;
    }

    private Stage stage;
    private Scene scene;

    private LoginPanel loginPanel;
    private boolean login;

    @Override
    public void start(Stage stage) throws Exception
    {
        instance = this;

        this.stage = stage;

        stage.setTitle("ChitChat");
        showLoginPanel();

        stage.setWidth(640);
        stage.setMinWidth(640);
        stage.setHeight(480);
        stage.setMinHeight(480);

        stage.show();

        stage.setOnHiding(e ->
        {
            //
        });

        stage.setOnShowing(e ->
        {
            //
        });
    }

    public void showLoginPanel()
    {
        Platform.runLater(() ->
        {
            if (loginPanel == null)
                loginPanel = new LoginPanel();
            loginPanel.reset();
            replaceSceneContent(loginPanel);

            login = true;
            stage.setResizable(false);
            stage.setOnCloseRequest(e -> System.exit(0));
            stage.show();
        });
    }

    public void showMainPanel()
    {
        runAndWait(() ->
        {
            mainPanel = new MainPanel();
            replaceSceneContent(mainPanel);

            login = false;
            stage.setResizable(true);
            stage.setOnCloseRequest(e -> System.exit(0));

            stage.show();
        });
    }

    private void replaceSceneContent(final Parent parent)
    {
        runAndWait(() ->
        {
            if (scene == null)
            {
                scene = new Scene(parent);
            } else
            {
                scene.setRoot(parent);
            }

            scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());
            stage.setScene(scene);
        });
    }

    public LoginPanel getLoginPanel()
    {
        return loginPanel;
    }

    public MainPanel getMainPanel()
    {
        return mainPanel;
    }

    public Stage getStage()
    {
        return stage;
    }

    public boolean isVisible()
    {
        return stage.isShowing();
    }

    public void setTitle(final String title)
    {
        runAndWait(() -> stage.setTitle(title));
    }
}
