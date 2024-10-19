package graphAlgorithms;
/**
 * File: GraphTraversal.java
 * Class: CSCI 4330
 * Author: Junior Meador
 * Created on: March 6, 2024
 * Last Modified: March 18, 2024
 * Description: BFS and DFS Bipartite Program - HA3
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import graphAlgorithms.Graph.Color;

public class GraphTraversal {

	private GraphTraversal() {
		// Prevent instantiation
	}

	// Performs BFS traversal on the graph and returns the parent array indicating
	// the tree structure.
	public static int[] bfsTraversal(Graph graph) {
		int V = graph.V();
		int[] parent = new int[V];
		Arrays.fill(parent, -1); // Initialize parent array with -1 to indicate no parent.
		boolean[] visited = new boolean[V]; // Track visited vertices.
		Queue<Integer> queue = new LinkedList<>(); // Queue for BFS.

		for (int i = 0; i < V; i++) {
			if (!visited[i]) {
				queue.offer(i);
				visited[i] = true;

				while (!queue.isEmpty()) {
					int u = queue.poll();
					for (int v : graph.getNeighbors(u)) { // Iterate through neighbors.
						if (!visited[v]) {
							visited[v] = true;
							parent[v] = u; // Set parent.
							queue.offer(v);
						}
					}
				}
			}
		}
		return parent;
	}

	// Performs DFS traversal on the graph and returns the parent array indicating
	// the tree structure.
	public static int[] dfsTraversal(Graph graph) {
		int V = graph.V();
		int[] parent = new int[V];
		Arrays.fill(parent, -1); // Initialize parent array with -1.
		boolean[] visited = new boolean[V]; // Track visited vertices.

		for (int i = 0; i < V; i++) {
			if (!visited[i]) {
				dfsHelper(i, visited, parent, graph); // Recursive DFS helper.
			}
		}
		return parent;
	}

	// Helper method for recursive DFS traversal.
	private static void dfsHelper(int u, boolean[] visited, int[] parent, Graph graph) {
		visited[u] = true;
		for (int v : graph.getNeighbors(u)) { // Iterate through neighbors.
			if (!visited[v]) {
				parent[v] = u; // Set parent.
				dfsHelper(v, visited, parent, graph); // Recurse.
			}
		}
	}

	// Determines if the graph is bipartite using BFS, coloring the graph in the
	// process.
	public static GraphColoringResult bfsColoring(Graph graph) {
		Color[] color = new Color[graph.V()];
		Arrays.fill(color, Color.UNCOLORED); // Initialize all vertices as uncolored.
		boolean isBipartite = true;
		List<int[]> conflictEdges = new ArrayList<>(); // Track edges causing a conflict in coloring.

		for (int i = 0; i < graph.V(); i++) {
			if (color[i] == Color.UNCOLORED) {
				isBipartite = assignColorBFS(i, color, conflictEdges, graph);
				if (!isBipartite)
					break; // Stop if graph is not bipartite.
			}
		}

		return new GraphColoringResult(isBipartite, color, null, conflictEdges);
	}

	// Helper method for coloring the graph using BFS.
	private static boolean assignColorBFS(int startVertex, Color[] color, List<int[]> conflictEdges, Graph graph) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(startVertex);
		color[startVertex] = Color.RED; // Start coloring with RED.

		while (!queue.isEmpty()) {
			int u = queue.poll();
			for (int v : graph.getNeighbors(u)) {
				if (color[v] == Color.UNCOLORED) {
					color[v] = (color[u] == Color.RED) ? Color.BLUE : Color.RED; // Alternate colors.
					queue.add(v);
				} else if (color[v] == color[u]) {
					conflictEdges.add(new int[] { u, v });
					return false; // Found conflict, graph is not bipartite.
				}
			}
		}
		return true; // No conflicts found, graph is bipartite.
	}

	// Determines if the graph is bipartite using DFS, coloring the graph in the
	// process.
	public static GraphColoringResult dfsColoring(Graph graph) {
		Color[] color = new Color[graph.V()];
		Arrays.fill(color, Color.UNCOLORED); // Initialize all vertices as uncolored.
		boolean isBipartite = true;
		List<int[]> conflictEdges = new ArrayList<>(); // Track edges causing a conflict in coloring.

		for (int start = 0; start < graph.V() && isBipartite; start++) {
			if (color[start] == Color.UNCOLORED) {
				isBipartite = dfsColor(start, Color.RED, color, conflictEdges, graph);
				if (!isBipartite)
					break; // Stop if graph is not bipartite.
			}
		}

		return new GraphColoringResult(isBipartite, color, null, conflictEdges);
	}

	// Helper method for coloring the graph using DFS.
	private static boolean dfsColor(int u, Color col, Color[] color, List<int[]> conflictEdges, Graph graph) {
		color[u] = col;
		for (int v : graph.getNeighbors(u)) {
			if (color[v] == Color.UNCOLORED) {
				if (!dfsColor(v, col == Color.RED ? Color.BLUE : Color.RED, color, conflictEdges, graph)) {
					return false; // Found conflict, graph is not bipartite.
				}
			} else if (color[v] == col) {
				conflictEdges.add(new int[] { u, v });
				return false; // Found conflict, graph is not bipartite.
			}
		}
		return true; // No conflicts found, graph is bipartite.
	}
}
