package se.su.inlupp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.awt.image.BufferedImage;
import javafx.stage.Stage;

public class Gui extends Application {
    // private FXForm test;
      private List<File> imageFiles = new ArrayList<>();
       private TextField aText = new TextField();
         private Pane center;
         private Pane right;
         private boolean nodeCheck = false;
        Button button6, saveButton;
       //private ListView hej = new ListView();
  public void start(Stage stage) {
    int width = 600;
    int height = 400;

       BorderPane root = new BorderPane();
         center = new Pane();
         right = new Pane();
    Graph<String> graph = new ListGraph<String>();
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

    Button button2 = new Button("boka");
    Button button3 = new Button("File");
    Button button4 = new Button("spara");
    Button button5 = new Button("Sök");
    Button button = new Button("Skicka");

    Button button6 = new Button("Make Node");
    Button button7 = new Button("Make Connect");
    Button button8 = new Button("Manage");

        //saveButton.setOnAction(new SaveButtonHandler());

 
    
    button.setBackground(Background.fill(Color.GREEN));
    button2.setBackground(Background.fill(Color.BLANCHEDALMOND));
    button3.setBackground(Background.fill(Color.CHOCOLATE));
    button4.setBackground(Background.fill(Color.BLUEVIOLET));
    button5.setBackground(Background.fill(Color.YELLOW));


   // button6.setBackground(Background.fill(Color.CHOCOLATE));
    button7.setBackground(Background.fill(Color.BLUEVIOLET));
    button8.setBackground(Background.fill(Color.YELLOW));
    
    
    HBox buttonRow = new HBox(10);
    VBox buttonOtherRow = new VBox(10);
    
    //right.getChildren().add(buttonOtherRow);
    
    Image image = new Image(getClass().getResourceAsStream("/images/bg_dark_wood.jpg"));
    //System.out.println(getClass().getResource("/images/bg_dark_wood.jpg"));
    ImageView imageView = new ImageView(image);
    center.getChildren().add(imageView);


    //HBox PictureRow = new HBox(10, button2, button3, aText, button, button4);
    //root.getChildren().add(button);<  ZCr§
    label.setTextFill(Color.RED);
    //buttonRow.setAlignment(Pos.TOP_CENTER);
        buttonRow.setStyle("-fx-background-color: lightgrey;");
        buttonOtherRow.setStyle("-fx-background-color: lightgrey;");
        //PictureRow.setStyle("-fx-background-color: blue;");   

        root.setTop(buttonRow);
        //root.setCenter(imageView);
        root.setLeft(buttonOtherRow);
    //root.setBottom(top);


      buttonRow.getChildren().add(button3); 
      buttonRow.getChildren().add(button5);
      buttonRow.getChildren().add(button2);
      buttonRow.getChildren().add(aText);
      buttonRow.getChildren().add(button);
      buttonRow.getChildren().add(button4);

      buttonOtherRow.getChildren().add(button6); 
      button6.setOnAction(new NewButtonHandler());

      buttonOtherRow.getChildren().add(button7);
      buttonOtherRow.getChildren().add(button8);

        button.setOnAction(
        ev -> {
          String name = aText.getText();   //byta nameField till aText
         graph.add(name);     //från MyPath istället för output
         System.out.println("DET FUNKAR");
        });


        button6.setOnAction(
        ev -> {
          String name = aText.getText();   //byta nameField till aText
         graph.add(name);     //från MyPath istället för output
         Circle ball1 = new Circle(width * 0.3, height * 0.65, 25, Color.WHITE);
         System.out.println("DET FUNKAR");


        });


         Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        //stage.show();

         /*    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setHeight(700);
    stage.setWidth(850);*/

    imageView.fitWidthProperty().bind(stage.widthProperty());
    //imageView.fitHeightProperty().bind(stage.heightProperty());

    stage.show();
  }

    class NewButtonHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            center.setOnMouseClicked(new ClickHandler());

            center.setCursor(Cursor.CROSSHAIR);
            System.out.print("DET ÄNTLIGEN FUNKAR");
            button6.setDisable(true);
        }
    }

    class ClickHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            double x = event.getX();
            double y = event.getY();

            //PostItNote note = new PostItNote(x, y);
            //center.getChildren().add(note);

            center.setCursor(Cursor.DEFAULT);
            System.out.println("DET HÄR FUNKAR OCKSÅ");


            Circle ball1 = new Circle(x, y, 10);
            center.getChildren().add(ball1);
            button6.setDisable(false);
            center.setOnMouseClicked(null);
        }
    }

   /*   class SaveButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                WritableImage image = center.snapshot(null,null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ImageIO.write(bufferedImage, "jpg", new File("bg_dark_wood.jpg"));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "IO Error");
                alert.showAndWait();
            }
        }
    }*/


  public static void main(String[] args) {
    launch(args);
  }//första verision att göra allt i samma klass och om denblir för mycket bryt upp den till mindre mindre klasser
  //lägger ut knappar och vart ska graphen "att lev"/komma åt. vad gör varje knapp skapa en hanterare för skärmen.
}
