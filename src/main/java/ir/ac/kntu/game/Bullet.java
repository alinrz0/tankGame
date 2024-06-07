package ir.ac.kntu.game;

import ir.ac.kntu.game.Tanks.Tank;
import ir.ac.kntu.game.wall.Wall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.List;

public class Bullet {
    private double x;

    private double y;

    private double velocityX;

    private double velocityY;

    private double damage;

    private Tank owner;

    private static double radius = 5;

    public Bullet(double x, double y, double velocityX, double velocityY, Tank owner,double damage) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.damage = damage;
        this.owner = owner;
    }

    public void update() {
        x += velocityX;
        y += velocityY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDamage() {
        return damage;
    }

    public Tank getOwner() {
        return owner;
    }

    public static double getRadius() {
        return radius;
    }

    public boolean intersectsTank(Tank tank) {
        double tankX = tank.getX();
        double tankY = tank.getY();
        return x >= tankX + Tank.getTankWidth()&& x < tankX + Tank.getTankWidth()*2&&
                y >= tankY +Tank.getTankHeight() && y < tankY + 2*Tank.getTankHeight();
    }

    public boolean intersectsWall(Wall wall) {
        double wallX = wall.getX();
        double wallY = wall.getY();
        return x >= wallX + Wall.getWallWidth()&& x < wallX + Wall.getWallWidth()*2&&
                y >= wallY +Wall.getWallHeight()&& y < wallY + 2* Wall.getWallHeight();
    }

    public Bullet checkCollision(List<Bullet> bullets) {
//        for (Bullet otherBullet : bullets) {
//            if (otherBullet != this ) {
//                double dx = x - otherBullet.getX();
//                double dy = y - otherBullet.getY();
//                double distance = Math.sqrt(dx * dx + dy * dy);
//                if (distance < radius + otherBullet.getRadius()) {
//                    return otherBullet;
//                }
//            }
//        }
//        return null;
        Iterator<Bullet> iteratorBullet = bullets.iterator();
        while (iteratorBullet.hasNext()) {
            Bullet otherBullet = iteratorBullet.next();
            if (otherBullet != this ) {
                double dx = x - otherBullet.getX();
                double dy = y - otherBullet.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < radius + otherBullet.getRadius()) {
                    return otherBullet;
                }
            }
        }
        return null;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}