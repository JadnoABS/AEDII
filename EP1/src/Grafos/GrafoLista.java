package Grafos;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GrafoLista implements Grafo{

    private Map<Vertice, List<Vertice>> adjVertices;
    private int time;
    private List<Vertice> vertexesOutOrder;
    private Map<List<Vertice>, List<List<Vertice>>> stronglyConnectedAdjList;
    private String nextVertexLabel = "";

    public Map<Vertice, List<Vertice>> getAdjVertices() {
        return this.adjVertices;
    }

    public void setVertexesOutOrder(List<Vertice> outOrderList) {
        this.vertexesOutOrder = outOrderList;
    }

    public Grafo Kosaraju() {
        this.stronglyConnectedAdjList = new HashMap<>();
        GrafoLista transposed = getTranspose();
        transposed.setVertexesOutOrder(this.DFS());
        transposed.DFS();
        Map<List<Vertice>, List<List<Vertice>>> SCAL = transposed.getStronglyConnectedAdjList();


        Map<Vertice, List<Vertice>> adjListSCG = new HashMap<>();

        for (Map.Entry<List<Vertice>, List<List<Vertice>>> entry : SCAL.entrySet()) {
            String label = new String();
            for (Vertice v : entry.getKey()) {
                label += v.getLabel();
            }
            Vertice key = new Vertice(label);
            List<Vertice> value = new ArrayList<>();

            for (List<Vertice> verticeList : entry.getValue()) {
                label = "";
                for (Vertice v : verticeList) {
                    label += v.getLabel();
                }
                Vertice adj = new Vertice(label);
                value.add(adj);
            }

            adjListSCG.put(key, value);
        }

        Grafo stronglyConnectedGraph = new GrafoLista();
        stronglyConnectedGraph.readAdjList(adjListSCG);
        stronglyConnectedGraph.printGraph();

        return stronglyConnectedGraph.getTranspose();
    }

    public List<Vertice> DFS() {
        if(!(this.vertexesOutOrder instanceof ArrayList)){
            this.vertexesOutOrder = new ArrayList<Vertice>();
        }
        adjVertices.keySet().forEach(k -> {
            k.setColor('w');
            k.setPI(null);
        });
        this.time = 0;

       if(this.vertexesOutOrder.isEmpty()) {
           adjVertices.keySet().forEach(k -> {
               if(k.getColor().equals('w')){
                   DFSVisit(k, true, false);
               }
           });
       } else {
           this.stronglyConnectedAdjList = new HashMap<>();
           for (int i = this.vertexesOutOrder.size() - 1; i >= 0; i--) {
               Vertice k = this.vertexesOutOrder.get(i);
               if(k.getColor().equals('w')){
                   DFSVisit(k,false, true);
                   componentsADJ();
               }
           }
       }

        return vertexesOutOrder;
    }

    private void DFSVisit(Vertice v, boolean addToList, boolean isComponentStart) {
        List<Vertice> component = isComponentStart ? new ArrayList<>() : null;
        if(!addToList && isComponentStart) {
            component.add(v);
            this.stronglyConnectedAdjList.put(component, new ArrayList<>());
        }
        if(!addToList && !isComponentStart) {
            for(Map.Entry<List<Vertice>, List<List<Vertice>>> entry : this.stronglyConnectedAdjList.entrySet()){
                if(entry.getKey().contains(v.getPI())){
                    component = entry.getKey();
                    component.add(v);
                    break;
                }
            }
        }
        this.time++;
        v.setIn(this.time);
        v.setColor('g');
        for (Vertice adj : adjVertices.get(v)) {
            if(adj.getColor().equals('w')) {
                adj.setPI(v);
                DFSVisit(adj, addToList, false);
            }
        }
        v.setColor('b');
        this.time++;
        v.setOut(time);

        if(addToList)
            this.vertexesOutOrder.add(v);
    }

    private void componentsADJ() {
        for (Map.Entry<List<Vertice>, List<List<Vertice>>> entry : this.stronglyConnectedAdjList.entrySet()) {
            entry.getKey().forEach(v -> {
                this.adjVertices.get(v).forEach( k -> {
                    if(!entry.getKey().contains(k)) {
                        List<Vertice> comp;
                        for (List<Vertice> x : this.stronglyConnectedAdjList.keySet()) {
                            if(x.contains(k)){
                                comp = x;
                                if(!entry.getValue().contains(comp))
                                    entry.getValue().add(comp);
                                break;
                            }
                        }
                    }
                });
            });
        }
    }

    @Override
    public GrafoLista getTranspose() {
        if(this.adjVertices.isEmpty()) return null;

        Map<Vertice, List<Vertice>> transposeList = new HashMap<Vertice, List<Vertice>>();

        for(Map.Entry<Vertice, List<Vertice>> entry : adjVertices.entrySet()){

            boolean containsKey = false;
            for (Vertice vertice : transposeList.keySet()) {
                if(vertice.equals(entry.getKey())){
                    containsKey = true;
                    break;
                }
            }
            if(!containsKey) {
                transposeList.put(entry.getKey(), new ArrayList<>());
            }

            for (Vertice v : entry.getValue()) {
                containsKey = false;
                for (Vertice vertice : transposeList.keySet()) {
                   if(vertice.equals(v)){
                       containsKey = true;
                       break;
                   }
                }

                if(containsKey){
                    transposeList.get(v).add(entry.getKey());
                } else {
                    List<Vertice> arraylist = new ArrayList<Vertice>();

                    arraylist.add(entry.getKey());
                    transposeList.put(v, arraylist);
                }
            };
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

    public Map<List<Vertice>, List<List<Vertice>>> getStronglyConnectedAdjList() {
        return stronglyConnectedAdjList;
    }
}
