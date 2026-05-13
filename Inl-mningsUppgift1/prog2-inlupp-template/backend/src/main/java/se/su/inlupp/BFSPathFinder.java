package se.su.inlupp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.lang.Iterable;
import java.util.Map;

public class BFSPathFinder<T> implements PathFinder<T> {

    /*final Map<T, Boolean> isVisited = new HashMap<>();
    T finalPath;
    MyPath<T> path = new MyPath<>(finalPath);
      boolean found = false;
      LinkedList<T> hello = new LinkedList<>();*/
        final Map<T, Boolean> isVisited = new HashMap<>();
        MyPath<T> path;
        boolean found = false;

      @Override
      public Path<T> findPath(Graph<T> graph, T from, T to) {

    //MyPath<T> path = new MyPath<>(finalPath);
    //try {

      for ( T node : graph.getNodes()){
        isVisited.put(node, false);
      }
    
      path = new MyPath<>(from);
      bfs(graph, from, to);
      if(!isVisited.get(to))
      {
      return null;
      }
      return path;


      /*MyPath<T> path = new MyPath<T>(from);

       T current  = to;
      while(current != null || !current.equals(from) ){
        T next = connections.get(current);

        Edge<T> edge = graph.getEdgeBetween(next, current);
        path.addEdge(edge);
        current = next;
      } 
      //T next = new getDestination<>(); 
      // for ( Edge<T> edge : graph.getEdgesFrom(to))
      
      return path; */
    }
    
    private List<T> bfs(Graph<T> graph, T from, T to) {
        List<T> edges = new LinkedList<>();
    T finalPath;
    if( from.equals(to)) {
       return null;
    }
  //List<T> edges = new LinkedList<>();
    Map<T, T> connections = new HashMap<>();
    connections.put(from, null);
    LinkedList<T> queue = new LinkedList<>();  //an arraylist istället kanske
    List<Edge<T>> finalQueue;
    

   // path = new MyPath<>(from);
    //bfs();
    queue.add(from);
    while (!queue.isEmpty() || !connections.containsKey(to)){

     // T current = queue.poll();//tar själva kön och lägger in och stryker bort.
      finalPath = queue.poll();
      //edges = finalQueue.remove();    //whitch methods are just in list in java to remove elements.
      //Path<T> path = queue.poll();
   for(Edge<T> edge : graph.getEdgesFrom(finalPath)) {
         T next = edge.getDestination();
         if(!next.equals(to) || !connections.containsKey(edge)){
           connections.put(next, finalPath);
           queue.add(next);
          } else {

            queue.add(finalPath);
            //return new Path<> finalPath;
          }
        }
        queue.remove(finalPath);
        return null;
      }
    return queue;



        
      }

    //return null;

      //LinkedList<Edge<T>> edges = new LinkedList<>();
      //LinkedList<T> nodes = new LinkedList<>();
    //throw new UnsupportedOperationException("Unimplemented method 'findPath'");
  }

  /*private void bfs(ArrayList<ArrayList<Integer>> adj){
  int V = adj.size();
  boolean[] visited = new boolean[V];
  ArrayList<Integer> res = new ArrayList<>();

  int src = 0;
  Queue<Integer> q = new LinkedList<>();
  visited[src] = true;
  q.add(src);

  while (!q.isEmpty()) {
    int curr = q.poll();
    res.add(curr);

     for ( int x : adj.get(curr)) {
      if (!visited[x]) {
        visited[x] = true;
        q.add(x);
      }
     }


  }
   //return res;


   //return new PathClass<T>(from, to, edges, nodes);
   //} catch (nosuchelementexception e ) { return null;}
}*/





