package me.coderleo.chitchat.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.coderleo.chitchat.client.gui.LoginPanel;
import me.coderleo.chitchat.client.gui.MainPanel;
import me.coderleo.chitchat.common.util.LogUtil;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    private void createTrayIcon()
    {
        LogUtil.info("System tray support: %s",
                SystemTray.isSupported() ? "YES" : "NO");
        if (SystemTray.isSupported())
        {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("res/traylogo.png");

            final TrayIcon trayIcon;

            MouseListener mouseListener = new MouseListener()
            {

                public void mouseClicked(MouseEvent e)
                {
                    System.out.println("Tray Icon - Mouse clicked!");
                }

                public void mouseEntered(MouseEvent e)
                {
                    System.out.println("Tray Icon - Mouse entered!");
                }

                public void mouseExited(MouseEvent e)
                {
                    System.out.println("Tray Icon - Mouse exited!");
                }

                public void mousePressed(MouseEvent e)
                {
                    System.out.println("Tray Icon - Mouse pressed!");
                }

                public void mouseReleased(MouseEvent e)
                {
                    System.out.println("Tray Icon - Mouse released!");
                }
            };

            ActionListener exitListener = e ->
            {
                System.out.println("Exiting...");
                System.exit(0);
            };

            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);

            trayIcon = new TrayIcon(image, "Tray Demo", popup);

            ActionListener actionListener = e -> trayIcon.displayMessage("Action Event",
                    "An Action Event Has Been Performed!",
                    TrayIcon.MessageType.INFO);

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);
            trayIcon.addMouseListener(mouseListener);

            try
            {
                tray.add(trayIcon);
            } catch (AWTException e)
            {
                System.err.println("TrayIcon could not be added.");
            }
        }
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
            if (mainPanel == null)
            {
                mainPanel = new MainPanel();
            }

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
