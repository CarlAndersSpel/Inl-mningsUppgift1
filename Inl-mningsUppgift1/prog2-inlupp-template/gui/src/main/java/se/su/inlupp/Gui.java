package se.su.inlupp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Gui extends Application {
    // private FXForm test;
       private TextField aText = new TextField();
       //private ListView hej = new ListView();
  public void start(Stage stage) {
            BorderPane root = new BorderPane();
    Graph<String> graph = new ListGraph<String>();
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

    /*VBox root = new VBox(30, label);
    root.setAlignment(Pos.CENTER);
    Scene scene = new Scene(root, 640, 480);
    stage.setScene(scene);
    stage.show();*/
    //HBox aRoot = new HBox(30, label);
    
    Button button2 = new Button("boka");
    Button button3 = new Button("byt sök väg");
    Button button = new Button("Skicka");
    Label top = new Label("Top");
    //root.setTop(aRoot);
    /*top.setAlignment(Pos.TOP_CENTER);
    top.setPrefWidth(2000);
    top.setPrefHeight(300);
    //HBox colorRow = new HBox(top); */
    label.setBackground(Background.fill(Color.LIGHTGREY));
    button.setBackground(Background.fill(Color.RED));
    button2.setBackground(Background.fill(Color.BLANCHEDALMOND));
    button3.setBackground(Background.fill(Color.CHOCOLATE));
    HBox buttonRow = new HBox(10, label, button2, button3, aText, button);
    //root.getChildren().add(button);<  ZCr§
    label.setTextFill(Color.RED);
    //buttonRow.setAlignment(Pos.TOP_CENTER);
       // buttonRow.setStyle("-fx-color: #343434");

    root.setCenter(buttonRow);
    //root.setBottom(top);
    

        button.setOnAction(
        ev -> {
          String name = aText.getText();   //byta nameField till aText
         graph.add(name);     //från MyPath istället för output
         System.out.println("DET FUNKAR");
        });


        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }//första verision att göra allt i samma klass och om denblir för mycket bryt upp den till mindre mindre klasser
  //lägger ut knappar och vart ska graphen "att lev"/komma åt. vad gör varje knapp skapa en hanterare för skärmen.
}
