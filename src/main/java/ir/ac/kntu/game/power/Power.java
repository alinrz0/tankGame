package ir.ac.kntu.game.power;

import ir.ac.kntu.game.Game;
import ir.ac.kntu.game.Tanks.Tank;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Power {

    private static int powerWidth = 50;

    private static int powerHeight = 50;

    private double x;

    private double y;

    private PowerType type;

    private Image image;

    private int timer=500;

    public static int getPowerWidth() {
        return powerWidth;
    }

    public static void setPowerWidth(int powerWidth) {
        Power.powerWidth = powerWidth;
    }

    public static int getPowerHeight() {
        return powerHeight;
    }

    public static void setPowerHeight(int powerHeight) {
        Power.powerHeight = powerHeight;
    }

    public Power(double x, double y) {
        this.x=x;
        this.y=y;
    }

    public void update() {
        if (timer > 0) {
            timer--;
            if (timer == 0) {
                removePower();
            }
        }
    }

    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(x + powerWidth, y + powerHeight);
        gc.drawImage(image, -powerWidth / 2, -powerHeight / 2, powerWidth, powerHeight);
        gc.restore();
    }


    public void applyPower(Tank tank) {

    }

    public void removePower() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(timer);
                Platform.runLater(() -> Game.removePower(this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public PowerType getType() {
        return type;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setType(PowerType type) {
        this.type = type;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean intersects(double x, double y, double width, double height) {
        Rectangle2D tankRect = new Rectangle2D(x, y, width, height);
        Rectangle2D powerRect = new Rectangle2D(getX(), getY(), powerWidth, powerHeight);
        return tankRect.intersects(powerRect);
    }
}