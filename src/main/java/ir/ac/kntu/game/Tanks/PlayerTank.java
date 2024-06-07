package ir.ac.kntu.game.Tanks;

import ir.ac.kntu.game.Game;
import ir.ac.kntu.game.power.Power;
import ir.ac.kntu.game.wall.Wall;
import javafx.scene.image.Image;

public class PlayerTank extends Tank{
    public PlayerTank(double x, double y) {
        super(x, y);
        setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\images.png"));
        setScore(0);
        setHealth(3);
        setTankType(TankType.Player);
    }

}
