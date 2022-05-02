package Grafos;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

public class GrafoLista implements Grafo{

    private Map<Vertice, List<Vertice>> adjVertices;
    private int time;
    private List<Vertice> vertexesOutOrder;

    public Map<Vertice, List<Vertice>> getAdjVertices() {
        return this.adjVertices;
    }

    public List<Vertice> DFS() {
        this.vertexesOutOrder = new Stack<Vertice>();
        adjVertices.keySet().forEach(k -> {
            k.setColor('w');
            k.setPI(null);
        });
        this.time = 0;

        final int[] i = {0};
        adjVertices.keySet().forEach(k -> {
            if(i[0] == 0) System.out.println(k.getLabel());
            i[0]++;
            if(k.getColor().equals('w')){
                DFSVisit(k);
            }
        });

        return vertexesOutOrder;
    }

    private void DFSVisit(Vertice v) {
        System.out.println(v.getLabel());
        this.time++;
        v.setIn(this.time);
        v.setColor('g');
        adjVertices.get(v).forEach(adj -> {
            System.out.println("     " + adj.getLabel());
            if(adj.getColor().equals('w')){
                adj.setPI(v);
                DFSVisit(adj);
            }
        });
        v.setColor('b');
        this.time++;
        v.setOut(time);
        this.vertexesOutOrder.add(v);
    }

    @Override
    public GrafoLista getTranspose() {
        if(this.adjVertices.isEmpty()) return null;

        Map<Vertice, List<Vertice>> transposeList = new HashMap<Vertice, List<Vertice>>();

        for(Map.Entry<Vertice, List<Vertice>> entry : adjVertices.entrySet()){
            entry.getValue().forEach((v) -> {
                boolean containsKey = false;
                Vertice transposeKey = v;
                for(Vertice key : transposeList.keySet()){
                    if(v.compareTo(key) == 0) {
                        containsKey = true;
                        transposeKey = key;
                    }
                }

                if(containsKey){
                    transposeList.get(transposeKey).add(new Vertice(entry.getKey().getLabel()));
                } else {
                    List<Vertice> arraylist = new ArrayList<Vertice>();

                    arraylist.add(new Vertice(entry.getKey().getLabel()));
                    transposeList.put(new Vertice(v.getLabel()), arraylist);
                }
            });
        }

        GrafoLista transpose = new GrafoLista();
        transpose.readAdjList(transposeList);
        return transpose;
    }

    @Override
    public void readAdjList(Map<Vertice, List<Vertice>> adjList) {
        this.adjVertices = new HashMap<>(adjList);
    }

    @Override
    public void printGraph() {
        for(Map.Entry<Vertice, List<Vertice>> entry : this.adjVertices.entrySet()) {
            System.out.printf("%s:", entry.getKey().getLabel());
            entry.getValue().forEach(v -> {
                System.out.printf(" %s;", v.getLabel());
            });
            System.out.printf("\n");
        }
    }
}
