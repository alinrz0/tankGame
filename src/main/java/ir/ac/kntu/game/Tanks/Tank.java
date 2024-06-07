package ir.ac.kntu.game.Tanks;

import ir.ac.kntu.game.Bullet;
import ir.ac.kntu.game.Game;
import ir.ac.kntu.game.GameMap;
import ir.ac.kntu.game.power.Power;
import ir.ac.kntu.game.wall.Flag;
import ir.ac.kntu.game.wall.Wall;
import ir.ac.kntu.game.wall.WallType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.Random;

public class Tank {
    private double x;

    private double y;

    private double angle;

    private double speed;

    private double health;

    private Image image;

    private int score;

    private static double tankWidth = 50;

    private static double tankHeight = 50;

    private TankType tankType;

    private Flag flag;

    private int move=200;

    private int damage=1;

    private Random random=new Random();

    public Tank(double x, double y) {
        this.x = x;
        this.y = y;
        this.angle = 0;
        this.speed = 0;
        for (Wall wall :Game.getWalls()){
            if (wall.getWallType().equals(WallType.Flag)){
                flag=(Flag) wall;
            }
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static double getTankWidth() {
        return tankWidth;
    }

    public static double getTankHeight() {
        return tankHeight;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void addHealth(){
        health++;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public TankType getTankType() {
        return tankType;
    }

    public void setTankType(TankType tankType) {
        this.tankType = tankType;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void moveForward() {
        if (angle == 0 && !collidesWithWall(x, y - tankHeight, tankWidth, tankHeight) && !collidesWithOtherTanks(x, y - tankHeight)) {
            y -= tankHeight;
        } else {
            angle = 0;
        }
        outOfGrid();
    }

    public void moveBackward() {
        if (angle == 180 && !collidesWithWall(x, y + tankHeight, tankWidth, tankHeight) && !collidesWithOtherTanks(x, y + tankHeight)) {
            y += tankHeight;
        } else {
            angle = 180;
        }
        outOfGrid();
    }

    public void moveLeftward() {
        if (angle == 270 && !collidesWithWall(x - tankWidth, y, tankWidth, tankHeight) && !collidesWithOtherTanks(x - tankWidth, y)) {
            x -= tankWidth;
        } else {
            angle = 270;
        }
        outOfGrid();
    }

    public void moveRightward() {
        if (angle == 90 && !collidesWithWall(x + tankHeight, y, tankWidth, tankHeight) && !collidesWithOtherTanks(x + tankHeight, y)) {
            x += tankWidth;
        } else {
            angle = 90;
        }
        outOfGrid();
    }

    public Bullet fire() {
        double bulletX = 0;
        double bulletY = 0;
        double bulletVelocityY = -Math.cos(Math.toRadians(angle)) * 8;
        double bulletVelocityX = Math.sin(Math.toRadians(angle)) * 8;
        if (angle == 0) {
            bulletX = x + Math.cos(Math.toRadians(angle)) * tankWidth;
            bulletY = y + Math.sin(Math.toRadians(angle)) * tankHeight + tankHeight / 2;
        } else if (angle == 90) {
            bulletX = x + Math.cos(Math.toRadians(angle)) * tankWidth + tankWidth * 1.5;
            bulletY = y + Math.sin(Math.toRadians(angle)) * tankHeight;
        } else if (angle == 180) {
            bulletX = x + Math.cos(Math.toRadians(angle)) * tankWidth + tankWidth * 2;
            bulletY = y + Math.sin(Math.toRadians(angle)) * tankHeight + tankHeight * 1.5;
        } else if (angle == 270) {
            bulletX = x + Math.cos(Math.toRadians(angle)) * tankWidth + tankWidth / 2;
            bulletY = y + Math.sin(Math.toRadians(angle)) * tankHeight + tankHeight * 2;
        }
        return new Bullet(bulletX, bulletY, bulletVelocityX, bulletVelocityY, this,damage);
    }

    public void update() {
        if (move%100==0){
            randomMove();
        }
        if (move==50){
            Bullet bullet =fire();
            if (bullet != null) {
                Game.addBullets(bullet);
            }
        }
        move--;
        if (move==0){
            move=200;
        }

//        if (angle == 0 || angle == 180) {
//            y -= speed;
//        } else if (angle == 90 || angle == 270) {
//            x += speed;
//        }
//        speed *= 0.9;
        outOfGrid();
    }

    private void randomMove(){
        int move= random.nextInt(1,5);
        if (move==1){
            moveBackward();
            moveBackward();
        }else if (move==2){
            moveForward();
            moveForward();
        }else if (move==3){
            moveLeftward();
            moveLeftward();
        }else if (move==4){
            moveRightward();
            moveRightward();
        }
    }

    private void outOfGrid(){
        if (x < -tankWidth / 2) {
            x = -tankWidth / 2;
        } else if (x > Game.getCanvasWidth() - tankWidth * 1.5) {
            x = Game.getCanvasWidth() - tankWidth * 1.5;
        }
        if (y < -tankHeight / 2) {
            y = -tankHeight / 2;
        } else if (y > Game.getCanvasHeight() - tankHeight * 1.5) {
            y = Game.getCanvasHeight() - tankHeight * 1.5;
        }
    }

    public void takeDamage(double damage) {
        health -= damage;
    }

    public double getHealth() {
        return health;
    }

    public boolean intersects(Bullet bullet) {
        double bulletX = bullet.getX();
        double bulletY = bullet.getY();
        return bulletX >= x && bulletX <= x + tankWidth &&
                bulletY >= y && bulletY <= y + tankHeight;
    }

    public boolean collidesWithWall(double x, double y, double width, double height) {
        for (Wall wall : Game.getWalls()) {
            if (wall.intersects(x, y, width, height)) {
                return true;
            }
        }
        return false;
    }


    public boolean collidesWithPower(double x, double y, double width, double height) {
        for (Power power:Game.getPowers()) {
            if (power.intersects(x, y, width, height)) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesWithOtherTanks(double x, double y) {
        for (Tank tank : Game.getTanks()) {
            if (collides(x, y, tank.getX(), tank.getY())) {
                return true;
            }
        }
        return false;
    }

    private boolean collides(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < Tank.getTankWidth();
    }

    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(x + tankWidth, y + tankHeight);
        gc.rotate(angle);
        gc.drawImage(image, -tankWidth / 2, -tankHeight / 2, tankWidth, tankHeight);
        gc.restore();
    }


}