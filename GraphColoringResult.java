package graphAlgorithms;
/**
 * File: GraphColoringResult.java
 * Class: CSCI 4330
 * Author: Junior Meador
 * Created on: March 6, 2024
 * Last Modified: March 18, 2024
 * Description: BFS and DFS Bipartite Program - HA3
 */
import java.util.List;

public class GraphColoringResult {
	
    boolean isBipartite;
    Graph.Color[] color;
    int[] parent;
    List<int[]> conflictEdges;

     // Constructs a GraphColoringResult with the specified properties.
    public GraphColoringResult(boolean isBipartite, Graph.Color[] color, int[] parent, List<int[]> conflictEdges) {
        this.isBipartite = isBipartite;
        this.color = color;
        this.parent = parent;
        this.conflictEdges = conflictEdges;
    }

    // Getter for isBipartite
    public boolean isBipartite() {
        return isBipartite;
    }

    // Getter for color array
    public Graph.Color[] getColor() {
        return color;
    }

    // Getter for parent array
    public int[] getParent() {
        return parent;
    }

    // Getter for list of conflict edges
    public List<int[]> getConflictEdges() {
        return conflictEdges;
    }
}
