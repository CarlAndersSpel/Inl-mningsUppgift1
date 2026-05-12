package se.su.inlupp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.HashMap;

public class ListGraph<T> implements Graph<T> {

  private final Map<T, List<Edge<T>>> graph = new HashMap<>();

  @Override
  public void add(T node) {  //T byts mot string integer etc... man skapar en instans av listgraph
    graph.putIfAbsent(node, new ArrayList<>()); //Om nyckeln inte finns så läggs den till.
    //throw new UnsupportedOperationException("Unimplemented method 'add'");
  }

  @Override
  public void remove(T node) {
    if(!hasNode(node)){  //om noden är false skickas meddelande
      throw new NoSuchElementException("Noden finns inte");
    } 

    List<Edge<T>> edges = graph.get(node); 

      for ( Edge<T> e : edges){
        disconnect(node, e.getDestination()); //alla noder jag(node) har en kant till disconnectar(alltså ta bort kanter och kantanen från andra sidan tas också bort).
      }
         graph.remove(node);
    }
    // gå igenom alla kanter
    
    //loppa igenom alla noder i "node" sen kan man ta bort den.

      // Gå till destinationen av kanten och ta bort kanten till node
    // ta bort alla kanter
    // ta bort nod allt i remove.

    //graf.remove(node);  slut mål
   
    //throw new UnsupportedOperationException("Unimplemented method 'remove'");
  

  @Override
  public boolean hasNode(T node) {
    return graph.containsKey(node);  //kollar om mappen har en nyckel och returnerar true om det finns annars false

    
    //throw new UnsupportedOperationException("Unimplemented method 'hasNode'");
  }

  @Override
  public void connect(T node1, T node2, String name, int weight) {
      
  if(graph.get(node1) == null || graph.get(node2) == null)
    {
      throw new NoSuchElementException("not[null]");
  }
    if(getEdgeBetween(node1, node2) != null || getEdgeBetween(node2, node1) != null)
    {
      throw new IllegalStateException("not[null]");
  }

    if (weight < 0){
      throw new IllegalArgumentException();
    }

    this.add(node1); 
    this.add(node2);
//skapa en klass som implementerar gränsnittet edge.
    List<Edge<T>> aEdges = graph.get(node1);
    List<Edge<T>> bEdges = graph.get(node2);

    aEdges.add(new MyEdge<T>(node2, name, weight));   //vet inte vad som gör att det inte funkar
    bEdges.add(new MyEdge<T>(node1, name, weight));
    
    
    //throw new UnsupportedOperationException("Unimplemented method 'connect'");
  }

  @Override
  public void disconnect(T node1, T node2) {  //alla metoder som ska skrivas in.

    if(graph.get(node1) == null || graph.get(node2) == null)
    {
      throw new NoSuchElementException("not[null]");
  }
      if(getEdgeBetween(node1, node2) == null || getEdgeBetween(node2, node1) == null)
    {
      throw new IllegalStateException("not[null]");
  }
    List<Edge<T>> aEdges = graph.get(node1);
    List<Edge<T>> bEdges = graph.get(node2);
    
    for ( int i = 0; i < aEdges.size(); i++)   //disconnect i remove 
    {
        if(aEdges.get(i).getDestination().equals(node2)){
              aEdges.remove(i);
              i--;
        }
    }
    for ( int i = 0; i < bEdges.size(); i++)
    {
        if(bEdges.get(i).getDestination().equals(node1)){
              bEdges.remove(i);
              i--;
        }
    }
    

    //throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
  }

  @Override
  public void setConnectionWeight(T node1, T node2, int weight) {

    /*if (weight < 0){
      throw new IllegalArgumentException();
    }*/

    if(getEdgeBetween(node1, node2) == null || getEdgeBetween(node2, node1) == null)
    {
      throw new NoSuchElementException();
    } 
  //lägga till den nya vikten i graph
 
    /*List<Edge<T>> aEdges = graph.get(node1);
    List<Edge<T>> bEdges = graph.get(node2);
    List<Edge<T>> wEdges = graph.get(weight);*/
    
    getEdgeBetween(node1, node2).setWeight(weight);
    getEdgeBetween(node2, node1).setWeight(weight);
     

   
    //aEdges.add(new MyEdge<T>(node2, weight));   //vet inte vad som gör att det inte funkar
   // bEdges.add(new MyEdge<T>(node1,weight));

    //throw new UnsupportedOperationException("Unimplemented method 'setConnectionWeight'");
  }

  @Override
  public Set<T> getNodes() {
  //ta fram alla nycklar från grapth där alla noder är
   return graph.keySet();
    //throw new UnsupportedOperationException("Unimplemented method 'getNodes'");
  }

  @Override
  public Collection<Edge<T>> getEdgesFrom(T node) {
    //graph.
    List<Edge<T>> theEdge = graph.get(node);
    if(graph.get(node) == null){
      throw new NoSuchElementException("not[null]");
    }
        return theEdge;
  }

  @Override
  public Edge<T> getEdgeBetween(T node1, T node2) {
    List<Edge<T>> edgeFromNode1 = graph.get(node1);
    if(graph.get(node1) == null || graph.get(node2) == null)
      {
        throw new NoSuchElementException("not[null]");
    }
    for ( Edge<T> e : edgeFromNode1){
      if(e.getDestination().equals(node2)){
        return e;
      }
    }
    return null;
    //throw new UnsupportedOperationException("Unimplemented method 'getEdgeBetween'");
  }

  @Override
  public Iterator<T> iterator() {
    Iterator<T> it = graph.keySet().iterator();

    return it;
    //throw new UnsupportedOperationException("Unimplemented method 'iterator'");
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (T node : getNodes())
    {
    sb.append(node);
    for (Edge<T> edge : getEdgesFrom(node)){
      sb.append(edge);
      //sb.append("\n");
    }
  }

  return sb.toString();

  }



}

