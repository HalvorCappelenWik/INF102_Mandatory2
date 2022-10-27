package graph;

import graph.Graph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BFS {

    /**
     * Performs a breadth first search on a graph starting at a given vertex.
     * Modified bfs from lecture 11.
     * @param g    the graph to search
     * @param root the vertex to root the search at
     * @param <V>  the type of the vertices in the graph
     * @return a map of each vertex to its parent in the search tree
     */
    public static <V> HashMap<V, V> bfs(Graph<V> g, V root) { //O(m)
        HashMap<V, V> bfs = new HashMap<>();
        LinkedList<V> toSearch = new LinkedList<>();
        HashSet<V> found = new HashSet<>();

        found.add(root);
        bfs.put(root, null);
        addNeighbours(root, g, toSearch, bfs); //O(n) if node is neighbour of all nodes


        while (!toSearch.isEmpty()) { //O(m)
            V next = toSearch.removeLast();
            if (found.contains(next)) //O(1) expected
                continue;
            //iterations past here once of each node
            found.add(next); //O(1)
            addNeighbours(next, g, toSearch, bfs); // this will happen O(n) times takes O(degree(node) time hence is O(m)
        }
        return bfs;
    }

    private static <V > void addNeighbours (V node, Graph <V> g, LinkedList<V> toSearch, HashMap<V,V> bfs) {
        for (V neighbour : g.neighbours(node)) { //O(degree(node))
            if (!bfs.containsKey(neighbour)) {
                bfs.put(neighbour, node);
                toSearch.addFirst(neighbour);
            }
        }
    }
}