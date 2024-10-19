package graphAlgorithms;
/**
 * File: Main.java
 * Class: CSCI 4330
 * Author: Junior Meador
 * Created on: March 6, 2024
 * Last Modified: March 18, 2024
 * Description: BFS and DFS Bipartite Program - HA3
 */
public class Main {
	
    public static void main(String[] args) {
        // Array of file paths containing graph data to be processed.
        String[] filePaths = {
            "C:\\Users\\Junio\\Downloads\\HA3_TestGraphs\\G1.txt",
            "C:\\Users\\Junio\\Downloads\\HA3_TestGraphs\\G2.txt"
        };

        // Iterate over each file path and process the graph contained 
        for (String filePath : filePaths) {
            // Call GraphProcessor to handle the reading, processing, and analysis of the graph
            GraphProcessor.processGraph(filePath);
        }
    }
}
