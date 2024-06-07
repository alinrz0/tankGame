package ir.ac.kntu.game.wall;

import javafx.scene.image.Image;

public class Flag extends Wall{
    public Flag(int x, int y){
        super(x, y);
        setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\images (3).png"));
        setWallType(WallType.Flag);
    }
}
