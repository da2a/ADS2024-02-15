package by.it.group351005.gorodko.lesson13;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    final List<List<Integer>> graph;
    public final int vertexCount;
    private String[] vertexNames;

    public Graph(int count) {
        vertexCount = count;
        graph = new ArrayList<>();
        vertexNames = new String[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            graph.add(new ArrayList<>());
        }
    }

    public void addOrientedEdge(int a, int b) {
        graph.get(a).add(b);
    }

    public int[] getNeighbors(int vertex) {
        int[] neighbors = new int[graph.get(vertex).size()];
        int i = 0;
        for (int index : graph.get(vertex)) {
            neighbors[i++] = index;
        }
        Arrays.sort(neighbors);
        return neighbors;
    }

    public String getVertexName(int vertex) {
        return vertexNames[vertex];
    }

    public void setVertexNames(int vertex, String vertexName) {
        this.vertexNames[vertex] = vertexName;
    }
}