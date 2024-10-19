package graphAlgorithms;
/**
 * File: GraphProcessor.java
 * Class: CSCI 4330
 * Author: Junior Meador
 * Created on: March 6, 2024
 * Last Modified: March 18, 2024
 * Description: BFS and DFS Bipartite Program - HA3
 */

import java.io.FileNotFoundException;
import java.util.Map;

public class GraphProcessor {

    private GraphProcessor() {
        // Prevent instantiation
    }

    private static int graphCounter = 0; // Static counter to use for Graph ID

    // Main method to process the graph given via file path
    public static void processGraph(String filePath) {
        try {
            graphCounter++; // Increment the graph counter.
            Graph graph = loadGraph(filePath, graphCounter);
            Map<Integer, String> indexToLabel = GraphLoader.loadVertexLabels(filePath);

            printGraphDetails(graphCounter, graph);

            int[] parentBFS = graph.bfsParentGraph();
            int[] parentDFS = graph.dfsParentGraph();
            printTraversalResults(parentBFS, indexToLabel, "BFS");
            printTraversalResults(parentDFS, indexToLabel, "DFS");

            checkAndPrintBipartiteness(graph, indexToLabel);

        } catch (FileNotFoundException e) {
            System.err.printf("File not found: %s - %s\n", filePath, e.getMessage());
        } catch (Exception e) {
            System.err.printf("An error occurred during graph processing: %s\n", e.getMessage());
        }
    }

    // Load the graph from a file
    private static Graph loadGraph(String filePath, int graphCounter) throws FileNotFoundException {
        return GraphLoader.readGraphFromFile(filePath, graphCounter);
    }

    // Print graph number (graph ID) with corresponding vertices and edges
    private static void printGraphDetails(int graphNumber, Graph graph) {
        System.out.printf("Graph %d consists of %d vertices and %d edges.\n", graphNumber, graph.V(), graph.getEdgesCount());
    }

    // Perform BFS and DFS, then print the traversal results
    private static void printTraversalResults(int[] parent, Map<Integer, String> indexToLabel, String traversalMethod) {
        System.out.println(traversalMethod + " Parent Graph:");
        printParentGraph(parent, indexToLabel);
    }

    // Print the parent-child relationships resulting from graph traversals
    private static void printParentGraph(int[] parent, Map<Integer, String> indexToLabel) {
        for (int i = 0; i < parent.length; i++) {
            String childLabel = indexToLabel.getOrDefault(i, String.valueOf(i));
            String parentLabel = parent[i] != -1 ? indexToLabel.getOrDefault(parent[i], String.valueOf(parent[i])) : "Null";
            System.out.printf("%s -> %s\n", childLabel, parentLabel);
        }
        System.out.println();
    }

    // Check for bipartiteness using BFS and DFS, then print results
    private static void checkAndPrintBipartiteness(Graph graph, Map<Integer, String> indexToLabel) {
        GraphColoringResult resultBFS = GraphTraversal.bfsColoring(graph);
        GraphColoringResult resultDFS = GraphTraversal.dfsColoring(graph);

        ResultPrinter.printResults(graph, resultBFS, indexToLabel, "BFS");
        ResultPrinter.printResults(graph, resultDFS, indexToLabel, "DFS");
    }
}
