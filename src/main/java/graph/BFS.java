package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BFS {

    public static <T> HashMap<T,T> bfs(Graph<T> g, T root) {
        HashSet<T> found = new HashSet<>();
        LinkedList<Edge<T>> toSearch = new LinkedList<>();

        HashMap<T,T> connectedNodes = new HashMap<>();
        connectedNodes.put(root, null);

        update(g,found,toSearch,root);

        while (!toSearch.isEmpty()) {
            Edge<T> e = toSearch.removeFirst();
            T foundNode = foundNode(e, found);
            T newNode = e.getOtherNode(foundNode);

            if (found.contains(newNode)) continue;

            connectedNodes.put(newNode,foundNode);
            update(g, found, toSearch, newNode);
        }
        return connectedNodes;
    }

    /**
     * Checks if the endpoint of an edge is in a set, if so return that endpoint (node)
     * @param edge to check its endpoints
     * @param found set with nodes to check with
     * @return the node which is in the set
     */
    private static <T> T foundNode(Edge<T> edge, HashSet<T> found) {
        if (found.contains(edge.a)) return edge.a;
        if (found.contains(edge.b)) return edge.b;
        throw new IllegalArgumentException("Edge should always have an endpoint in the set");
    }

    /**
     * Adds a node to a set. And adds all the adjacent edges of the given node to a list,
     * only if the node of the adjacent edges is not in the set.
     * @param g the graph from which the node is from
     * @param found the set from wich to add the node
     * @param toSearch the list to add the adjacent edges of the node
     * @param newNode the node
     */
    private static <T> void update(Graph<T> g, HashSet<T> found, LinkedList<Edge<T>> toSearch, T newNode) {
        found.add(newNode);
        for (Edge<T> edge : g.adjacentEdges(newNode)) {
            if (found.contains(edge.a) && found.contains(edge.b)) {
                continue;
            }
            toSearch.addLast(edge);
        }
    }
}