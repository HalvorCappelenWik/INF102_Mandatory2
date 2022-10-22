package student;

import java.util.*;

import graph.*;

public class ProblemSolver implements IProblem {


	@Override
	public <V, E extends Comparable<E>> List<Edge<V>> mst(WeightedGraph<V, E> g) {
		ArrayList<Edge<V>> mstEdges = new ArrayList<>(g.numVertices());
		if (g.numEdges() < 1) {return mstEdges;}

		HashSet<V> found = new HashSet<>(g.numVertices());
		PriorityQueue<Edge<V>> toSearch = new PriorityQueue<>(g);
		V start = g.vertices().iterator().next();
		found.add(start);

		for (Edge<V> edge : g.adjacentEdges(start)) {
			toSearch.add(edge);
		}

		while(!toSearch.isEmpty()) {
			Edge<V> next = toSearch.poll();

			if (found.contains(next.a) && found.contains(next.b)) continue;

			mstEdges.add(next);

			isVisited(g, next.a, found, toSearch);
			isVisited(g, next.b, found, toSearch);

		}
		return mstEdges;
	}

	private  <V, E extends Comparable<E>> void isVisited(WeightedGraph<V, E> g, V node, HashSet<V> found, PriorityQueue<Edge<V>> toSearch) {
		found.add(node);
		for (Edge<V> edge : g.adjacentEdges(node)) {
			toSearch.add(edge);
		}
	}

		@Override
	public <V> V lca(Graph<V> g, V root, V u, V v) {

		new BFS();
		HashMap<V, V> bfs = BFS.bfs(g,root);

		ArrayList<V> pathToU = path(u, bfs);
		ArrayList<V> pathToV = path(v, bfs);

		for (V node : pathToV) {
			if (pathToU.contains(node))
				return node;
		}
		throw new IllegalArgumentException("No LCA found");
		}

	private <V> ArrayList<V> path(V node, HashMap<V,V> breadthFirstSearch) {
		ArrayList<V> path = new ArrayList<>();
		while (node != null) {
			path.add(node);
			node = breadthFirstSearch.get(node);
		}
		return path;
	}




	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {
		HashMap<V,Integer> count = new HashMap<>();
		HashMap<V,ArrayList<V>> neighbours = new HashMap<>();
		HashSet<V> visited = new HashSet<>();

		size(g, root, count, visited, neighbours);

		ArrayList<V> leaves = new ArrayList<>();
		ArrayList<V> nodes = new ArrayList<>();

		for (V n : g.neighbours(root)){
			nodes.add(n);
		}

		HashSet<V> subTrees = new HashSet<>();
		Comparator<V> compareSize = Comparator.comparingInt(count::get);
		nodes.sort(Collections.reverseOrder(compareSize));
		subTrees.add(nodes.get(0));

		if (g.degree(root) > 1){
			subTrees.add(nodes.get(1));
		} else {
			leaves.add(root);
		}


		for (V rootNode : subTrees){
			while (g.degree(rootNode) != 1){
				int i = 0;
				V newNode = null;

				for (V neighbor : neighbours.get(rootNode)){
					if (count.get(neighbor) > i){
						i = count.get(neighbor);
						newNode = neighbor;
					}
				}
				rootNode = newNode;

			}
			leaves.add(rootNode);
		}
		return new Edge<>(leaves.get(0),leaves.get(1));
	}


	/**
	 * modified size-method from PowerOutage class in Main.Test.problemsolver package
	 * @param g
	 * @param node
	 * @param count
	 * @param visited
	 * @param neighbours
	 * @return
	 * @param <V>
	 */
	public <V> int size(Graph<V> g, V node, HashMap <V, Integer> count, HashSet <V> visited, HashMap <V, ArrayList<V>> neighbours ){
		int counter = 1;
		visited.add(node);
		ArrayList<V> n = new ArrayList<>();
		for(V children : g.neighbours(node)){
			if(!visited.contains(children)){
				n.add(children);
				counter += size(g, children, count, visited, neighbours);
			}
		}
		neighbours.put(node,n);
		count.put(node, counter);
		return counter;
	}
}
