package ir.ac.kntu.game;

import ir.ac.kntu.Level;
import ir.ac.kntu.game.Tanks.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RandomTank {
    private int count = 0;

    private Random random=new Random();

    private static int leftTank;

    private ArrayList<Tank> tanks= (ArrayList<Tank>) Game.getTanks();

    public static int getLeftTank() {
        return leftTank;
    }

    public static void setLeftTank(int leftTank) {
        RandomTank.leftTank = leftTank;
    }

    public void createRandomTank(Level level) {
        int[] numberOfTanksPerLevel = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int numberOfTanks = 6 + 4 * numberOfTanksPerLevel[level.ordinal()];
        tanks.add(Game.getPlayerTank());
        Game.setTanks(tanks);
        leftTank = numberOfTanks;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (count < numberOfTanks) {
                    addTank();
                } else {
                    timer.cancel();
                }
            }
        }, 0, 500);
    }

    private void addTank() {
        if (Game.getTanks().size() < 5) {
            TankType tankType = TankType.values()[random.nextInt(TankType.values().length)];
            double x = getRandomLocationNewTank() - Tank.getTankWidth() / 2;
            double y = -Tank.getTankHeight() / 2;
            if (!collidesWithOtherTanks(x, y)) {
                switch (tankType) {
                    case Player:
                        break;
                    case Normal:
                        tanks.add(new NormalTank(x, y));
                        count++;
                        leftTank--;
                        break;
                    case Armored:
                        tanks.add(new ArmoredTank(x, y));
                        count++;
                        leftTank--;
                        break;
                    case Lucky:
                        tanks.add(new LuckyTank(x, y));
                        count++;
                        leftTank--;
                        break;
                    default:
                }
                Game.setTanks(tanks);
            }
        }
    }

    public int getRandomLocationNewTank() {
        int[] possible = {0, 200, 400, 600};
        int index = random.nextInt(possible.length);
        return possible[index];
    }

    public boolean collidesWithOtherTanks(double x, double y) {
        for (Tank tank :Game.getTanks()) {
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
}
