package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BFS {

    public static <T> HashMap<T,T> bfs(Graph<T> g, T root) {
        HashSet<T> found = new HashSet<>();
        LinkedList<Edge<T>> toSearch = new LinkedList<>();
        HashMap<T,T> parents = new HashMap<>();
        parents.put(root, null);
        update(g,found,toSearch,root);

        while (!toSearch.isEmpty()) {
            Edge<T> e = toSearch.removeFirst();
            T foundNode = foundNode(e, found);
            T newNode = e.getOtherNode(foundNode);

            if (found.contains(newNode)) continue;

            parents.put(newNode,foundNode);
            update(g, found, toSearch, newNode);
        }
        return parents;
    }

    private static <T> T foundNode(Edge<T> e, HashSet<T> found) {
        if (found.contains(e.a)) return e.a;
        if (found.contains(e.b)) return e.b;
        throw new IllegalArgumentException("e should have an endpoint in found");
    }


    private static <T> void update(Graph<T> g, HashSet<T> found, LinkedList<Edge<T>> toSearch, T node) {
        found.add(node);
        for (Edge<T> edge : g.adjacentEdges(node)) {
            if (found.contains(edge.a) && found.contains(edge.b)) {
                continue;
            }
            toSearch.addLast(edge);
        }
    }
}