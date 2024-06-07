package ir.ac.kntu.game.Tanks;

import javafx.scene.image.Image;

public class NormalTank extends Tank {

    public NormalTank(double x, double y) {
        super(x, y);
        setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\normal.png"));
        setScore(100);
        setHealth(1);
        setTankType(TankType.Normal);
    }
}