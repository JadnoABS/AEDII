package Grafos;

interface Grafo {

  Grafo getTranspose();

  void readList(Map<Vertice, List<Vertice>> adjList);

}
