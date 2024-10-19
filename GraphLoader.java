package graphAlgorithms;
/**
 * File: GraphLoader.java
 * Class: CSCI 4330
 * Author: Junior Meador
 * Created on: March 6, 2024
 * Last Modified: March 18, 2024
 * Description: BFS and DFS Bipartite Program - HA3
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GraphLoader {

	private GraphLoader() {
		// Prevent instantiation
	}

	// Reads graph data from a file and constructs a Graph object.
	public static Graph readGraphFromFile(String filePath, int graphCounter) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(filePath))) {
			// Assume the first line contains the number of vertices. Edge count is not
			// directly used.
			int V = Integer.parseInt(scanner.nextLine().trim().split("\\s+")[0]);
			Graph g = new Graph(V, graphCounter); // Initialize the Graph object.
			Map<String, Integer> labelToIndex = new HashMap<>(); // Map to convert labels to numeric indices.

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (!line.isEmpty()) { // Ignore empty lines.
					processLine(g, labelToIndex, line);
				}
			}
			return g;
		}
	}

	// Processes a line from the graph file, adding edges to the graph.
	private static void processLine(Graph g, Map<String, Integer> labelToIndex, String line) {
		String[] parts = line.split("\\s+");
		// Convert labels to indices, creating new entries in the map if necessary.
		for (int i = 1; i < parts.length; i++) {
			int fromIndex = labelToIndex.computeIfAbsent(parts[0], k -> labelToIndex.size());
			int toIndex = labelToIndex.computeIfAbsent(parts[i], k -> labelToIndex.size());
			g.addEdge(fromIndex, toIndex); // Add edge between the vertices.
		}
	}

	// Generates a mapping from vertex indices back to their original labels.
	public static Map<Integer, String> loadVertexLabels(String filePath) throws FileNotFoundException {
		Map<String, Integer> labelToIndex = new HashMap<>(); // Temporary map to facilitate index lookup.
		Map<Integer, String> indexToLabel = new HashMap<>(); // The map to be returned.

		try (Scanner scanner = new Scanner(new File(filePath))) {
			scanner.nextLine(); // Skip the first line which contains the vertex and edge count.
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (!line.isEmpty()) {
					for (String label : line.split("\\s+")) {
						// For each unique label, assign an index and store the reverse mapping.
						labelToIndex.computeIfAbsent(label, k -> {
							int index = labelToIndex.size();
							indexToLabel.put(index, label);
							return index;
						});
					}
				}
			}
		}
		return indexToLabel; // Return the mapping from indices to labels.
	}
}
