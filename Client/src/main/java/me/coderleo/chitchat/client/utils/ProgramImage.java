package me.coderleo.chitchat.client.utils;

import javafx.scene.image.Image;

public enum ProgramImage
{
    LOGO,
    TRAYLOGO;

    private Image image;

    ProgramImage()
    {
        this.image = new Image(getClass().getResourceAsStream("/res/" + name().toLowerCase() + ".png"));
    }

    public Image getImage()
    {
        return image;
    }
}