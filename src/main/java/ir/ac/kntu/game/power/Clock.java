package ir.ac.kntu.game.power;

import ir.ac.kntu.game.Game;
import ir.ac.kntu.game.Tanks.Tank;
import javafx.scene.image.Image;

public class Clock extends Power{
    public Clock(double x, double y){
        super(x, y);
        setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\clock.png"));
        setType(PowerType.Star);
    }

    @Override
    public void applyPower(Tank tank) {
        Game.setStopEnemyTanks(true);
        Game.setStopEnemyTanksStartTime(System.currentTimeMillis());
    }
}
