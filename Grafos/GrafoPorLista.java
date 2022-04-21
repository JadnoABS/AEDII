package Grafos;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class GrafoPorLista {
  private Map<Vertice, List<Vertice>> adjVertices;

  public GrafoPorLista() {
  }

  public GrafoPorLista(Map<Vertice, List<Vertice>> adj) {
    this.adjVertices = adj;
  }

  public void generateRandom(int maxNVertices) {
    adjVertices = new HashMap<Vertice, List<Vertice>>();

    Random generator = new Random();

    int nVertices = generator.nextInt(maxNVertices);

    for (int i = 0; i < nVertices; i++) {
      adjVertices.put(new Vertice(getCharForNumber(i + 1)), new ArrayList<Vertice>());
    }

    Iterator<Map.Entry<Vertice, List<Vertice>>> iterator = adjVertices.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Vertice, List<Vertice>> entry = iterator.next();

      Iterator<Map.Entry<Vertice, List<Vertice>>> iterator2 = adjVertices.entrySet().iterator();
      while (iterator2.hasNext()) {
        Map.Entry<Vertice, List<Vertice>> entry2 = iterator2.next();

        if (!entry.getKey().equals(entry2.getKey()) && generator.nextInt(5) < 2) {
          entry.getValue().add(entry2.getKey());
        }
      }
    }
  }

  public void imprimeMermaid() {
    String aresta = "-->";

    Iterator<Map.Entry<Vertice, List<Vertice>>> iterator = adjVertices.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Vertice, List<Vertice>> entry = iterator.next();

      entry.getValue().forEach((v) -> System.out.println(entry.getKey().label + aresta + v.label));
    }
  }

  private String getCharForNumber(int i) {
    return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
  }

  public Grafo getTraspose() {
    Map<Vertice, List<Vertice>> transposeList = new HashMap<Vertice, List<Vertice>>();

    Iterator<Map.Entry<Vertice, List<Vertice>>> iterator = adjVertices.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Vertice, List<Vertice>> entry = iterator.next();

      entry.getValue().forEach((v) -> {
        List<Vertice> arraylist = new ArrayList<Vertice>();

        arraylist.add(new Vertice(entry.getKey().label));
        transposeList.put(new Vertice(v.label), arraylist);
      });
    }

    Grafo transpose = new Grafo(transposeList);
    return transpose;
  }
}

class Vertice {
  String label;
  char color;

  int in;
  int out;

  Vertice(String label) {
    this.label = label;
  }
}
