package ir.ac.kntu;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SelectUser extends Application {
    private static User user;

    private ListView<String> userList;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        SelectUser.user = user;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        User user1=new User("ali",0,0);
//        User user2=new User("a",0,0);
//        User user3=new User("b",0,0);
//        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("src/main/java/ir/ac/kntu/Data/users"));
//        objectOutputStream.writeObject(user1);
//        objectOutputStream.writeObject(user2);
//        objectOutputStream.writeObject(user3);
//        objectOutputStream.close();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        primaryStage.setTitle("User Selection");
        Label instructions = new Label("Select a user:");
        instructions.setTextFill(Color.WHITE);
        instructions.setStyle("-fx-font-size: 16pt;");
        ObservableList<String> users = FXCollections.observableArrayList(
                getUsername());
        userList = new ListView<>(users);
        userList.setPrefSize(200, 250);
        userList.setStyle("-fx-control-inner-background: black; -fx-background-color: black;");
        Button createUserButton = new Button("Create User");
        createUserButton.setOnAction(e -> createUser());
        createUserButton.setStyle("-fx-background-color: #424640; -fx-text-fill: white;");
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(10));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(choseButton(primaryStage),createUserButton);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(instructions);
        borderPane.setCenter(userList);
        borderPane.setBottom(buttonBox);
        borderPane.setStyle("-fx-background-color: black;");
        primaryStage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button choseButton(Stage stage){
        Button choseButton = new Button("Choose");
        choseButton.setStyle("-fx-background-color: #424640; -fx-text-fill: white;");
        choseButton.setOnAction(e -> {
            try {
                performActionOnSelectedUser(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        return choseButton;
    }

    private void performActionOnSelectedUser(Stage stage) throws IOException, ClassNotFoundException {
        String selectedUser = userList.getSelectionModel().getSelectedItem();
        for (User user1:getUsers()){
            if (user1.getName().equals(selectedUser)){
                user=user1;
                UserPage userPage=new UserPage(user);
                userPage.openPage();
                stage.close();
                break;
            }
        }
    }

    private void createUser(){
        Stage createUserStage = new Stage();
        createUserStage.setTitle("Create User");
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        Button createButton = new Button("Create");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(nameLabel, nameTextField, createButton);
        createButton.setOnAction(event -> {
            String name = nameTextField.getText();
            User newUser = new User(name,0,0);
            addUserToFile(newUser);
            try {
                userList.setItems(FXCollections.observableArrayList(getUsername()));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            createUserStage.close();
        });
        Scene scene = new Scene(vbox, 300, 100);
        createUserStage.setScene(scene);
        createUserStage.show();
    }

    public ArrayList<User> getUsers() throws IOException, ClassNotFoundException {
        ArrayList<User> users =new ArrayList<>();
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("src/main/java/ir/ac/kntu/Data/users"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/main/java/ir/ac/kntu/Data/users temp"));
        User user1;
        try {
            user1=(User) objectInputStream.readObject();
        }catch (Exception e){
            user1=null;
        }

        while (user1!=null){
            users.add(user1);
            objectOutputStream.writeObject(user1);
            try {
                user1=(User) objectInputStream.readObject();
            }catch (Exception e){
                user1=null;
            }
        }
        objectInputStream.close();
        return users;
    }

    private ArrayList<String> getUsername() throws IOException, ClassNotFoundException {
        ArrayList<String> userNameList = getUsers().stream()
                .map(User::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        return userNameList;
    }

    private void addUserToFile(User newUser){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/main/java/ir/ac/kntu/Data/users"));
            ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("src/main/java/ir/ac/kntu/Data/users temp"));
            User user1;
            try {
                user1=(User) objectInputStream.readObject();
            }catch (Exception e){
                user1=null;
            }

            while (user1!=null){
                objectOutputStream.writeObject(user1);
                try {
                    user1=(User) objectInputStream.readObject();
                }catch (Exception e){
                    user1=null;
                }
            }
            objectInputStream.close();
            objectOutputStream.writeObject(newUser);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}