package se.su.inlupp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyPath<T> implements Path<T>{

  private T startNode;
  private final List<Edge<T>> edges = new ArrayList<>();

  public MyPath(T startNode) {
    this.startNode = startNode;
  }

  public void addEdge(Edge<T> edge) {
    edges.add(edge); //lägger till edge som är en väg
  }

  public void removeLast() {
    edges.removeLast();
  }

  /* public Iterator<T> iterator() {
    Iterator<T> it = graph.keySet().iterator(); //try to extrakt informaation and export it in a different method. thru a middle method.

    return it;
  }*/

@Override
public T getStart(){
    return startNode;  //returnerar startnode 
}
    
  public void addFirst(Edge<T> edge) {
    edges.addFirst(edge); //lägger till edge som är en väg
  }

@Override
public T getEnd(){
    return edges.get(edges.size()-1).getDestination(); //den tar den sista noden i Path<T> listan
}

@Override
public int getTotalWeight(){
  //edges.get(edges.setConnectionWeight());

int sum = 0;
  for ( Edge<T> edge : edges ){
    edge.getWeight();
    sum += edge.getWeight();
  }
    return sum;
}

@Override
public List<Edge<T>> getEdges(){
    return edges;
}

@Override
public List<T> getNodes(){
  //ListGraph.getNodes();
  //graph.getNodes();
  
    return null;
}

@Override
public Iterator<Edge<T>> iterator(){

    return null;
}

@Override
public String toString(){
    return null;
}
}
