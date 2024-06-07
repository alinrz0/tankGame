package ir.ac.kntu.game;

import ir.ac.kntu.game.Tanks.LuckyTank;
import ir.ac.kntu.game.Tanks.Tank;

import java.util.Random;

public class Methods {
    private Random random=new Random();

    public void calculateNumberTank(Tank tank) {
        switch (tank.getTankType()) {
            case Normal -> Game.setNormalTankDestroyed(Game.getNormalTankDestroyed()+1);
            case Armored -> Game.setArmoredTankDestroyed(Game.getArmoredTankDestroyed()+1);
            case Lucky -> {
                LuckyTank lucky = (LuckyTank) tank;
                switch (lucky.getLuckyTankType()) {
                    case Normal -> Game.setNormalLuckyTankDestroyed(Game.getNormalLuckyTankDestroyed()+1);
                    case Armored -> Game.setArmoredLuckyTankDestroyed(Game.getArmoredLuckyTankDestroyed()+1);
                    default -> {
                    }
                }
            }
            default -> {
            }
        }
    }

    public int getRandomLocationPower() {
        int[] possible = {0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600};
        int index = random.nextInt(possible.length);
        return possible[index];
    }
}
