package ir.ac.kntu.game.wall;


import javafx.scene.image.Image;

public class BrickWall extends Wall {
    public BrickWall(int x, int y) {
        super(x, y);
        setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\2139375.png"));
        setWallType(WallType.Brick);
    }
}