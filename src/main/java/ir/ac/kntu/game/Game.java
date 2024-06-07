package ir.ac.kntu.game;

import ir.ac.kntu.Level;
import ir.ac.kntu.game.Tanks.*;
import ir.ac.kntu.game.power.*;
import ir.ac.kntu.game.wall.Wall;
import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game {
    private boolean lose = false;

    private boolean win = false;

    private Stage stage;

    private static int score = 0;

    private static List<Tank> tanks = new ArrayList<>();

    private static Tank playerTank;

    private static List<Bullet> bullets = new ArrayList<>();

    private static List<Wall> walls = new ArrayList<>();

    private static List<Power> powers = new ArrayList<>();

    private Canvas canvas;

    private GraphicsContext gc;

    private static Level level;

    private static int canvasWidth = 650;

    private static int canvasHeight = 650;

    private Random random = new Random();

    private GameMap gameMap;

    private Label labelLife;

    private Label labelTanks;

    private Label labelScore;

    private static long stopEnemyTankDuration = 5000;

    private static boolean stopEnemyTanks = false;

    private static long stopEnemyTanksStartTime = 0;

    private long startTime = 0;

    private static int normalTankDestroyed = 0;

    private static int armoredTankDestroyed = 0;

    private static int normalLuckyTankDestroyed = 0;

    private static int armoredLuckyTankDestroyed = 0;

    private static double life = 3;

    private boolean stop = false;

    public Game(Level level) throws InterruptedException {
        this.level = level;
        start();
    }

    public static int getCanvasWidth() {
        return canvasWidth;
    }

    public static int getCanvasHeight() {
        return canvasHeight;
    }

    public static void addScore(int tankScore) {
        score += tankScore;
    }

    public static List<Wall> getWalls() {
        return walls;
    }

    public static void setWalls(List<Wall> walls) {
        Game.walls = walls;
    }

    public static List<Tank> getTanks() {
        return tanks;
    }

    public static void setTanks(List<Tank> tanks) {
        Game.tanks = tanks;
    }

    public static void setBullets(List<Bullet> bullets) {
        Game.bullets = bullets;
    }

    public static void addBullets(Bullet bullet) {
        Game.bullets.add(bullet);
    }

    public static List<Power> getPowers() {
        return powers;
    }

    public static void setPowers(List<Power> powers) {
        Game.powers = powers;
    }

    public static void removePower(Power power) {
        Game.powers.remove(power);
    }

    public static void setStopEnemyTanks(boolean stopEnemyTanks) {
        Game.stopEnemyTanks = stopEnemyTanks;
    }

    public static void setStopEnemyTanksStartTime(long stopEnemyTanksStartTime) {
        Game.stopEnemyTanksStartTime = stopEnemyTanksStartTime;
    }

    public static Level getLevel() {
        return level;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Game.score = score;
    }

    public static int getNormalTankDestroyed() {
        return normalTankDestroyed;
    }

    public static void setNormalTankDestroyed(int normalTankDestroyed) {
        Game.normalTankDestroyed = normalTankDestroyed;
    }

    public static int getArmoredTankDestroyed() {
        return armoredTankDestroyed;
    }

    public static void setArmoredTankDestroyed(int armoredTankDestroyed) {
        Game.armoredTankDestroyed = armoredTankDestroyed;
    }

    public static int getNormalLuckyTankDestroyed() {
        return normalLuckyTankDestroyed;
    }

    public static void setNormalLuckyTankDestroyed(int normalLuckyTankDestroyed) {
        Game.normalLuckyTankDestroyed = normalLuckyTankDestroyed;
    }

    public static int getArmoredLuckyTankDestroyed() {
        return armoredLuckyTankDestroyed;
    }

    public static void setArmoredLuckyTankDestroyed(int armoredLuckyTankDestroyed) {
        Game.armoredLuckyTankDestroyed = armoredLuckyTankDestroyed;
    }

    public static double getLife() {
        return life;
    }

    public static void setLife(double life) {
        Game.life = life;
    }

    public static Tank getPlayerTank() {
        return playerTank;
    }

    public void start() {
        AnchorPane root = new AnchorPane();
        setStage(root);
        Scene scene = new Scene(root, canvasWidth + 150, canvasHeight);
        keyPress(scene);
        runner();
        stage.setScene(scene);
        stage.show();
    }

    private void setStage(AnchorPane root) {
        gameMap = new GameMap("src/main/java/ir/ac/kntu/Data/maps/" + String.valueOf(level));
        walls = gameMap.getWalls();
        playerTank = gameMap.getTank();
        stage = new Stage();
        labelTanks = createLabels("",670,20);
        labelLife = createLabels("",670,50);
        labelScore = createLabels("Score:   " + String.valueOf(score),670,80);
        RandomTank randomTank=new RandomTank();
        randomTank.createRandomTank(level);
        canvas = new Canvas(canvasWidth, canvasHeight);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().addAll(canvas, labelLife, labelTanks, labelScore);
        root.setStyle("-fx-background-color: #424640");
    }

    private Label createLabels(String label,int x,int y){
        Label label1=new Label(label);
        label1.setLayoutX(x);
        label1.setLayoutY(y);
        label1.setTextFill(Color.RED);
        javafx.scene.text.Font font = javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 20);
        label1.setFont(font);
        return label1;
    }

    private void runner() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    render();
                    if (!stop) {
                        update();
                        if (System.currentTimeMillis() - startTime > 5000 && lose) {
                            stage.close();
                            Finish finish = new Finish();
                            finish.openPage();
                            stop();
                        }
                        if (System.currentTimeMillis() - startTime > 5000 && win) {
                            stage.close();
                            life = tanks.get(0).getHealth();
                            Win win = new Win();
                            win.openPage();
                            stop();
                        }
                    }
                } catch (InterruptedException e) {
                } catch (IOException e) {
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

    private void keyPress(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    if (!stop) {
                        playerTank.moveForward();
                    }
                }
                case DOWN -> {
                    if (!stop) {
                        playerTank.moveBackward();
                    }
                }
                case LEFT -> {
                    if (!stop) {
                        playerTank.moveLeftward();
                    }
                }
                case RIGHT -> {
                    if (!stop) {
                        playerTank.moveRightward();
                    }
                }
                case SPACE -> pressSpace();
                case ENTER -> pressEnter();
                default -> {
                }
            }
        });
    }

    private void pressEnter() {
        if (stop) {
            stop = false;
        } else {
            stop = true;
        }
    }

    private void pressSpace() {
        if (!stop) {
            Bullet bullet = playerTank.fire();
            if (bullet != null) {
                bullets.add(bullet);
            }
        }
    }

    private void update() throws InterruptedException {
        updateLists();
        updateMap();
        updateWall();
        Iterator<Power> iterator = powers.iterator();
        while (iterator.hasNext()) {
            Power power = iterator.next();
            if (playerTank.collidesWithPower(playerTank.getX(), playerTank.getY(), Tank.getTankWidth(), Tank.getTankHeight())) {
                power.applyPower(playerTank);
                iterator.remove();
            }
        }
    }

    private void updateLists() {
        if (!stopEnemyTanks || System.currentTimeMillis() - stopEnemyTanksStartTime >= stopEnemyTankDuration) {
            Iterator<Tank> iteratorTank = tanks.iterator();
            while (iteratorTank.hasNext()) {
                Tank tank = iteratorTank.next();
                if (!tank.equals(playerTank)) {
                    tank.update();
                }
            }
        }
        Iterator<Bullet> iteratorBullet = bullets.iterator();
        while (iteratorBullet.hasNext()) {
            Bullet bullet = iteratorBullet.next();
            bullet.update();
            if (bullet.getOwner().equals(playerTank) && bullet.checkCollision(bullets) != null && !bullet.checkCollision(bullets).getOwner().equals(playerTank)) {
                bullets.remove(bullet);
                bullets.remove(bullet.checkCollision(bullets));
            }
        }
        Iterator<Power> iteratorPower = powers.iterator();
        while (iteratorPower.hasNext()) {
            Power power = iteratorPower.next();
            power.update();
        }
    }

    private void updateMap() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Tank> tankIterator = tanks.iterator();
            while (tankIterator.hasNext()) {
                Tank tank = tankIterator.next();
                bulletHintTank(bullet, tank);
                if (!bullet.getOwner().equals(playerTank) && tank.equals(playerTank) && bullet.intersectsTank(tank)) {
                    tank.takeDamage(bullet.getDamage());
                    bulletIterator.remove();
                    if (tank.getHealth() <= 0) {
                        lose = true;
                        startTime = System.currentTimeMillis();
                        tankIterator.remove();
                    } else {
                        tank.setX(gameMap.getTank().getX());
                        tank.setY(gameMap.getTank().getY());
                        tank.setDamage(1);
                    }
                }
            }
        }
    }

    private void bulletHintTank(Bullet bullet, Tank tank) {
        if (bullet.getOwner().equals(playerTank) && !tank.equals(playerTank) && bullet.intersectsTank(tank)) {
            tank.takeDamage(bullet.getDamage());
            bullets.remove(bullet);
            if (tank.getHealth() <= 0) {
                addScore(tank.getScore());
                labelScore.setText("Score:   " + String.valueOf(score));
                addPower(tank);
                Methods methods=new Methods();
                methods.calculateNumberTank(tank);
                tanks.remove(tank);
                if (tanks.size() == 1 && RandomTank.getLeftTank() == 0) {
                    win = true;
                    startTime = System.currentTimeMillis();
                }
            }
        }
    }

    private void updateWall() throws InterruptedException {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Wall> wallIterator = walls.iterator();
            while (wallIterator.hasNext()) {
                Wall wall = wallIterator.next();
                if (bullet.intersectsWall(wall)) {
                    switch (wall.getWallType()) {
                        case Brick:
                            wallIterator.remove();
                            bulletIterator.remove();
                            break;
                        case Metal:
                            bulletIterator.remove();
                            break;
                        case Flag:
                            wallIterator.remove();
                            bulletIterator.remove();
                            lose = true;
                            startTime = System.currentTimeMillis();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void render() throws InterruptedException {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        if (lose) {
            lose();
        }
        if (win) {
            win();
        }
        if (stop) {
            stop();
        }
        labelLife.setText("Life:  " + (int) playerTank.getHealth());
        labelTanks.setText("left tank:  " + RandomTank.getLeftTank());
        for (Tank tank : tanks) {
            tank.draw(gc);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(gc);
        }
        for (Wall wall : walls) {
            wall.draw(gc);
        }
        for (Power power : powers) {
            power.draw(gc);
        }
    }

    private void addPower(Tank tank) {
        Methods methods=new Methods();
        if (tank.getTankType().equals(TankType.Lucky)) {
            int x;
            int y;
            do {
                x = methods.getRandomLocationPower();
                y = methods.getRandomLocationPower();
            } while (gameMap.getMap()[y / 50][x / 50] == 'B' || gameMap.getMap()[y / 50][x / 50] == 'M' || gameMap.getMap()[y / 50][x / 50] == 'F');
            PowerType powerType = PowerType.values()[random.nextInt(PowerType.values().length)];
            switch (powerType) {
                case Star -> powers.add(new Star(x - Power.getPowerWidth() / 2, y - Power.getPowerHeight() / 2));
                case Clock -> powers.add(new Clock(x - Power.getPowerWidth() / 2, y - Power.getPowerHeight() / 2));
                case Extra_Life ->
                        powers.add(new ExtraLife(x - Power.getPowerWidth() / 2, y - Power.getPowerHeight() / 2));
                default -> {
                }
            }
        }
    }

    private void lose() {
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("You lose!", canvasWidth / 2, canvasHeight / 2);
    }

    private void win() {
        gc.setFill(Color.GREEN);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("You win!", canvasWidth / 2, canvasHeight / 2);
    }

    private void stop() {
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("paused!", canvasWidth / 2, canvasHeight / 2);
    }
}