package Grafos;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GrafoLista implements Grafo{

    private Map<Vertice, List<Vertice>> adjVertices;
    private int time;
    private List<Vertice> vertexesOutOrder;
    private Map<Componente, List<Componente>> stronglyConnectedComponents;

    public GrafoLista() {}
    public GrafoLista(Map<Vertice, List<Vertice>> map) {
        this.readAdjList(map);
    }

    public void setVertexesOutOrder(List<Vertice> outOrderList) {
        this.vertexesOutOrder = outOrderList;
    }

    public Map<Vertice, List<Vertice>> componentsToVertexMap() {
        Map<Vertice, List<Vertice>> componentMap = new HashMap<>();

        for (Map.Entry<Componente, List<Componente>> entry : stronglyConnectedComponents.entrySet()) {
            List<Vertice> list = new ArrayList<>();
            for (Componente comp : entry.getValue()) {
               list.add(comp.toVertice());
            }
            componentMap.put(entry.getKey().toVertice(), list);
        }

        return componentMap;
    }

    public Map<Componente, List<Componente>> componentsToMap() {
        return this.stronglyConnectedComponents;
    }

    public Map<Vertice, List<Vertice>> vertexMap() {
        return this.adjVertices;
    }

    public GrafoLista Kosaraju() {
        GrafoLista transposed = getTranspose();
        transposed.setVertexesOutOrder(this.DFS());
        transposed.DFS();

        Map<Vertice, List<Vertice>> adjListSCG = transposed.componentsToVertexMap();

        GrafoLista stronglyConnectedGraph = new GrafoLista();
        stronglyConnectedGraph.readAdjList(adjListSCG);

        return stronglyConnectedGraph.getTranspose();
    }

    public List<Vertice> DFS() {
        if(!(this.vertexesOutOrder instanceof ArrayList)){
            this.vertexesOutOrder = new ArrayList<Vertice>();
        }
        for (Vertice k : adjVertices.keySet()) {
            k.setColor('w');
            k.setPI(null);
        }
        this.time = 0;

       if(this.vertexesOutOrder.isEmpty()) {
           for (Vertice k : adjVertices.keySet()) {
               if(k.getColor().equals('w')){
                   DFSVisit(k, true, false);
               }
           }
       } else {
           this.stronglyConnectedComponents = new HashMap<>();
           for (int i = this.vertexesOutOrder.size() - 1; i >= 0; i--) {
               Vertice k = this.vertexesOutOrder.get(i);
               if(k.getColor().equals('w')){
                   DFSVisit(k,false, true);
               }
           }
           componentsADJ();
       }

        return vertexesOutOrder;
    }

    private void DFSVisit(Vertice v, boolean addToList, boolean isComponentStart) {
        Componente component = isComponentStart ? new Componente() : null;
        if(!addToList && isComponentStart) {
            component.add(v);
            this.stronglyConnectedComponents.put(component, new ArrayList<>());
        }
        if(!addToList && !isComponentStart) {
            for(Map.Entry<Componente, List<Componente>> entry : this.stronglyConnectedComponents.entrySet()){
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
            if(adj.getColor() == null){
                for (Vertice vertice : this.adjVertices.keySet()) {
                   if(vertice.getLabel().equals(adj.getLabel())) {
                       adj = vertice;
                       break;
                   }
                }
            };
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
        for (Map.Entry<Componente, List<Componente>> component : this.stronglyConnectedComponents.entrySet()) {
            for (Vertice vertice : component.getKey().getComponentList()) {
                for (Vertice adj : this.adjVertices.get(vertice)) {
                    if(!component.getKey().contains(adj)){
                        for (Componente c : this.stronglyConnectedComponents.keySet()) {
                            if(c.equals(component.getKey())) continue;
                            if(c.contains(adj) && !component.getValue().contains(c)){
                                component.getValue().add(c);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public List<Vertice> topologicalSort() {
        List<Vertice> vertexOrder = DFS();
        List<Vertice> vertexTopOrder = new ArrayList<>();
        for (int i = vertexOrder.size()-1; i >= 0; i--) {
            vertexTopOrder.add(vertexOrder.get(i));
        }

        return vertexTopOrder;
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
}
