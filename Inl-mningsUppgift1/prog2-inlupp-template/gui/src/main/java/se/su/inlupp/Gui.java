package se.su.inlupp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.awt.image.BufferedImage;
import javafx.stage.Stage;

public class Gui extends Application {
  // private FXForm test;
  private List<File> imageFiles = new ArrayList<>();
  private TextField aText = new TextField();
  private Pane center;
  private Pane right;
  private boolean nodeCheck = false;
  private GridPane fullScreen = new GridPane();
  private double startX, startY;
  Button button6, saveButton;
  private Text nodeText;
  Graph<String> graph = new ListGraph<String>();
  private final Map<String,StackPane> nameList = new HashMap<>();
        
  public class Node extends BorderPane {
         public Node(double x, double y){
         relocate(x, y);
        //Pane titlebar = new Pane();
        //setCenter(titlebar);
      //  Text nodText = new Text(50, 40, "name");
        setCenter(nodeText);
        //titlebar.setPrefSize(50,40);

        setOnMousePressed(new StartDragHandler());
        setOnMouseDragged(new DragHandler());
         }


               class StartDragHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
              startX = event.getX();
              startY = event.getY();
        }
    }

    class DragHandler implements EventHandler<MouseEvent> {

        public void handle(MouseEvent event) {
            double newX = getLayoutX() + event.getX() - startX;
            double newY = getLayoutY() + event.getY() - startY;
            relocate(newX, newY);
        }
    }
  }



       //private ListView hej = new ListView();
  public void start(Stage stage) {
    int width = 600;
    int height = 400;
    
    BorderPane root = new BorderPane();
    center = new Pane();
    right = new Pane();
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    
    Button button2 = new Button("boka");
    Button button3 = new Button("File");
    Button button4 = new Button("spara");
    Button button5 = new Button("Sök");
    Button button = new Button("Skicka");
    
    Button makeNodeButton = new Button("Make Node");
    makeNodeButton.setOnAction(new NewButtonHandler());
    Button removeNodeButton = new Button("Remove Node");
    Button button7 = new Button("Make Connect");
    Button button8 = new Button("Manage");
    Text textError = new Text(" ");
    
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
    HBox ErrorRow = new HBox(10);
    VBox buttonOtherRow = new VBox(10);
    
    //right.getChildren().add(buttonOtherRow);
    
    Image image = new Image(getClass().getResourceAsStream("/images/bg_dark_wood.jpg"));
    //System.out.println(getClass().getResource("/images/bg_dark_wood.jpg"));
    ImageView imageView = new ImageView(image);
    center.getChildren().add(imageView);
    center.getChildren().add(fullScreen);
    
    
    //HBox PictureRow = new HBox(10, button2, button3, aText, button, button4);
    //root.getChildren().add(button);<  ZCr§
    label.setTextFill(Color.RED);
    //buttonRow.setAlignment(Pos.TOP_CENTER);
    buttonRow.setStyle("-fx-background-color: lightgrey;");
    buttonOtherRow.setStyle("-fx-background-color: lightgrey;");
    ErrorRow.setStyle("-fx-background-color: lightgrey;");
        center.setStyle("-fx-background-color: white;");

    //PictureRow.setStyle("-fx-background-color: blue;");   
    
    root.setTop(buttonRow);
    root.setCenter(center);
    //root.setCenter(imageView);
    root.setRight(buttonOtherRow);
    root.setBottom(ErrorRow);
    //root.setBottom(top);
    
    
    buttonRow.getChildren().add(button3); 
    buttonRow.getChildren().add(button5);
    buttonRow.getChildren().add(button2);
    buttonRow.getChildren().add(aText);
    buttonRow.getChildren().add(button);
    buttonRow.getChildren().add(button4);
    
    buttonOtherRow.getChildren().add(makeNodeButton); 
    
    buttonOtherRow.getChildren().add(button7);
    buttonOtherRow.getChildren().add(button8);
    buttonOtherRow.getChildren().add(removeNodeButton);
    ErrorRow.getChildren().add(textError);
    
    button.setOnAction(
      ev -> {
        String name = aText.getText();   //byta nameField till aText
        graph.add(name);     //från MyPath istället för output
        System.out.println("DET FUNKAR");
      });
      
      
      makeNodeButton.setOnAction(
        ev -> {
          String name = aText.getText().toUpperCase();   //byta nameField till aText
          if(name.equals("")){
            textError.setText("Error: Location is missing name");
            return;
          } else if(graph.hasNode(name)){
            //nodeText.setText("name");
            System.out.println("FUNKAR det?");
            textError.setText("Error: Location already Exists");
            return;
          }
          graph.add(name);     //från MyPath istället för output
          textError.setText("");
          Circle ball1 = new Circle(20.3, 50.65, 25, Color.RED);
          Text nodText = new Text(50, 40, name);
          StackPane combinedNode = new StackPane(ball1, nodText);
          center.getChildren().add(combinedNode);
          nameList.put(name, combinedNode);
          System.out.println("DET FUNKAR");
          
          //center.getChildren().add(nodText);
          //center.getChildren().add(ball1);


        });

       removeNodeButton.setOnAction(
        ev -> {
          String name = aText.getText().toUpperCase();   //byta nameField till aText
          if(graph.hasNode(name)){
            graph.remove(name);
            center.getChildren().remove(nameList.get(name));
            nameList.remove(name);
            //center.getChildren().remove();
            //nodeText.setText("name");
            System.out.println("varfor funkar den?");
          textError.setText("");
            return;
          }
          textError.setText("Error: Location doesn't exist");
          System.out.println("den INTE funkar?");
          
          //center.getChildren().add(nodText);
          //center.getChildren().add(ball1);


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
      @Override
        public void handle(ActionEvent event) {
          System.out.print("DET ÄNTLIGEN FUNKAR");
            center.setOnMouseClicked(new ClickHandler());

            center.setCursor(Cursor.CROSSHAIR);
            button6.setDisable(true);
        }
    }

    class ClickHandler implements EventHandler<MouseEvent> {
      @Override
        public void handle(MouseEvent event) {
          System.out.println("DET HÄR FUNKAR OCKSÅ");
            double x = event.getX();
            double y = event.getY();

            Node node = new Node(x,y);
            center.getChildren().add(node);
            
            center.setCursor(Cursor.DEFAULT);
            
            
            Circle ball1 = new Circle(x, y, 10, Color.ALICEBLUE);
            center.getChildren().add(ball1);
            /*fullScreen.add(ball1, 10, 10);    
            ball1.toFront();*/
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
