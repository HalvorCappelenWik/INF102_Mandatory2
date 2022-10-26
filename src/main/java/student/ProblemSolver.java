package student;

import java.util.*;

import graph.*;

public class ProblemSolver implements IProblem {

	//m - number of edges in the graph
	//n - number of nodes in the graph

	@Override
	public <V, E extends Comparable<E>> List<Edge<V>> mst(WeightedGraph<V, E> g) { //O(n + log m + (m log m)) = O(m log m)
		ArrayList<Edge<V>> mstEdges = new ArrayList<>(g.numVertices()); //O(1)


		if (g.numEdges() < 1) {return mstEdges;} //O(1)
		HashSet<V> found = new HashSet<>(g.numVertices()); //O(1)
		PriorityQueue<Edge<V>> toSearch = new PriorityQueue<>(g); //O(n)
		V start = g.getFirstNode(); //O(1)
		found.add(start); //O(1)


		for (Edge<V> edge : g.adjacentEdges(start)) { //O(degree)
			toSearch.add(edge); //O(log m)
		}


		while(!toSearch.isEmpty()) { //O(m)
			Edge<V> next = toSearch.poll(); //O(log m)
			if (found.contains(next.a) && found.contains(next.b)) continue; //O(1)


			mstEdges.add(next); //O(1)


			isVisited(g, next.a, found, toSearch); //O(log m)
			isVisited(g, next.b, found, toSearch); //O(log m)

		}
		return mstEdges;
	}


	/**
	 * Adds a node to a list.
	 * Adds all the edges which has endpoint equal to the given node in a list.
	 * @param g the graph from which the node is from
	 * @param node the node
	 * @param found the list to add the node
	 * @param toSearch the list to add the edges with equal endpoint as the node
	 */
	private  <V, E extends Comparable<E>> void isVisited(WeightedGraph<V, E> g, V node, HashSet<V> found, PriorityQueue<Edge<V>> toSearch) { //O(log m)
		found.add(node); //O(1)
		for (Edge<V> edge : g.adjacentEdges(node)) { //O(degree)
			toSearch.add(edge); //O(log m)
		}
	}

		@Override
	public <V> V lca(Graph<V> g, V root, V u, V v) { //O(n+m)

		new BFS();
		HashMap<V, V> bfs = BFS.bfs(g,root); //O(m)

		LinkedList<V> pathToU = path(u, bfs); //O(n)
		LinkedList<V> pathToV = path(v, bfs); //O(n)


		for (V node : pathToV) { //O(n)
			if (new HashSet<>(pathToU).contains(node))
				return node;
		}
		throw new IllegalArgumentException("No LCA found");
		}


	/**
	 * Creates a path from a node to the root.
	 * @param node the node to start from
	 * @param bfs the map of each node to its parent in the search tree
	 * @return the path from the node to the root
	 */
	private <V> LinkedList<V> path(V node, HashMap<V,V> bfs) {
		LinkedList<V> path = new LinkedList<>();
		while (node != null) { //O(n)
			path.add(node);
			node = bfs.get(node);
		}
		return path;
	}


	/**
	 * Finds the best place to put a new edge in a graph.
	 * @param g - the tree built by the power company
	 * @param root
	 * @return
	 * @param <V>
	 */
	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {
		HashMap<V,Integer> size = new HashMap<>(); //O(1)
		HashSet<V> visited = new HashSet<>(); //O(1)
		HashMap<V, LinkedList<V>> nodeNeighbours = new HashMap<>(); //O(1)
		size(g, root, size, visited, nodeNeighbours); //O(

		return findBestEdge(size, nodeNeighbours, g, root);
	}


	/**
	 * Find leaf of the two biggest subtree and return the edge between them.
	 * @param size
	 * @param nodeNeighbours
	 * @return
	 * @param <V>
	 */
	public <V> Edge<V> findBestEdge(HashMap<V, Integer> size, HashMap<V, LinkedList<V>> nodeNeighbours, Graph<V> g, V root) {
		LinkedList<V> leaves = new LinkedList<>();
		LinkedList<V> nodes = new LinkedList<>();
		HashSet<V> subTrees = new HashSet<>();

		for (V rootNeighbours : g.neighbours(root)){
			nodes.add(rootNeighbours);
		}

		Comparator<V> compareSize = Comparator.comparingInt(size::get);
		nodes.sort(Collections.reverseOrder(compareSize));

		subTrees.add(nodes.poll());
		if (g.degree(root) > 1){subTrees.add(nodes.poll());}
		else {leaves.add(root);}


		for (V rootNode : subTrees){
			while (g.degree(rootNode) != 1){
				int i = 0;
				V tempNode = null;

				for (V neighbor : nodeNeighbours.get(rootNode)){
					if (size.get(neighbor) > i){
						i = size.get(neighbor);
						tempNode = neighbor;
					}
				}
				rootNode = tempNode;
			}
			leaves.add(rootNode);
		}
		return new Edge<>(leaves.getFirst(),leaves.getLast());
	}


	/**
	 * Calculates the size of each subtree.
	 * @param g the graph
	 * @param node the node to start from
	 * @param count hashmap to store the size of each subtree
	 * @param visited set of visited nodes
	 * @param neighbours hashmap to store the neighbours of each node
	 * @return the size of the subtree
	 */
	public <V> int size(Graph<V> g, V node, HashMap <V, Integer> count, HashSet <V> visited, HashMap <V, LinkedList<V>> neighbours){
		int counter = 1;

		visited.add(node); //(1)
		LinkedList<V> n = new LinkedList<>();
		for(V children : g.neighbours(node)){  //O(degree(node))
			if(!visited.contains(children)){ //O(1)
				n.add(children); //O(1)
				counter += size(g, children, count, visited, neighbours);
			}
		}
		neighbours.put(node,n);
		count.put(node, counter);
		return counter;
	}
}
