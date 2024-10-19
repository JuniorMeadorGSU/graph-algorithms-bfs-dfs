package graphAlgorithms;
/**
 * File: ResultPrinter.java
 * Class: CSCI 4330
 * Author: Junior Meador
 * Created on: March 6, 2024
 * Last Modified: March 18, 2024
 * Description: BFS and DFS Bipartite Program - HA3
 */
import java.util.Map;

public class ResultPrinter {

	private ResultPrinter() {
		// Prevent instantiation
	}

	// Prints the results of the graph coloring or conflict detection
	public static void printResults(Graph graph, GraphColoringResult result, Map<Integer, String> indexToLabel,
			String method) {
		// Print which method's results are being printed.
		System.out.println("Results using " + method + ":");

		// Print whether the graph is 2-colorable or not
		System.out.println("The graph is "
				+ (result.isBipartite ? "2-colorable (bipartite)." : "not 2-colorable (not bipartite)."));

		if (result.isBipartite) {
			// If the graph is 2-colorable, print the coloring of each vertex
			graph.printColoring(result.color, indexToLabel);
		} else {
			// If the graph is not 2-colorable, print the conflict detected
			graph.printConflict(result.color, result.conflictEdges, indexToLabel);
		}
		System.out.println();
	}
}
