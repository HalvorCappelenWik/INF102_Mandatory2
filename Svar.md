# Answer File - Semester 2
# Description of each Implementation
Briefly describe your implementation of the different methods. What was your idea and how did you execute it? If there were any problems and/or failed implementations please add a description.

## Task 1 - mst
My plan for the implementation of my minimum spanning tree method was to use prime's algorithm as showed in lecture. 

First I create a list where I will store all the edges which will be included in the mst and a set which will control the vertices which have been found when iterating through the graph.

Then, if the graph don't have any edges the method just returns an empty list, because there is no mst, since the graph won't have any subsets of edges. 

Then I create a priorityQueue which will hold the edges that are to be searched. The queue will sort the edges according to the edges with the least weight, since we want the minimum.

I then select a start-node and adds the start-nodes neighbours to the toSearch queue. 

Then we iterate through the toSearch queue as long as there are any edges to search along. 
In the iteration we select and remove the first edge form the queue (since it is the one with the least weight) and check if the nodes connected to the edge has been found, if not we add it 
to the list with the mst-edges. Then we update the found set and updates the toSearch queue with the new edges which is connected to the node just visited (isVisited method). 

Lastly we return the list with the edges forming the minimum spanning tree. 


## Task 2 - lca
*Enter description*

## Task 3 - addRedundant
*Enter description*


# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented any helper methods you must add these as well.**

* ``mst(WeightedGraph<T, E> g)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``lca(Graph<T> g, T root, T u, T v)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``addRedundant(Graph<T> g, T root)``: O(?)
    * *Insert description of why the method has the given runtime*

