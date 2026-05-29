package se.su.inlupp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
  private List<Node> chosenNodes = new ArrayList<>();  //kontrollerar att om det redan finns två noder markerade så gör den ingenting annars lägger till clickNodes(clickhandler för noderna) till chosenNodes och om man klickar på en nod och den redan finns i chosenNodes så tas den bort från chosenNodes
  private HashSet<Edge1> edges = new HashSet<>();
  private final Map<String, Edge1> edgesTest = new HashMap<>();
  private TextField aText = new TextField();
  private Pane center;
  private ImageView imageView;
  private double startX, startY;
  Button makeNodeButton, saveButton;
  private Text nodeText;
  Graph<String> graph = new ListGraph<String>();
  Text textError = new Text(" ");
  Dialog<Void> nodContain;
  Dialog<Void> conContain;
  String selectedNode;
  Button addConnection = new Button("lägg en koppling");
  private TextField weightEdgeText = new TextField();
  private TextField nameEdgeText = new TextField();
  
  private final Map<String,Node> nameList = new HashMap<>();
  //private final Map<String,String> connectedCheck = new HashMap<>();

  public class Node extends BorderPane {
    private HashSet<Edge1> edgeSet = new HashSet<>();
    Button destroyButton = new Button("Förstör");
    Button makeSelectionButton = new Button("Markera plats");
    String name;
    Circle sprite;
    double x;
    double y;
    
         public Node(String name, double x, double y, Circle sprite){
           this.name = name;
           this.sprite = sprite;
           this.x = x;
           this.y = y;
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
                if(graph.getEdgesFrom(name) != null){
         // center.getChildren().remove(edgesTest.get(graph.getEdgesFrom(name)));
                  graph.getEdgesFrom(name);
                  for(Edge<String> e : graph.getEdgesFrom(name)){
                    center.getChildren().remove(edgesTest.get(e.getName()));
                    edges.remove(edgesTest.get(e.getName()));
                    edgesTest.remove(e.getName());
                    //graph.disconnect(name, hej.getDestination());
                  }
                } 
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
          //man kan bara ha de två senaste noderna man kan skapa och man måste trycka på mae connections för att skapa just de senaste kopplingen mellan noder.
          textError.setText("Error: Location doesn't exist");
          System.out.println("den INTE funkar?");
        });
        
        
        makeSelectionButton.setOnAction(
          ev -> {
            selectedNode = name;
            if(chosenNodes.contains(nameList.get(name))){
              chosenNodes.remove(nameList.get(name));
              sprite.setFill(Color.RED);
              if(chosenNodes.size() >= 1){
                chosenNodes.get(0).sprite.setFill(Color.GREEN);
              }
            }else {
              if(chosenNodes.size() == 2){
                chosenNodes.get(0).sprite.setFill(Color.RED);
                chosenNodes.remove(0);
              }
              chosenNodes.add(nameList.get(name));
              if(chosenNodes.size() == 2){  
                chosenNodes.get(1).sprite.setFill(Color.BLUE);
              }
              chosenNodes.get(0).sprite.setFill(Color.GREEN);
      
              
              
            }
            nodContain.close();
            return;
          });


          addConnection.setOnAction(
            ev -> {
              if(nameEdgeText.getText().isEmpty() || weightEdgeText.getText().isEmpty()){
               textError.setText("Error: TextField is empty");    
               return;          
              }
              String nameEdge = nameEdgeText.getText().toUpperCase();
              int weightEdge = Integer.parseInt(weightEdgeText.getText());  //gör från en textfield till en integer
              if(edgesTest.containsKey(nameEdge)){
               textError.setText("Error: Name already exists");
               return;
              }
              graph.connect(chosenNodes.get(0).name, chosenNodes.get(1).name, nameEdge, weightEdge); //vi tar namnet från noden på plats noll och plats ett på listan och lägger in den och skapar en edge
              chosenNodes.get(0).sprite.setFill(Color.RED);
              chosenNodes.get(1).sprite.setFill(Color.RED);
              Line line = new Line();
              //line.setStyle("-fx-stroke: blue;"); 
              line.startXProperty().bind(chosenNodes.get(0).layoutXProperty());
              line.startYProperty().bind(chosenNodes.get(0).layoutYProperty());
              line.endXProperty().bind(chosenNodes.get(1).layoutXProperty());
              line.endYProperty().bind(chosenNodes.get(1).layoutYProperty());
              graph.getEdgeBetween(chosenNodes.get(0).name, chosenNodes.get(1).name);
              Edge1 edge = new Edge1(nameEdge, weightEdge, chosenNodes.get(0), chosenNodes.get(1), line);
              Text testEdgelabel = new Text("Namn: " + nameEdge + "\nKostnad: " + weightEdge);
              testEdgelabel.layoutXProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2).subtract(20));
              testEdgelabel.layoutYProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2).subtract(5));
              edge.getChildren().add(testEdgelabel);
              edge.getChildren().add(line);
              testEdgelabel.setFill(Color.WHITE);
              testEdgelabel.toFront();
              center.getChildren().add(edge);
              edge.toBack();
              imageView.toBack();
              edges.add(edge);
              edgesTest.put(nameEdge, edge);
              System.out.print(edge);
              System.out.print(graph.getEdgeBetween(chosenNodes.get(0).name, chosenNodes.get(1).name));
             // chosenNodes.get(0).edgeSet.add(edge);
             // chosenNodes.get(1).edgeSet.add(edge);
              
           /* line.setStartX(chosenNodes.get(0).x);
              line.setStartY(chosenNodes.get(0).y);
              line.setEndX(chosenNodes.get(1).x);
              line.setEndY(chosenNodes.get(1).y);*/
             // connectedCheck.put(chosenNodes.get(0).name, chosenNodes.get(1).name);
              System.out.print("Linjen funkar!");
              chosenNodes.clear();
              nameEdgeText.clear();
              weightEdgeText.clear();
              conContain.close();
              textError.setText("");
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
        
        
        
        class ClickHandler implements EventHandler<MouseEvent> {
          public void handle(MouseEvent event) {
              if(event.getButton() == MouseButton.SECONDARY){
                //setOnMousePressed(new ColorClickHandler());
             
            VBox nodBox = new VBox(10);
            
            nodContain = new Dialog<>();
            // Pane testPane = new Pane();
            Label testName = new Label(name);
            nodContain.setContentText(name);         
            nodBox.getChildren().add(testName);
            nodBox.getChildren().add(destroyButton);
            nodBox.getChildren().add(makeSelectionButton);
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
          //System.out.print("DEN DRAR NU");
          double newX = getLayoutX() + event.getX() - startX; //startx is starting position of mouse and getX is current position of mouse
          double newY = getLayoutY() + event.getY() - startY; 
         // System.out.print(newX);
          if (getLayoutX() + event.getX() - startX < 0){
            System.out.print(newX);
            newX = 0;
          }
          if (getLayoutY() + event.getY() - startY < 0){
            System.out.print(newY);
            newY = 0;
          }
          relocate(newX, newY);   //video games engine check every frame         
          x = newX;
          y = newY;
        }
      }
    }
  }
  
  public class Edge1 extends BorderPane {
  private final HashSet<Node> nodeSet = new HashSet<>();
  String name;
  Node node1, node2;
  Line sprite;
  int weight;

          public Edge1(String name, int weight, Node node1,Node node2, Line sprite){
           this.name = name;
           this.weight = weight;
           this.node1 = node1;
           this.node2 = node2;
           this.sprite = sprite;


           nodeSet.add(node1);
           nodeSet.add(node2);
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
    Button searchWay = new Button("Sök väg");
    Button button = new Button("Skicka");
    
    makeNodeButton = new Button("Make Node");
    // makeNodeButton.setOnAction(new NewButtonHandler());
    Button removeNodeButton = new Button("Remove Node");
    Button makeConnections = new Button("Make Connect");
    Button removeConnections = new Button("Remove Connect");
    Button button8 = new Button("Manage");
    RadioButton bfsWay = new RadioButton("bfs");
    RadioButton dfsWay = new RadioButton("dfs");

    ToggleGroup witchWay = new ToggleGroup();
    bfsWay.setToggleGroup(witchWay);
    dfsWay.setToggleGroup(witchWay);
    
    //saveButton.setOnAction(new SaveButtonHandler());
    
    
    
    button.setBackground(Background.fill(Color.GREEN));
    button2.setBackground(Background.fill(Color.BLANCHEDALMOND));
    button3.setBackground(Background.fill(Color.CHOCOLATE));
    button4.setBackground(Background.fill(Color.BLUEVIOLET));
    searchWay.setBackground(Background.fill(Color.YELLOW));
    
    
    makeConnections.setBackground(Background.fill(Color.BLUEVIOLET));
    button8.setBackground(Background.fill(Color.YELLOW));
    
    
    HBox buttonRow = new HBox(10);
    HBox ErrorRow = new HBox(10);
    VBox buttonOtherRow = new VBox(10);
    
    //right.getChildren().add(buttonOtherRow);
    
    Image image = new Image(getClass().getResourceAsStream("/images/bg_dark_wood.jpg"));
    //System.out.println(getClass().getResource("/images/bg_dark_wood.jpg"));
    imageView = new ImageView(image);
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
    buttonRow.getChildren().add(button2);
    buttonRow.getChildren().add(aText);
    buttonRow.getChildren().add(button);
    buttonRow.getChildren().add(button4);
    
    buttonOtherRow.getChildren().add(makeNodeButton); 
    
    buttonOtherRow.getChildren().add(makeConnections);
    buttonOtherRow.getChildren().add(removeConnections);
    buttonOtherRow.getChildren().add(button8);
    buttonOtherRow.getChildren().add(removeNodeButton);
    buttonOtherRow.getChildren().add(searchWay);
    buttonOtherRow.getChildren().add(bfsWay);
    buttonOtherRow.getChildren().add(dfsWay);
    ErrorRow.getChildren().add(textError);
    
    
    button.setOnAction(
      ev -> {
        
      });
      
      
    searchWay.setOnAction(
      ev -> {    
      /* if (!graph.getNodes().contains(chosenNodes.get(0).name) || !graph.getNodes().contains(chosenNodes.get(1).name)) {
          textError.setText("Error: Destinationen sitter inte ihop");
          return;
         }*/  //kanske funkar.
        if(chosenNodes.size() == 2) {
          for(Edge1 a : edges){
            a.sprite.setStyle("-fx-stroke: black;");
          }
          RadioButton bw =(RadioButton)witchWay.getSelectedToggle();
          if(bw == null){
            textError.setText("Error: ingen sökväg vald");
            return;
          }
          if(bw.getText().equals("bfs")){ 
            PathFinder<String> bfs = new BFSPathFinder<>();
            Path<String> path = bfs.findPath(graph, chosenNodes.get(0).name, chosenNodes.get(1).name);    
            for (Edge<String> b : path.getEdges()){
              if(edgesTest.keySet().contains(b.getName())){
             edgesTest.get(b.getName()).sprite.setStyle("-fx-stroke: blue;");
              }
              }
                textError.setText("");
               return;
              }else if(bw.getText().equals("dfs")){
            PathFinder<String> dfs = new DFSPathFinder<>();    
            Path<String> path = dfs.findPath(graph, chosenNodes.get(0).name, chosenNodes.get(1).name);   
            for (Edge<String> b : path.getEdges()){
              if(edgesTest.keySet().contains(b.getName())){
                edgesTest.get(b.getName()).sprite.setStyle("-fx-stroke: blue;");
              }
            }  
          textError.setText("");
          return;
          }
          //om dfs gör det 
          
          
          
        }
        textError.setText("Error: ingen destinationer vald");
      });


      makeNodeButton.setOnAction(
        ev -> {
          center.setOnMouseClicked(new ClickHandler());
          
          center.setCursor(Cursor.CROSSHAIR);
          makeNodeButton.setDisable(true);
          
        });
        
      makeConnections.setOnAction(
        ev -> {
          if(graph.getEdgeBetween(chosenNodes.get(0).name, chosenNodes.get(1).name) != null || graph.getEdgeBetween(chosenNodes.get(1).name, chosenNodes.get(0).name) != null/*chosenNodes.get(1).name == connectedCheck.get(chosenNodes.get(0).name) || chosenNodes.get(0).name == connectedCheck.get(chosenNodes.get(1).name)*/){
            textError.setText("Error: Already connected");
            return;
          } 
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
            textError.setText("");
            
            conContain.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            conContain.showAndWait();           
          
        });

        removeConnections.setOnAction(
          ev -> {  
          if(graph.getEdgeBetween(chosenNodes.get(0).name, chosenNodes.get(1).name) != null || graph.getEdgeBetween(chosenNodes.get(1).name, chosenNodes.get(0).name) != null){
          //  if()
          //edges.remove(graph.getEdgeBetween(chosenNodes.get(0).name, chosenNodes.get(1).name));
          center.getChildren().remove(edgesTest.get(graph.getEdgeBetween(chosenNodes.get(0).name, chosenNodes.get(1).name).getName()));
          edges.remove(edgesTest.get(graph.getEdgeBetween(chosenNodes.get(0).name, chosenNodes.get(1).name).getName()));
          edgesTest.remove((graph.getEdgeBetween(chosenNodes.get(0).name, chosenNodes.get(1).name).getName()));
           graph.disconnect(chosenNodes.get(0).name, chosenNodes.get(1).name);
           chosenNodes.get(0).sprite.setFill(Color.RED);
           chosenNodes.get(1).sprite.setFill(Color.RED);
           //chosenNodes.get(0).edgeSet.remove();
           // connectedCheck.remove();
            chosenNodes.clear();
            textError.setText("");
            return;
          } 
             textError.setText("Error: Not Connected");         
         });
          

        removeNodeButton.setOnAction(
          ev -> {
            String name = aText.getText().toUpperCase();   //byta nameField till aText
             if(graph.hasNode(name)){
                if(graph.getEdgesFrom(name) != null){
         // center.getChildren().remove(edgesTest.get(graph.getEdgesFrom(name)));
                  graph.getEdgesFrom(name);
                  for(Edge<String> e : graph.getEdgesFrom(name)){
                    center.getChildren().remove(edgesTest.get(e.getName()));
                    edges.remove(edgesTest.get(e.getName()));
                    edgesTest.remove(e.getName());
                    //graph.disconnect(name, hej.getDestination());
                  }
                } 
            graph.remove(name);
            center.getChildren().remove(nameList.get(name));
            nameList.remove(name);

            //center.getChildren().remove();
            //nodeText.setText("name");

            textError.setText("");
          }else {
            textError.setText("Error: Location doesn't exist");
            System.out.println("den INTE funkar?");
          }
            
            
            
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
                Circle sprite = new Circle(x, y, 25, Color.RED);
                
                

                Text nodText = new Text();
                if(name.length() > 5){
                  
                  nodText = new Text(name.substring(0,5) + "...");
                }else {

                  nodText = new Text(name);
                }
                nodText.setFill(Color.WHITE);
                StackPane combinedNode = new StackPane(sprite, nodText);
                Node node = new Node(name, x, y, sprite);
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

            class ColorClickHandler implements EventHandler<MouseEvent> {
              @Override
              public void handle(MouseEvent event){
                  event.getSource();
                    System.out.print("clicked");
                   Circle circle = (Circle) event.getSource();
                   circle.setFill(Color.GREEN);
            
                
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
