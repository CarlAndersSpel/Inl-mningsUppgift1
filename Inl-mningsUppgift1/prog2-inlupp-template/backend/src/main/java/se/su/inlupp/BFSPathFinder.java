package se.su.inlupp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.lang.Iterable;
import java.util.Map;

public class BFSPathFinder<T> implements PathFinder<T> {

  Map<T, T> connections = new HashMap<>();
  Path<T> path;
  boolean found = false;

  @Override
  public Path<T> findPath(Graph<T> graph, T from, T to) {

    if (!graph.getNodes().contains(from) || !graph.getNodes().contains(to)) {
      return null;
    }
    path = bfs(graph, from, to);
    // bfs(graph, from, to);
    if (!connections.containsKey(to)) {
      return null;
    }
    return path;

  }

  private Path<T> bfs(Graph<T> graph, T from, T to) {
    T current;
    if (from.equals(to)) {
      return null;
    }

    connections.put(from, null);
    LinkedList<T> queue = new LinkedList<>(); // an arraylist istället kanske
    queue.add(from);
    while (!queue.isEmpty() && !connections.containsKey(to)) {

      current = queue.poll();

      for (Edge<T> edge : graph.getEdgesFrom(current)) {
        T next = edge.getDestination();
        if (/* !next.equals(to) || */ !connections.containsKey(next)) {
          connections.put(next, current);
          if (next.equals(to)) { // om man hittar noden som gick så bara gör så bryts while loppen
            queue.clear();
            break;
          }
          queue.add(next);
        }
      }
      
    }
    if (!connections.containsKey(to)) {  //after entirer proccess check if the goal is reached
      return null;
    }
    MyPath<T> path = new MyPath<>(from);

    T cur = to;
    while (cur != null && !cur.equals(from)) {
      T next = connections.get(cur);
      Edge<T> edge = graph.getEdgeBetween(next, cur);
      path.addFirst(edge);
      cur = next;
    }
    return path;

  }

}




