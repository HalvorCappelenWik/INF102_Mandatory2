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
		throw new UnsupportedOperationException();
		// Task 2
		// TODO: Implement me :)
	}

	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {
		throw new UnsupportedOperationException();
		// Task 3
		// TODO: Implement me :)
	}

}
