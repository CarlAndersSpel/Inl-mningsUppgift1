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
    } else {
      graph.remove(node);  //säker ett dåligt sätt att göra.
    }
    List<Edge<T>> kanter = graph.get(node); 
    // gå igenom alla kanter
    
    //loppa igenom alla noder i "node" sen kan man ta bort den.

      // Gå till destinationen av kanten och ta bort kanten till node
    // ta bort alla kanter
    // ta bort nod allt i remove.

    //graf.remove(node);  slut mål
   
    //throw new UnsupportedOperationException("Unimplemented method 'remove'");
  }

  @Override
  public boolean hasNode(T node) {
    return graph.containsKey(node);  //kollar om mappen har en nyckel och returnerar true om det finns annars false

    
    //throw new UnsupportedOperationException("Unimplemented method 'hasNode'");
  }

  @Override
  public void connect(T node1, T node2, String name, int weight) {
    this.add(node1); // guarantee that cities.get(a) returns a set
    this.add(node2);
//skapa en klass som implementerar gränsnittet edge.
    List<Edge<T>> aEdges = graph.get(node1);
    List<Edge<T>> bEdges = graph.get(node2);

    aEdges.add(new Edge<T>(node2, name, weight));   //vet inte vad som gör att det inte funkar
    bEdges.add(new Edge<T>(node1, name, weight));
    
    //throw new UnsupportedOperationException("Unimplemented method 'connect'");
  }

  @Override
  public void disconnect(T node1, T node2) {  //alla metoder som ska skrivas in.
    throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
  }

  @Override
  public void setConnectionWeight(T node1, T node2, int weight) {
    throw new UnsupportedOperationException("Unimplemented method 'setConnectionWeight'");
  }

  @Override
  public Set<T> getNodes() {
  //ta fram alla nycklar från grapth där alla noder är
   return graph.keySet();
    //throw new UnsupportedOperationException("Unimplemented method 'getNodes'");
  }

  @Override
  public Collection<Edge<T>> getEdgesFrom(T node) {
    throw new UnsupportedOperationException("Unimplemented method 'getEdgesFrom'");
  }

  @Override
  public Edge<T> getEdgeBetween(T node1, T node2) {
    throw new UnsupportedOperationException("Unimplemented method 'getEdgeBetween'");
  }

  @Override
  public Iterator<T> iterator() {
    throw new UnsupportedOperationException("Unimplemented method 'iterator'");
  }
}

