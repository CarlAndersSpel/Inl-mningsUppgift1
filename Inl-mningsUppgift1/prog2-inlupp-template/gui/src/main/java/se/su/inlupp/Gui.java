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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Gui extends Application {
    // private FXForm test;
       private TextField aText = new TextField();
         private Pane center;
       //private ListView hej = new ListView();
  public void start(Stage stage) {
       BorderPane root = new BorderPane();
         center = new Pane();
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
    //Label top = new Label("Top");
    //root.setTop(aRoot);
    /*top.setAlignment(Pos.TOP_CENTER);
    top.setPrefWidth(2000);
    top.setPrefHeight(300);
    //HBox colorRow = new HBox(top); */
    //label.setBackground(Background.fill(Color.LIGHTGREY));
    button.setBackground(Background.fill(Color.GREEN));
    button2.setBackground(Background.fill(Color.BLANCHEDALMOND));
    button3.setBackground(Background.fill(Color.CHOCOLATE));

    HBox buttonRow = new HBox(10, button2, button3, aText, button);

    Image image = new Image(getClass().getResourceAsStream("/images/bg_dark_wood.jpg"));
    //System.out.println(getClass().getResource("/images/bg_dark_wood.jpg"));
    ImageView imageView = new ImageView(image);
    center.getChildren().add(imageView);


    HBox PictureRow = new HBox(10, button2, button3, aText, button);
    //root.getChildren().add(button);<  ZCr§
    label.setTextFill(Color.RED);
    //buttonRow.setAlignment(Pos.TOP_CENTER);
        buttonRow.setStyle("-fx-background-color: red;");
        PictureRow.setStyle("-fx-background-color: blue;");   

    root.setTop(buttonRow);
    root.setCenter(imageView);
    //root.setBottom(top);


      buttonRow.getChildren().add(button3); 
      buttonRow.getChildren().add(button2);
      buttonRow.getChildren().add(aText);
      buttonRow.getChildren().add(button);
    

        button.setOnAction(
        ev -> {
          String name = aText.getText();   //byta nameField till aText
         graph.add(name);     //från MyPath istället för output
         System.out.println("DET FUNKAR");
        });


         Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();

         /*    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setHeight(700);
    stage.setWidth(850);*/

    imageView.fitWidthProperty().bind(stage.widthProperty());

    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }//första verision att göra allt i samma klass och om denblir för mycket bryt upp den till mindre mindre klasser
  //lägger ut knappar och vart ska graphen "att lev"/komma åt. vad gör varje knapp skapa en hanterare för skärmen.
}
