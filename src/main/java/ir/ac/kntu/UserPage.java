package ir.ac.kntu;

import ir.ac.kntu.game.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Arrays;

public class UserPage {
    private User user;

    private ListView<String> levelList;

    private static Level level;

    public UserPage(User user) {
        this.user = user;
    }


    public static void setLevel(Level level) {
        UserPage.level = level;
    }

    public static Level getLevel() {
        return level;
    }

    public void openPage() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("User Page");
        Label nameLabel = setLabel("Name: " + user.getName());
        Label emailLabel = setLabel("number of games: " + user.getNumberOfGames());
        Label phoneLabel = setLabel("high score: " + user.getHighScore());
        Label instructions = setLabel("Select a level:");
        ObservableList<String> levels = FXCollections.observableArrayList(
                getLevels());
        levelList = new ListView<>(levels);
        levelList.setPrefSize(200, 250);
        levelList.setStyle("-fx-control-inner-background: black; -fx-background-color: black;");
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: #424640; -fx-text-fill: white;");
        startButton(startButton, primaryStage);
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(10));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(startButton);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(nameLabel, emailLabel, phoneLabel, instructions, levelList, buttonBox);
        vBox.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(vBox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startButton(Button button, Stage stage) {
        button.setOnAction(e -> {
            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(200);
            button.setDisable(true);
            levelList.setDisable(true);
            HBox buttonBox = (HBox) button.getParent();
            buttonBox.getChildren().add(progressBar);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i < 100; i++) {
                        Thread.sleep(50);
                        updateProgress(i + 1, 100);
                    }
                    return null;
                }
            };
            progressBar(stage, progressBar, task);
        });
    }

    private void progressBar(Stage stage, ProgressBar progressBar, Task<Void> task) {
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        Task<Void> delayTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(5000);
                return null;
            }
        };
        delayTask.setOnSucceeded(event -> {
            try {
                selectLevel(stage);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            stage.close();
        });

        new Thread(delayTask).start();
    }

    private Label setLabel(String name) {
        Label label = new Label(name);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-size: 16pt;");
        return label;
    }

    public ArrayList<String> getLevels() {
        ArrayList<String> levels = new ArrayList<>();
        for (Level level : Level.values()) {
            levels.add(String.valueOf(level));
        }
        return levels;
    }


    public void selectLevel(Stage stage) throws InterruptedException {
        level = Level.valueOf(levelList.getSelectionModel().getSelectedItem());
        Game game = new Game(level);
        stage.close();
    }
}