package ir.ac.kntu.game.wall;

import javafx.scene.image.Image;

public class MetalWall extends Wall{
    public MetalWall(int x, int y){
        super(x, y);
        setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\images (1).jfif"));
        setWallType(WallType.Metal);
    }
}
