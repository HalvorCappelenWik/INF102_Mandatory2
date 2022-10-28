package student;

import java.util.*;
import graph.*;

public class ProblemSolver implements IProblem {

	//m - number of edges in the graph
	//n - number of nodes in the graph

	@Override
	public <V, E extends Comparable<E>> List<Edge<V>> mst(WeightedGraph<V, E> g) { //O(n) + O(m log m) + O(m log m) = O(m log m)
		ArrayList<Edge<V>> mstEdges = new ArrayList<>(g.numVertices());
		PriorityQueue<Edge<V>> toSearch = new PriorityQueue<>(g); //O(n)
		HashSet<V> found = new HashSet<>(g.numVertices());

		if (g.numEdges() < 1) {  // <- if edges of graph is less than 1, there is no mst.
			return mstEdges;
		}

		V start = g.getFirstNode(); //O(1)
		found.add(start);

		for (Edge<V> edge : g.adjacentEdges(start)) { //O(m)
			toSearch.add(edge); //O(log m)
		}


		while(!toSearch.isEmpty()) { //O(m)
			Edge<V> next = toSearch.poll(); //O(log m)
			if (found.contains(next.a) && found.contains(next.b)) //O(1)
				continue;

			mstEdges.add(next);

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
		for (Edge<V> edge : g.adjacentEdges(node)) { //O(degree(node))
			toSearch.add(edge); //O(log m)
		}
	}

		@Override
	public <V> V lca(Graph<V> g, V root, V u, V v) { //O(m) + O(n) + O(n) + O(n) = O(n)
		new BFS();
		HashMap<V, V> bfs = BFS.bfs(g,root); //O(m)


		LinkedList<V> pathU = path(u, bfs); //O(n)
		HashSet<V> setPathU = new HashSet<>(pathU); //O(n)
		LinkedList<V> pathV = path(v, bfs); //O(n)


		for (V node : pathV) { //O(n)
			if (setPathU.contains(node))  //O(1)
				return node;
		}
		throw new IllegalArgumentException("No LCA between the given nodes.");
		}


	/**
	 * Creates a path from a node to the root.
	 * @param node the node to start from
	 * @param bfs the map of each node to its parent in the search tree
	 * @return the path from the node to the root
	 */
	private <V> LinkedList<V> path(V node, HashMap<V,V> bfs) { //O(n)
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
		HashMap<V,Integer> size = new HashMap<>();
		HashSet<V> visited = new HashSet<>();
		HashMap<V, LinkedList<V>> nodeNeighbours = new HashMap<>();
		size(g, root, size, visited, nodeNeighbours); //O(n)


		LinkedList<V> nodes = new LinkedList<>();
		HashSet<V> subTrees = new HashSet<>();

		for (V neighbours : g.neighbours(root)) {  //O(n)
			nodes.add(neighbours); //O(1)
		}

		Comparator<V> compareSize = Comparator.comparingInt(size::get);
		//nodes.sort(Collections.reverseOrder(compareSize)); //O(n log n)
		subTrees.add(Collections.max(nodes, compareSize)); //O(n)
		nodes.remove(Collections.max(nodes, compareSize)); //O(n)



		if (g.degree(root) > 1) { //O(log n)
			subTrees.add(Collections.max(nodes, compareSize)); //O(n)
		} else subTrees.add(root); // if degree of root is less than 1, then root-node itself will be subtree.

		return EdgeBetweenSubtree(subTrees, size, nodeNeighbours, g); //O(n)
	}


	/**
	 * Find leaf between two subtrees and return the edge between them.
	 * @param size
	 * @param nodeNeighbours
	 * @return
	 * @param <V>
	 */
	public <V> Edge<V> EdgeBetweenSubtree(HashSet<V> subTrees, HashMap<V, Integer> size, HashMap<V, LinkedList<V>> nodeNeighbours, Graph<V> g) {
		LinkedList<V> leaves = new LinkedList<>();

		for (V rootNode : subTrees) { //We have only 2 subtrees, therefore for-loop will only run twice.
			while (g.degree(rootNode) != 1) { //O(log n) want to find leaf, hence keep iterating until degree of node = 1.
				int i = 0; //O(1)
				V tempNode = null; //O(1)

				for (V neighbor : nodeNeighbours.get(rootNode)) { //O(1/2n) finding the node with most neighbours.
					if (size.get(neighbor) > i) {
						i = size.get(neighbor);
						tempNode = neighbor;
					}
				}
				rootNode = tempNode;
			}
			leaves.add(rootNode);
		}
		return new Edge<>(leaves.getFirst(), leaves.getLast());
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

		visited.add(node);
		LinkedList<V> childrenList = new LinkedList<>();
		for(V child : g.neighbours(node)){  //O(n)
			if(!visited.contains(child)){
				childrenList.add(child);
				counter += size(g, child, count, visited, neighbours); //The function is called only if node has not been visited
			}
		}
		neighbours.put(node,childrenList);
		count.put(node, counter);
		return counter;
	}
}
