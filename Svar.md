# Answer File - Semester 2
# Description of each Implementation
Briefly describe your implementation of the different methods. What was your idea and how did you execute it? If there were any problems and/or failed implementations please add a description.

## Task 1 - mst
My plan for the implementation of my minimum spanning tree method was to use prime's algorithm as showed in lecture. 

First I create a list where I will store all the edges which will be included in the mst and a set which will control the vertices which have been found when iterating through the graph.

Then I create a priorityQueue which will hold the edges that are to be searched. The queue will sort the edges according to the edges with the least weight, since we want the minimum.

I then select a start-node and adds the start-nodes neighbours to the toSearch queue. 

Then we iterate through the toSearch queue as long as there are any edges to search along. 
In the iteration we select and remove the first edge form the queue (since it is the one with the least weight) and check if the nodes connected to the edge has been found, if not we add it 
to the list with the mst-edges. Then we update the found set and updates the toSearch queue with the new edges which is connected to the node just visited (isVisited method). 

Lastly we return the list with the edges forming the minimum spanning tree. 

## Task 2 - lca
My plan for the implementation of the least common ancestor method was to use breadth first search from the root (power-station) and through the graph, and then find the respective paths to each of the nodes with no power. 
And then check when these two paths has a common node (iterating from node the furthest away from root and towards root), this node would then be the least common ancestor. 

My BFS implementation: 
Same as in lecture, but instead of just returning the furthest node I return a hashmap of all nodes and their parent node according to the search. 

My lca method implementation: 
First I conduct a bfs on the graph, retrieving a hashmap of all nodes and parent nodes. Then I find the path from the node with no power to the power-station with the help of the bfs.
This is done with the path method, which takes a node and iterate through the bfs hashmap and adds all the nodes on their way from the node to the power-station (see implementation). 
I then check when each of the respective paths (from node -> power-station) has a node in common. This will be the least common ancestor.

## Task 3 - addRedundant
Myplan for the implementation of the addRedundant was that if I were able to find the two biggest subtrees and add an edge between leaves of these trees, this would
cause as small outage as possible if one power-cable stops working. 

To do this I created a help-method which will calculate the size of all the subtrees (number of children) in our graph and store this in a hashmap (Modified size-method from test.problemSolver.PowerOutage). This method works recursively down a three. 
Then in the addRedundant method I choose the two biggest subtrees. Then I traverse through these subtrees, and finds the leaf of the trees (a leaf will have degree = 1). 
Then I return a new edge between these two leafs.

# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented any helper methods you must add these as well.**

### MST
* ``mst(WeightedGraph<T, E> g)``: O(m log m)
    * For my mimimum spanning tree implementation, I based my implementation on lecture 11, where we went through prims algorithm to find the minimum spanning tree of a graph. 
    * More specific this implementation is with minheap for edges, since we want to sort our weighted edges to get minimum. 
    * For details about the implementation look at description section. 
    * Creating a priority queue (toSearch) and filling it with our graph is O(n), iterating thru all the edges is O(m) and adding to our pq is O(log m)
    * This gives us O(m log m). See method for more details. 
##### Helper methods 
* ``isVisited(WeightedGraph<V, E> g, V node, HashSet<V> found, PriorityQueue<Edge<V>> toSearch)``: O(log m)
    * isVisited is my helper method to keep track of the nodes that has been visited in our search. 
    * For each node which is found, it iterates through its adjacent edges and adds the edges to our toSearch pq. 
    * Iterating through a nodes adjacent edges is O(degree(node)) and adding to a pq is O(log m)


### LCA 
* ``lca(Graph<T> g, T root, T u, T v)``: O(n)
    * In the method for finding the last common ancestor i first conduct a bfs on the graph, this is O(m) (ref Martin's lecture, se comments in code)
    * I then find the two respected path to node u and v, this is O(n) (path method). 
    * Lastly I iterate each node in both paths until we find a node that is in common for both paths, this is O(n).
    * See method for specifics. 
  ##### Helper methods
* ``bfs((Graph<T> g, T root))``: O(m)
  * Conducting bfs, implemented as in lecture, is O(m). See comments in method. 
* ``addNeighbours (V node, Graph <V> g, LinkedList<V> toSearch, HashMap<V,V> bfs)``: O(n)
  * if a node is neighbour of all nodes then O(n). 
* ``path(V node, HashMap<V,V> bfs)``: O(n)
  * Worst case is that if path of node is through all nodes, hence O(n)


### AddRedundant
* ``addRedundant(Graph<T> g, T root)``: O(n)
    * Firstly the addRedundant method makes a call to the size method. This would be O(n), since the size method works recursively down our tree storing all the nodes and their size. The recursive function is called at most n times before reaching its base case (all nodes has been visited).
    * Further on we iterate through the neighbours of root to select the biggest subtree of the neighbours, this is worst case O(n).
    * Then by the use of Collection.Max with comparator from the hashmap storing each node and their size, we pick the two largest subtrees. Collection.Max on a linkedList is O(n)
    * We then pass the two biggest subtrees in to the EdgeBetweenSubtree method, which is O(n). 
  ##### Helper methods

* ``EdgeBetweenSubtree(HashMap<V, Integer> size, HashMap<V, LinkedList<V>> nodeNeighbours, Graph<V> g, V root)``: O(n)
  * This method wil follow the two biggest subtrees down the graph to a leaf, going to the nodes with the biggest amount of children.  
  * Worst case is O(n). The method will always go further and further down the graph, and never in the same node more than once. 
  * See comments in method. 
* ``size(Graph<V> g, V node, HashMap <V, Integer> count, HashSet <V> visited, HashMap <V, ArrayList<V>> neighbours )``: O(n)
  * As mentioned, size is a recursive method. Where is called at most n times before reaching its base case (all nodes has been visited).


