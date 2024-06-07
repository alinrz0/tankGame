package ir.ac.kntu.game;

import ir.ac.kntu.game.Tanks.*;
import ir.ac.kntu.game.wall.BrickWall;
import ir.ac.kntu.game.wall.Flag;
import ir.ac.kntu.game.wall.MetalWall;
import ir.ac.kntu.game.wall.Wall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private static final int MAP_WIDTH = 15;

    private static final int MAP_HEIGHT = 15;

    private char[][] map;

    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public GameMap(String mapFilePath) {
        map = new char[MAP_WIDTH][MAP_HEIGHT];
        try (BufferedReader reader = new BufferedReader(new FileReader(mapFilePath))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                if (row >= MAP_HEIGHT) {
                    break;
                }
                if (line.length() < MAP_WIDTH) {
                    line = String.format("%-" + MAP_WIDTH + "s", line);
                }
                for (int col = 0; col < MAP_WIDTH; col++) {
                    map[row][col] = line.charAt(col);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerTank getTank() {
        PlayerTank tank = new PlayerTank(0,0);
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                if (map[row][col]=='P'){
                    tank.setX(col*50-Tank.getTankWidth()/2);
                    tank.setY(row*50-Tank.getTankHeight()/2);
                    tank.setHealth(Game.getLife());
                }

            }
        }
        return tank;
    }

    public Flag getFlag() {
        Flag flag=new Flag(0,0);
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                if (map[row][col]=='F'){
                    flag.setX(col*50-Wall.getWallWidth()/2);
                    flag.setY(row*50-Wall.getWallHeight()/2);
                }

            }
        }
        return flag;
    }

    public List<Wall> getWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                char c = map[row][col];
                switch (c) {
                    case 'B':
                        walls.add(new BrickWall(col*50,row*50));
                        break;
                    case 'M':
                        walls.add(new MetalWall(col*50,row*50));
                        break;
                    case  'F':
                        walls.add(new Flag(col*50,row*50));
                    default:
                }
            }
        }
        return walls;
    }
}
