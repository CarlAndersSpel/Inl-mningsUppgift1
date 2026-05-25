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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.input.MouseButton;

import java.awt.image.BufferedImage;
import javafx.stage.Stage;

public class Gui extends Application {
  // private FXForm test;
  private List<File> imageFiles = new ArrayList<>();
  private TextField aText = new TextField();
  private Pane center;
  private double startX, startY;
  Button makeNodeButton, saveButton;
  private Text nodeText;
  Graph<String> graph = new ListGraph<String>();
  Text textError = new Text(" ");
  private final Map<String,Node> nameList = new HashMap<>();
  
  public class Node extends BorderPane {
    Button destroyButton = new Button("Förstör");
    Button makeConnectionButton = new Button("Skapa koppling");
    Button addConnection = new Button("lägg en koppling");
    private TextField weightEdgeText = new TextField();
    private TextField nameEdgeText = new TextField();
    String name;
    Dialog<Void> nodContain;
    Dialog<Void> conContain;
    static String selectedNode;
    
         public Node(String name, double x, double y){
           this.name = name;
           relocate(x, y);
           //Pane titlebar = new Pane();
           //setCenter(titlebar);
           //  Text nodText = new Text(50, 40, "name");
           setCenter(nodeText);
           //titlebar.setPrefSize(50,40);
           
           setOnMousePressed(new StartDragHandler());
           setOnMousePressed(new ClickHandler());
           setOnMouseDragged(new DragHandler());
           
         destroyButton.setOnAction(
           ev -> {
             if(graph.hasNode(name)){
               graph.remove(name);
            center.getChildren().remove(nameList.get(name));
            nameList.remove(name);
            //center.getChildren().remove();
            //nodeText.setText("name");
            System.out.println("varfor funkar den?");
            textError.setText("");
            nodContain.close();
            
            return;
          }
          
          textError.setText("Error: Location doesn't exist");
          System.out.println("den INTE funkar?");
        });
        
        
        makeConnectionButton.setOnAction(
          ev -> {
            selectedNode = name;
            
            setOnMousePressed(new ConnectionClickHandler());
            nodContain.close();
            return;
          });


          addConnection.setOnAction(
            ev -> {
              String nameEdge = nameEdgeText.getText().toUpperCase();
              int weightEdge = Integer.parseInt(weightEdgeText.getText());  //gör från en textfield till en integer
              graph.connect(selectedNode, name, nameEdge, weightEdge);
              System.out.print("Linjen funkar!");
              setOnMousePressed(new ConnectionClickHandler());
              nameEdgeText.clear();
              weightEdgeText.clear();
              conContain.close();
              return;
            });





        }

        

        
        class StartDragHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
          System.out.print("DEN BÖRJAR DRA");
              startX = event.getX();
              startY = event.getY();
        }
    }

    class ConnectionClickHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
          if(event.getButton() == MouseButton.SECONDARY){
           VBox conBox = new VBox(10);


         //  MyEdge<Node> edge = new MyEdge<Node>(selectedNode, );
            
            conContain = new Dialog<>();
            Text weightEdgeName = new Text("weight");
            Text nameEdgeName = new Text("name");
            conContain.getDialogPane().setContent(conBox);     
            conBox.getChildren().add(addConnection);
            conBox.getChildren().add(weightEdgeName);
            conBox.getChildren().add(weightEdgeText);
            conBox.getChildren().add(nameEdgeName);
            conBox.getChildren().add(nameEdgeText);

            conContain.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            conContain.showAndWait();
          }
          return;

        }
    }


    class ClickHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
          if(event.getButton() == MouseButton.SECONDARY){
             VBox nodBox = new VBox(10);
            
            nodContain = new Dialog<>();
           // Pane testPane = new Pane();
            Label testName = new Label(name);
            nodContain.setContentText(name);         
            nodBox.getChildren().add(testName);
            nodBox.getChildren().add(destroyButton);
            nodBox.getChildren().add(makeConnectionButton);
            nodContain.getDialogPane().setContent(nodBox);
            nodContain.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            nodContain.showAndWait();
         
          }
          return;
         // nodContain.close();
        }

    }

    class DragHandler implements EventHandler<MouseEvent> {

        public void handle(MouseEvent event) {
          if(event.getButton() == MouseButton.PRIMARY){         
          System.out.print("DEN DRAR NU");
            double newX = getLayoutX() + event.getX() - startX;
            double newY = getLayoutY() + event.getY() - startY;
            if (getLayoutX() + event.getX() - startX < 0){
              newX = 0;
            }
            if (getLayoutY() + event.getY() - startY < 0){
              newY = 0;
            }
            relocate(newX, newY);
        }
      }
    }
  }




  public void start(Stage stage) {

    BorderPane root = new BorderPane();
    center = new Pane();
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    
    Button button2 = new Button("boka");
    Button button3 = new Button("File");
    Button button4 = new Button("spara");
    Button button5 = new Button("Sök");
    Button button = new Button("Skicka");
    
    makeNodeButton = new Button("Make Node");
   // makeNodeButton.setOnAction(new NewButtonHandler());
    Button removeNodeButton = new Button("Remove Node");
    Button button7 = new Button("Make Connect");
    Button button8 = new Button("Manage");
    
    //saveButton.setOnAction(new SaveButtonHandler());
    
    
    
    button.setBackground(Background.fill(Color.GREEN));
    button2.setBackground(Background.fill(Color.BLANCHEDALMOND));
    button3.setBackground(Background.fill(Color.CHOCOLATE));
    button4.setBackground(Background.fill(Color.BLUEVIOLET));
    button5.setBackground(Background.fill(Color.YELLOW));
    
    
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
    //center.getChildren().add(fullScreen);
    
    
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

      });
      
      
      makeNodeButton.setOnAction(
        ev -> {
          center.setOnMouseClicked(new ClickHandler());

          center.setCursor(Cursor.CROSSHAIR);
          makeNodeButton.setDisable(true);
          
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
          

          
          
        });
        
        
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);      
        imageView.fitWidthProperty().bind(stage.widthProperty());
        //imageView.fitHeightProperty().bind(stage.heightProperty());
        
        stage.show();
      }
      
      
      /* class NewButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
          System.out.print("DET ÄNTLIGEN FUNKAR");
          center.setOnMouseClicked(new ClickHandler());
          
          center.setCursor(Cursor.CROSSHAIR);
          makeNodeButton.setDisable(true);
          }
          }*/
         
         class ClickHandler implements EventHandler<MouseEvent> {
           @Override
             public void handle(MouseEvent event) {
               System.out.println("DET HÄR FUNKAR OCKSÅ");
               double x = event.getX();
               double y = event.getY();
               
               center.setCursor(Cursor.DEFAULT);
               String name = aText.getText().toUpperCase();   //byta nameField till aText
               if(name.equals("")){
                 textError.setText("Error: Location is missing name");
                             makeNodeButton.setDisable(false);
                 return;
               } else if(graph.hasNode(name)){
                 System.out.println("FUNKAR det verkligen?");
                 textError.setText("Error: Location already Exists");
                             makeNodeButton.setDisable(false);
                 return;
               }
               graph.add(name);     //från MyPath istället för output
               textError.setText("");
               Circle ball1 = new Circle(x, y, 25, Color.RED);
               if(name.length() > 5){

               Text nodText = new Text( name.substring(0,5) + "...");
               StackPane combinedNode = new StackPane(ball1, nodText);
               Node node = new Node(name, x, y);
               node.getChildren().add(combinedNode);
               node.setLayoutX(x);
               node.setLayoutY(y);
               center.getChildren().add(node);
               nameList.put(name, node);
               System.out.println("DET FUNKAR");
               
               makeNodeButton.setDisable(false);
               center.setOnMouseClicked(null);
               return;
               }
               Text nodText = new Text(name);
               StackPane combinedNode = new StackPane(ball1, nodText);
               Node node = new Node(name, x, y);
               node.getChildren().add(combinedNode);
               node.setLayoutX(x);
               node.setLayoutY(y);
               center.getChildren().add(node);
               nameList.put(name, node);
               System.out.println("DET FUNKAR");
               
            makeNodeButton.setDisable(false);
            center.setOnMouseClicked(null);
            return;
          }
        }

        class DialogOpener implements EventHandler<MouseEvent> {
          @Override
          public void handle(MouseEvent event){
          Dialog<String> nodContain = new Dialog<>();
          nodContain.showAndWait();
          

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
