package ir.ac.kntu.game.power;

import ir.ac.kntu.game.Game;
import ir.ac.kntu.game.Tanks.Tank;
import javafx.scene.image.Image;

public class ExtraLife extends Power{
    public ExtraLife(double x, double y){
        super(x, y);
        setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\extra life.png"));
        setType(PowerType.Star);
    }

    @Override
    public void applyPower(Tank tank) {
        Game.getTanks().get(0).addHealth();
    }
}
