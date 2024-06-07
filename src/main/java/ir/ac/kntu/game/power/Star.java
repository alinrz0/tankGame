package ir.ac.kntu.game.power;

import ir.ac.kntu.game.Game;
import ir.ac.kntu.game.Tanks.Tank;
import javafx.scene.image.Image;

public class Star extends Power{
    public Star(double x, double y){
        super(x, y);
        setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\star.png"));
        setType(PowerType.Star);
    }

    @Override
    public void applyPower(Tank tank) {
        Game.getTanks().get(0).setDamage(Game.getTanks().get(0).getDamage()+1);
    }
}
