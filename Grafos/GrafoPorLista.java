package Grafos;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class GrafoPorLista implements Grafo {
  private Map<Vertice, List<Vertice>> adjVertices;

  public GrafoPorLista() {
  }

  public GrafoPorLista(Map<Vertice, List<Vertice>> adj) {
    this.adjVertices = adj;
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
