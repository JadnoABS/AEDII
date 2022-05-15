package Grafos;

import java.util.List;
import java.util.Map;

public interface Grafo {
    Grafo getTranspose();
    void readAdjList(Map<Vertice, List<Vertice>> adjList);
    void printGraph();
    List<Vertice> topologicalSort();
    List DFS();
    Grafo Kosaraju();
}
