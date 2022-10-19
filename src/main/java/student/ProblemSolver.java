package student;

import java.util.*;

import graph.*;

public class ProblemSolver implements IProblem {



	@Override
	public <V, E extends Comparable<E>> List<Edge<V>> mst(WeightedGraph<V, E> g) {
		V start = g.vertices().iterator().next();
		ArrayList<Edge<V>> shortestEdges = new ArrayList<>(g.numVertices());

		if (g.numEdges() < 1) {return shortestEdges;}

		HashSet<V> found = new HashSet<>(g.numVertices());
		PriorityQueue<Edge<V>> sortedEdges = new PriorityQueue<>(g);

		for (Edge<V> edge : g.adjacentEdges(start)) {
			sortedEdges.add(edge);
		}
		found.add(start);

		while(!sortedEdges.isEmpty()) {
			Edge<V> e = sortedEdges.poll();

			if (found.contains(e.a) && found.contains(e.b)) continue;

			shortestEdges.add(e);

			checkNeighbours(g, e.a, found, sortedEdges);
			checkNeighbours(g, e.b, found, sortedEdges);

		}
		return shortestEdges;
	}

	private  <V, E extends Comparable<E>> void checkNeighbours(WeightedGraph<V, E> g, V node, HashSet<V> found, PriorityQueue<Edge<V>> sortedEdges) {
		if (!found.contains(node)) {
			found.add(node);
			for (Edge<V> edge : g.adjacentEdges(node)) {
				sortedEdges.add(edge);
			}
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
