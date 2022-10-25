package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BFS {

    //m - number of edges in the graph
    //n - number of nodes in the graph

    /**
     * Performs a breadth first search on a graph starting at a given vertex.
     * Modified bfs from lecture 11.
     * @param g    the graph to search
     * @param root the vertex to root the search at
     * @param <V>  the type of the vertices in the graph
     * @return a map of each vertex to its parent in the search tree
     */
    public static <V> HashMap<V, V> bfs(Graph<V> g, V root) { //O(m)
        HashMap<V, V> bfs = new HashMap<>(); //O(1)
        LinkedList<V> toSearch = new LinkedList<>(); //O(1)
        HashSet<V> found = new HashSet<>(); //O(1)

        found.add(root);  //O(1)
        bfs.put(root, null); //O(1)
        addNeighbours(root, g, toSearch, bfs); //O(n) if node is neighbour of all nodes

        while (!toSearch.isEmpty()) { //O(m) -> while-loop -> for each node we get the sum of its degree.
            V next = toSearch.removeLast(); //O(1)
            if (found.contains(next)) //O(1) expected
                continue;
            //iterations past here once of each node, O(n)
            found.add(next); //O(1)
            addNeighbours(next, g, toSearch, bfs); //O(degree(next))
        }
        return bfs;
    }
    // We have a connected graph, where the while-loop goes through each degree, hence O(m)

    private static <V > void addNeighbours (V node, Graph <V> g, LinkedList<V> toSearch, HashMap<V,V> bfs) {
        for (V neighbour : g.neighbours(node)) { //O(degree)
            if (!bfs.containsKey(neighbour)) {
                bfs.put(neighbour, node);
                toSearch.addFirst(neighbour);
            }
        }
    }

}