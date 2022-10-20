package student;

import java.util.*;

import graph.*;

public class ProblemSolver implements IProblem {


	@Override
	public <V, E extends Comparable<E>> List<Edge<V>> mst(WeightedGraph<V, E> g) {
		ArrayList<Edge<V>> mstEdges = new ArrayList<>(g.numVertices());
		if (g.numEdges() < 1) {return mstEdges;}

		HashSet<V> found = new HashSet<>(g.numVertices());
		PriorityQueue<Edge<V>> sortedEdges = new PriorityQueue<>(g);
		V start = g.vertices().iterator().next();
		found.add(start);

		for (Edge<V> edge : g.adjacentEdges(start)) {
			sortedEdges.add(edge);
		}

		while(!sortedEdges.isEmpty()) {
			Edge<V> e = sortedEdges.poll();

			if (found.contains(e.a) && found.contains(e.b)) continue;

			mstEdges.add(e);

			isVisited(g, e.a, found, sortedEdges);
			isVisited(g, e.b, found, sortedEdges);

		}
		return mstEdges;
	}

	private  <V, E extends Comparable<E>> void isVisited(WeightedGraph<V, E> g, V node, HashSet<V> found, PriorityQueue<Edge<V>> sortedEdges) {
		found.add(node);
		for (Edge<V> edge : g.adjacentEdges(node)) {
			sortedEdges.add(edge);
		}
	}

		@Override
	public <V> V lca(Graph<V> g, V root, V u, V v) {
		new BFS();
		HashMap<V, V> breadthFirstSearch = BFS.parents(g,root);
		System.out.println(breadthFirstSearch);

		ArrayList<V> pathToU = path(u, breadthFirstSearch);
		ArrayList<V> pathToV = path(v, breadthFirstSearch);


		HashSet<V> SetPathU = new HashSet<>(pathToU);
		for (V node : pathToV) {
			if (SetPathU.contains(node))
				return node;
		}
		throw new IllegalArgumentException("No LCA found");
		}


	private <V> ArrayList<V> path(V parent, HashMap<V,V> breadthFirstSearch) {
		ArrayList<V> path = new ArrayList<>();
		while (parent != null) {
			path.add(parent);
			parent = breadthFirstSearch.get(parent);
		}
		System.out.println(path);
		return path;
	}







	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {
		throw new UnsupportedOperationException();
		// Task 3
		// TODO: Implement me :)
	}

}
