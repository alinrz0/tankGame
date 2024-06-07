package ir.ac.kntu.game.Tanks;

import javafx.scene.image.Image;

public class ArmoredTank extends Tank{
    public ArmoredTank(double x, double y){
        super(x, y);
        setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\armored.png"));
        setScore(200);
        setHealth(2);
        setTankType(TankType.Armored);
    }
}
