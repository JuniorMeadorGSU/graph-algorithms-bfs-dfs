package graphAlgorithms;
/**
 * File: Graph.java
 * Class: CSCI 4330
 * Author: Junior Meador
 * Created on: March 6, 2024
 * Last Modified: March 18, 2024
 * Description: BFS and DFS Bipartite Program - HA3
 */
import java.util.*;

public class Graph {

	// Defines colors for vertices in bipartite graph checking
	public enum Color {
		UNCOLORED, RED, BLUE;
	}

	private int V; // Number of vertices
	private int edgeCount = 0; // Number of edges
	private List<List<Integer>> adjacencyList; // Adjacency list representation of the graph
	private int graphId; // Identifier for the graph

	// Constructor - Initializes the graph 
	public Graph(int V, int graphId) {
		this.V = V;
		this.graphId = graphId;
		adjacencyList = new ArrayList<>(V);
		for (int i = 0; i < V; i++) {
			adjacencyList.add(new LinkedList<>());
		}
	}

	// Adds an undirected edge between vertices v and w
	public void addEdge(int v, int w) {
		if (!adjacencyList.get(v).contains(w)) {
			edgeCount++;
			adjacencyList.get(v).add(w);
			adjacencyList.get(w).add(v);
		}
	}

	// Returns number of vertices in the graph
	public int V() {
		return V;
	}

	// Returns number of edges in the graph
	public int getEdgesCount() {
		return edgeCount;
	}

	// Returns unique identifier of the graph
	public int getGraphId() {
		return graphId;
	}

	// Retrieves neighbors of a specified vertex
	public List<Integer> getNeighbors(int vertex) {
		return new ArrayList<>(adjacencyList.get(vertex));
	}

	// Performs BFS and returns an array representing parent relationships
	public int[] bfsParentGraph() {
		int[] parent = new int[V];
		Arrays.fill(parent, -1);
		boolean[] visited = new boolean[V];
		Queue<Integer> queue = new LinkedList<>();

		for (int i = 0; i < V; i++) {
			if (!visited[i]) {
				queue.offer(i);
				visited[i] = true;
				while (!queue.isEmpty()) {
					int u = queue.poll();
					for (int v : adjacencyList.get(u)) {
						if (!visited[v]) {
							visited[v] = true;
							parent[v] = u;
							queue.offer(v);
						}
					}
				}
			}
		}
		return parent;
	}

	// Performs DFS and returns an array representing parent relationships
	public int[] dfsParentGraph() {
		int[] parent = new int[V];
		Arrays.fill(parent, -1);
		boolean[] visited = new boolean[V];

		for (int i = 0; i < V; i++) {
			if (!visited[i]) {
				dfsHelper(i, visited, parent);
			}
		}
		return parent;
	}

	// Helper method for DFS to explore graph recursively
	private void dfsHelper(int u, boolean[] visited, int[] parent) {
		visited[u] = true;
		for (int v : adjacencyList.get(u)) {
			if (!visited[v]) {
				parent[v] = u;
				dfsHelper(v, visited, parent);
			}
		}
	}

	// Prints coloring of vertices if graph is bipartite
	public void printColoring(Color[] color, Map<Integer, String> indexToLabel) {
		System.out.print("Vertex coloring: ");
		for (int v = 0; v < V; v++) {
			String vertexLabel = indexToLabel.getOrDefault(v, String.valueOf(v));
			String colorName = color[v].name();
			System.out.printf("%s(%s)%s", vertexLabel, colorName, v < V - 1 ? ", " : "");
		}
		System.out.println();
	}
	
	// Prints coloring conflicts if graph is not bipartite
	public void printConflict(Color[] color, List<int[]> conflicts, Map<Integer, String> indexToLabel) {

		int[] firstConflict = conflicts.get(0);
		String vertex1 = indexToLabel.getOrDefault(firstConflict[0], String.valueOf(firstConflict[0]));
		String vertex2 = indexToLabel.getOrDefault(firstConflict[1], String.valueOf(firstConflict[1]));
		String conflictColor = color[firstConflict[0]].name();

		System.out.print("Vertex coloring up to first conflict: ");

		// Calculate the last vertex to print so no end comma in CSV
		int lastVertexToPrint = Math.max(firstConflict[0], firstConflict[1]);
		for (int v = 0; v <= lastVertexToPrint; v++) {
			if (color[v] != Color.UNCOLORED) { // Only include visited (colored) vertices
				String vertexLabel = indexToLabel.getOrDefault(v, String.valueOf(v));
				// Print without trailing comma for the last element
				if (v < lastVertexToPrint || (v == lastVertexToPrint && conflicts.get(0).length <= 1)) {
					System.out.printf("%s(%s), ", vertexLabel, color[v].name());
				} else {
					System.out.printf("%s(%s)", vertexLabel, color[v].name());
				}
			}
		}

		System.out.println();
		
		// Print first set of vertices that have edge color conflict
		System.out.printf("First conflict detected between %s and %s, both colored %s.\n", vertex1, vertex2,
				conflictColor);
	}
}