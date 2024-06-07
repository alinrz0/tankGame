package ir.ac.kntu.game;

import ir.ac.kntu.SelectUser;
import ir.ac.kntu.User;
import ir.ac.kntu.UserPage;
import ir.ac.kntu.game.wall.Wall;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Finish {
    public void openPage() throws IOException, ClassNotFoundException {
        checkHighScore();
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        setLabels(root);
        setImages(root);
        setImagesFlash(root);
        drawLine(root);
        Button button=new Button("Finish");
        button.setLayoutY(350);
        button.setLayoutX(320);
        buttonAction(button,stage);
        root.setStyle("-fx-background-color: black");
        root.getChildren().add(button);
        Scene scene = new Scene(root, 400,400);
        stage.setScene(scene);
        stage.show();
    }

    private void buttonAction(Button button,Stage stage){
        button.setOnAction(actionEvent -> {
            stage.close();
            UserPage userPage=new UserPage(SelectUser.getUser());
            userPage.openPage();
            Game.setScore(0);
            Game.setTanks(new ArrayList<>());
            Game.setArmoredTankDestroyed(0);
            Game.setNormalLuckyTankDestroyed(0);
            Game.setArmoredLuckyTankDestroyed(0);
            Game.setNormalTankDestroyed(0);
            Game.setBullets(new ArrayList<>());
            Game.setPowers(new ArrayList<>());
            Game.setWalls(new ArrayList<Wall>());
            Game.setLife(3);
        });
    }

    private void setLabels(AnchorPane root){
        Label hiScorelabel=new Label("HI-SCORE");
        setLabel(hiScorelabel,110,20);
        Label highScorelabel=new Label(String.valueOf(SelectUser.getUser().getHighScore()));
        setLabel(highScorelabel,230,20);
        Label levellabel=new Label(String.valueOf(Game.getLevel()));
        setLabel(levellabel,140,60);
        Label namelabel=new Label(SelectUser.getUser().getName());
        setLabel(namelabel,50,100);
        Label scorelabel=new Label(String.valueOf(Game.getScore()));
        scorelabel.setLayoutY(130);
        scorelabel.setLayoutX(50);
        setLabel(scorelabel,50,130);
        Label numberTankNormal=new Label(String.valueOf(Game.getNormalTankDestroyed()*100)+"   PTS   "+String.valueOf(Game.getNormalTankDestroyed()));
        setLabel(numberTankNormal,10,160);
        Label numberTankArmored=new Label(String.valueOf(Game.getArmoredTankDestroyed()*200)+"   PTS   "+String.valueOf(Game.getArmoredTankDestroyed()));
        setLabel(numberTankArmored,10,200);
        Label numberTankNormalLucky=new Label(String.valueOf(Game.getNormalLuckyTankDestroyed()*100)+"   PTS   "+String.valueOf(Game.getNormalLuckyTankDestroyed()));
        setLabel(numberTankNormalLucky,10,240);
        Label numberTankArmoredLucky=new Label(String.valueOf(Game.getArmoredLuckyTankDestroyed()*200)+"   PTS   "+String.valueOf(Game.getArmoredLuckyTankDestroyed()));
        setLabel(numberTankArmoredLucky,10,280);
        Label totalLabel=new Label("TOTAL   "+String.valueOf(Game.getArmoredLuckyTankDestroyed()+Game.getNormalTankDestroyed()+Game.getArmoredTankDestroyed()+Game.getNormalLuckyTankDestroyed()));
        setLabel(totalLabel,80,340);
        root.getChildren().addAll(hiScorelabel,highScorelabel,levellabel,namelabel,scorelabel,numberTankNormal,numberTankArmored,numberTankNormalLucky,numberTankArmoredLucky,totalLabel);
    }

    private void setLabel(Label label,int x,int y){
        label.setPrefSize(150,30);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        label.setTextFill(Color.WHITE);
        label.setLayoutY(y);
        label.setLayoutX(x);
    }

    private void checkHighScore() throws IOException, ClassNotFoundException {
        SelectUser selectUser=new SelectUser();
        ArrayList<User> users=selectUser.getUsers();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/main/java/ir/ac/kntu/Data/users"));
        for (User user:users){
            if (user.getName().equals(SelectUser.getUser().getName())&&Game.getScore()>SelectUser.getUser().getHighScore()){
                user.setHighScore(Game.getScore());
                SelectUser.getUser().setHighScore(Game.getScore());
            }
            if (user.getName().equals(SelectUser.getUser().getName())){
                user.setNumberOfGames(user.getNumberOfGames()+1);
                SelectUser.getUser().setNumberOfGames(user.getNumberOfGames());
            }
            objectOutputStream.writeObject(user);
        }
    }

    private void setImage(ImageView imageView, String address, int x, int y){
        imageView.setImage(new Image(address));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
    }

    private void setImagesFlash(AnchorPane root){
        for (int i=0;i<4;i++){
            ImageView imageView=new ImageView();
            imageView.setImage(new Image("C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\images (4).jpg"));
            imageView.setFitWidth(15);
            imageView.setFitHeight(15);
            imageView.setLayoutX(140);
            imageView.setLayoutY(i*40+167.5);
            root.getChildren().add(imageView);
        }
    }

    private void setImages(AnchorPane root){
        ImageView tankNormalImageView=new ImageView();
        setImage(tankNormalImageView,"C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\normal.png",170,160);
        ImageView tankAromerdImageView=new ImageView();
        setImage(tankAromerdImageView,"C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\armored.png",170,200);
        ImageView tankLucky1ImageView=new ImageView();
        setImage(tankLucky1ImageView,"C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\normal lucky.png",170,240);
        ImageView tankLucky2ImageView=new ImageView();
        setImage(tankLucky2ImageView,"C:\\Users\\alino\\OneDrive\\Desktop\\project4\\src\\main\\resources\\images\\armored lucky.png",170,280);
        root.getChildren().addAll(tankNormalImageView,tankAromerdImageView,tankLucky1ImageView,tankLucky2ImageView);
    }

    private void drawLine(AnchorPane root){
        Line line=new Line(-100,0,100,0);
        line.setStroke(Color.WHITE);
        line.setLayoutX(160);
        line.setLayoutY(330);
        line.setStrokeWidth(8);
        root.getChildren().add(line);
    }
}
