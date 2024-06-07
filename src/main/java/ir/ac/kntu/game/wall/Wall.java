package ir.ac.kntu.game.wall;

import ir.ac.kntu.game.Bullet;
import ir.ac.kntu.game.Tanks.Tank;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Wall {
    private static int wallWidth = 50;

    private static int wallHeight = 50;

    private double x;

    private double y;

    private Image image;

    private WallType wallType;

    public Wall(int x, int y) {
        this.x = x-wallWidth/2;
        this.y = y-wallHeight/2;
    }

    public static int getWallWidth() {
        return wallWidth;
    }

    public static void setWallWidth(int wallWidth) {
        Wall.wallWidth = wallWidth;
    }

    public static int getWallHeight() {
        return wallHeight;
    }

    public static void setWallHeight(int wallHeight) {
        Wall.wallHeight = wallHeight;
    }

    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(x + wallWidth, y + wallHeight);
        gc.drawImage(image, -wallWidth / 2, -wallHeight / 2, wallWidth, wallHeight);
        gc.restore();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public WallType getWallType() {
        return wallType;
    }

    public void setWallType(WallType wallType) {
        this.wallType = wallType;
    }

//    public boolean intersectsTank(Tank tank) {
//        double tankX = tank.getX();
//        double tankY = tank.getY();
//        return x >= tankX + Tank.getTankWidth()&& x < tankX + Tank.getTankWidth()*2&&
//                y >= tankY +Tank.getTankHeight() && y < tankY + 2*Tank.getTankHeight();
//    }

    public boolean intersects(double x, double y, double width, double height) {
        Rectangle2D tankRect = new Rectangle2D(x, y, width, height);
        Rectangle2D wallRect = new Rectangle2D(getX(), getY(), wallWidth, wallHeight);
        return tankRect.intersects(wallRect);
    }
}