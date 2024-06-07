package ir.ac.kntu.game.Tanks;

import javafx.scene.image.Image;
import java.util.Random;

public class LuckyTank extends Tank {
    private  Random random = new Random();

    private LuckyTankType luckyTankType;

    public LuckyTank(double x, double y) {
        super(x, y);
        setTankType(TankType.Lucky);
        luckyTankType = LuckyTankType.values()[random.nextInt(LuckyTankType.values().length)];
        if (luckyTankType.equals(LuckyTankType.Normal)){
            setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\normal lucky.png"));
            setScore(100);
            setHealth(1);
        }else if (luckyTankType.equals(LuckyTankType.Armored)){
            setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\armored lucky.png"));
            setScore(200);
            setHealth(2);
        }
    }

    public LuckyTankType getLuckyTankType() {
        return luckyTankType;
    }

    public void setLuckyTankType(LuckyTankType luckyTankType) {
        this.luckyTankType = luckyTankType;
    }
}