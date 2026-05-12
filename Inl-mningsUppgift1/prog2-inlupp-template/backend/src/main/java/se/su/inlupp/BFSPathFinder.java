package se.su.inlupp;
import java.util.*;

public class BFSPathFinder<T> implements PathFinder<T> {

    final Map<T, Boolean> isVisited = new HashMap<>();
      MyPath<T> path;
  boolean found = false;

  @Override
  public Path<T> findPath(Graph<T> graph, T from, T to) {

    if (!graph.hasNode(from) || !graph.hasNode(to)) {
        return null;
    }

    Queue<T> queue = new LinkedList<>();

    Map<T, T> connections = new HashMap<>();

    Set<T> visited = new HashSet<>();

    queue.add(from);
    visited.add(from);

    boolean found = false;

    while (!queue.isEmpty() && !found) {

        T current = queue.poll();

        for (Edge<T> edge : graph.getEdgesFrom(current)) {

            T neighbor = edge.getDestination();

            if (!visited.contains(neighbor)) {

                visited.add(neighbor);

                connections.put(neighbor, current);

                queue.add(neighbor);

                if (neighbor.equals(to)) {
                    found = true;
                    break;
                }
            }
        }
    }

    if (!found && !from.equals(to)) {
        return null;
    }

    List<T> nodes = new ArrayList<>();

    T current = to;

    nodes.add(current);

    while (!current.equals(from)) {
        current = connections.get(current);

        if (current == null) {
            return null;
        }

        nodes.add(current);
    }

    Collections.reverse(nodes);

    MyPath<T> path = new MyPath<>(from);

    for (int i = 0; i < nodes.size() - 1; i++) {

        T a = nodes.get(i);
        T b = nodes.get(i + 1);

        Edge<T> edge = graph.getEdgeBetween(a, b);

        path.addEdge(edge);
    }

    return path;
}

  static void bfs(List<List<Integer> > graph, int S,
    List<Integer> par, List<Integer> dist)
{

Queue<Integer> q = new LinkedList<>();

dist.set(S, 0);

q.add(S);


while (!q.isEmpty()) {

int node = q.poll();


for (int neighbor : graph.get(node)) {

if (dist.get(neighbor)
    == Integer.MAX_VALUE) {

    par.set(neighbor, node);

    dist.set(neighbor, dist.get(node) + 1);

    q.add(neighbor);
}
}
}
}

static List<Integer> shortestPath(List<List<Integer>> graph,
  int S, int D, int V)
{

List<Integer> par =
new ArrayList<>(Collections.nCopies(V, -1));

List<Integer> dist =
new ArrayList<>(Collections.nCopies(V, Integer.MAX_VALUE));


bfs(graph, S, par, dist);


if (dist.get(D) == Integer.MAX_VALUE) {
return new ArrayList<>(); 
}


List<Integer> path = new ArrayList<>();

int currentNode = D;

while (currentNode != -1) {
path.add(currentNode);
currentNode = par.get(currentNode);
}


Collections.reverse(path);

return path;
}



}
