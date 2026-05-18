package se.su.inlupp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Map;

public class BFSPathFinder<T> implements PathFinder<T> {

    final Map<T, Boolean> isVisited = new HashMap<>();
      MyPath<T> path;
  boolean found = false;

  @Override
  public Path<T> findPath(Graph<T> graph, T from, T to) {
     Map<T, T> connections = new HashMap<>();
    connections.put(from, null);
    LinkedList<T> queue = new LinkedList<>();  //an arraylist istället kanske

    path = new MyPath<>(from);
    //bfs();
    queue.add(from);
    return null;

    //throw new UnsupportedOperationException("Unimplemented method 'findPath'");
  }

  private void bfs(ArrayList<ArrayList<Integer>> adj){
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
}
}

