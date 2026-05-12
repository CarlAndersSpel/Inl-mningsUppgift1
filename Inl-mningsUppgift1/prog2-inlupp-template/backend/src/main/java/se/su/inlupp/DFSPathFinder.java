package se.su.inlupp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.HashMap;

public class DFSPathFinder<T> implements PathFinder<T> {
  
  final Map<T, Boolean> isVisited = new HashMap<>();
  MyPath<T> path;
  boolean found = false;

  @Override
  public Path<T> findPath(Graph<T> graph, T from, T to) {
      for ( T node : graph.getNodes()){
        isVisited.put(node, false);
      }
    
    path = new MyPath<>(from);
    dfs(graph, from, to);
    if(!isVisited.get(to))
    {
      return null;
    }
    return path;
    //throw new UnsupportedOperationException("Unimplemented method 'findPath'");
  }
  
  private void dfs(Graph<T> graph, T node, T to){
    isVisited.put(node, true);
    if (node.equals(to)) {
      found = true;
      return;
    }
  for ( Edge<T> edge : graph.getEdgesFrom(node)){
    if(!isVisited.get(edge.getDestination())){  //om en granne till, en nod inte är besökt än. så anropaas den destinationen. i if satsen.
      path.addEdge(edge);  //jag är i den här noden om grannen inte är besökt än så jag kommer till grannen genom den här noden.
      dfs(graph, edge.getDestination(), to);   //anropar sig själv vilket skapar rekursion
      if (found) return;
      path.removeLast();  //när rekursionen händer så går den igenom alla noder den bygger en "path" samtidigt som rekursionen händer och om den har inte lyckats hitta "to" noden så går den tillbaka och tar bort edges som inte leder till noden som vi letar efter.
    }
  }
}


 /*  public boolean isNamePending(T node, T next, T to) {
    for( ) //hitta edgerna 
                        //gå igenom vägen
    isNamePending(next, to);
  }*/
}

