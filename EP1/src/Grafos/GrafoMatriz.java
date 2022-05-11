package Grafos;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class GrafoMatriz implements Grafo{

   private List<List<Boolean>> adjMatrix;
   private Map<Integer, Vertice> vertexIndexes;
   private List<Integer> vertexesOutOrder;
   private int time;

   private List<List<Boolean>> componentAdjMatrix;
   private Map<Integer, Componente> componentIndexes;

    public List<List<Boolean>> getAdjMatrix() {
        return adjMatrix;
    }

    public GrafoMatriz(){
    }

    public GrafoMatriz(List<List<Boolean>> matrix, Map<Integer, Vertice> indexes) {
        this.adjMatrix = matrix;
        this.vertexIndexes = indexes;
    }

    public void setVertexesOutOrder(List<Integer> vertexesOutOrder) {
        this.vertexesOutOrder = vertexesOutOrder;
    }

    public List<List<Boolean>> getComponentAdjMatrix() { return this.componentAdjMatrix; }

    public Map<Integer, Vertice> getSCIndexes() {
        Map<Integer, Vertice> SCI = new HashMap<>();
        for (Map.Entry<Integer, Componente> entry : this.componentIndexes.entrySet()) {
            SCI.put(entry.getKey(), entry.getValue().toVertice());
        }
        return SCI;
    }

    private Integer getIndexByComponent(Componente comp) {
        for (Map.Entry<Integer, Componente> entry : this.componentIndexes.entrySet()) {
           if(entry.getValue().equals(comp)){
               return entry.getKey();
           }
        }
        return -1;
    }

    private Integer getIndexByVertex(Vertice v) {
        for (Map.Entry<Integer, Vertice> entry : this.vertexIndexes.entrySet()) {
           if(entry.getValue().equals(v)){
               return entry.getKey();
           }
        }
        return -1;
    }


    public Grafo Kosaraju() {
       GrafoMatriz transpose = this.getTranspose();
       transpose.setVertexesOutOrder(this.DFS());
       transpose.DFS();

       transpose.printCAL();

        GrafoMatriz stronglyConnectedGraph = new GrafoMatriz();
        stronglyConnectedGraph.readAdjList(transpose.componentsToMap());
        System.out.println();
        stronglyConnectedGraph.printGraph();

        return stronglyConnectedGraph.getTranspose();
    }

   public List<Integer> DFS() {
       if(!(this.vertexesOutOrder instanceof ArrayList)){
           this.vertexesOutOrder = new ArrayList<>();
       }
       this.vertexIndexes.values().forEach(v -> {
          v.setColor('w');
          v.setPI(null);
       });
       this.time = 0;

       if(this.vertexesOutOrder.isEmpty()){
           for (Map.Entry<Integer, Vertice> entry : this.vertexIndexes.entrySet()) {
              if(entry.getValue().getColor().equals('w')){
                  DFSVisit(entry.getKey(), true, false);
              }
           }
       } else {
           this.componentIndexes = new HashMap<>();
           this.componentAdjMatrix = new ArrayList<>(adjMatrix.size());
           for (List<Boolean> x: adjMatrix) {
               this.componentAdjMatrix.add(new ArrayList<>(Collections.nCopies(adjMatrix.size(), false)));
           }
           for (int i = this.vertexesOutOrder.size() - 1; i >= 0; i--) {
               int v = this.vertexesOutOrder.get(i);
               if(vertexIndexes.get(v).getColor().equals('w')){
                   DFSVisit(v,false, true);
               }
           }
           printComponentsMatrix();
           componentsADJ();
           printComponentsMatrix();
       }


        return this.vertexesOutOrder;
   }

   private void DFSVisit(int index, boolean addToList, boolean isComponentStart) {
        Vertice v = this.vertexIndexes.get(index);

       Componente component = isComponentStart ? new Componente() : null;
       if(!addToList && isComponentStart) {
           component.add(v);
           int i = this.componentIndexes.size();
           this.componentIndexes.put(i, component);
       }
       if(!addToList && !isComponentStart) {
           for(Componente entry : this.componentIndexes.values()){
               if(entry.contains(v.getPI())){
                   entry.add(v);
                   break;
               }
           }
       }
       this.time++;
       v.setIn(this.time);
       v.setColor('g');

       for (int j = 0; j < adjMatrix.get(index).size(); j++) {
           if(adjMatrix.get(index).get(j)){
               Vertice adj = this.vertexIndexes.get(j);
               if(adj.getColor().equals('w')) {
                   adj.setPI(v);
                   DFSVisit(j, addToList, false);
               }
           }
       }
       v.setColor('b');
       this.time++;
       v.setOut(time);

       if(addToList)
           this.vertexesOutOrder.add(index);
   }

    public Map<Vertice, List<Vertice>> vertexesToMap() {
        Map<Vertice, List<Vertice>> componentMap = new HashMap<>();

        for (Map.Entry<Integer, Vertice> entry : this.vertexIndexes.entrySet()) {
            int index = entry.getKey();
            Vertice comp = entry.getValue();
            System.out.print(comp.getLabel() + " : ");

            List<Vertice> adjList = new ArrayList<>();

            for (int i = 0; i < this.adjMatrix.get(index).size(); i++) {
                if(this.adjMatrix.get(index).get(i)){
                    System.out.print(vertexIndexes.get(i).getLabel() + " ");
                    adjList.add(vertexIndexes.get(i));
                }
            }
            System.out.println();
            componentMap.put(comp, adjList);
        }
        return componentMap;
    }
   public Map<Vertice, List<Vertice>> componentsToMap() {
        Map<Vertice, List<Vertice>> componentMap = new HashMap<>();

       for (Map.Entry<Integer, Componente> entry : this.componentIndexes.entrySet()) {
           int index = entry.getKey();
           Componente comp = entry.getValue();
           System.out.print(comp.toVertice().getLabel() + " : ");

           List<Vertice> adjList = new ArrayList<>();

           for (int i = 0; i < this.componentAdjMatrix.get(index).size(); i++) {
              if(this.componentAdjMatrix.get(index).get(i)){
                  System.out.print(componentIndexes.get(i).toVertice().getLabel() + " ");
                  adjList.add(componentIndexes.get(i).toVertice());
              }
           }
           System.out.println();
           componentMap.put(comp.toVertice(), adjList);
       }
       return componentMap;
   }

   public void printComponentsMatrix() {
       for (List<Boolean> list : this.componentAdjMatrix) {
           for (int i = 0; i < list.size(); i++) {
              System.out.print(list.get(i) + " ");
           }
           System.out.println();
       }
   }

   private void componentsADJ() {
       for (Map.Entry<Integer, Vertice> e : this.vertexIndexes.entrySet()) {
           Vertice vertex = e.getValue();
           int index = e.getKey();
           System.out.println(vertex.getLabel());
//           Componente comp = null;
//           int compIndex = 0;
           for (Map.Entry<Integer, Componente> intCompPair : this.componentIndexes.entrySet()) {
              if(intCompPair.getValue().contains(vertex)){
                  Componente comp = intCompPair.getValue();
                  int compIndex = intCompPair.getKey();
                  System.out.println(comp.toVertice().getLabel());

                  for (int i = 0; i < this.adjMatrix.get(index).size(); i++) {
                      if(this.adjMatrix.get(index).get(i) && !comp.contains(this.vertexIndexes.get(i))){
                          System.out.print("Achou ADJ: ");
                          for (Map.Entry<Integer, Componente> intCompEntry : this.componentIndexes.entrySet()) {
                              if(intCompEntry.getValue().contains(this.vertexIndexes.get(i))){
                                  System.out.print(intCompEntry.getValue().toVertice().getLabel() + "\n");
                                  this.componentAdjMatrix.get(compIndex).set(intCompEntry.getKey(), true);
                                  break;
                              }
                          }
                      }
                  }

                  break;
              }
           }

       }
   }

    @Override
    public GrafoMatriz getTranspose() {
        Map<Vertice, List<Vertice>> transposeList = new HashMap<>();

        printGraph();

        System.out.println("AAAA");
        for (int i = 0; i < adjMatrix.size(); i++) {
           if(!transposeList.containsKey(vertexIndexes.get(i))){
               transposeList.put(vertexIndexes.get(i), new ArrayList<>());
           }
            for (int j = 0; j < adjMatrix.get(i).size(); j++) {
                if(adjMatrix.get(i).get(j)){
                    if(!transposeList.containsKey(vertexIndexes.get(j))){
                        List<Vertice> adj = new ArrayList<>();
                        adj.add(vertexIndexes.get(i));
                        transposeList.put(vertexIndexes.get(j), adj);
                    } else {
                       transposeList.get(vertexIndexes.get(j)).add(vertexIndexes.get(i));
                    }
                }
            }
        }
        for (Map.Entry<Vertice, List<Vertice>> verticeListEntry : transposeList.entrySet()) {
           System.out.print(verticeListEntry.getKey().getLabel() + " : ");
            for (Vertice vertice : verticeListEntry.getValue()) {
               System.out.print(vertice.getLabel() + " ");
            }
            System.out.println();
        }

        GrafoMatriz transpose = new GrafoMatriz();
        transpose.readAdjList(transposeList);

        return transpose;
    }

    @Override
    public void readAdjList(Map<Vertice, List<Vertice>> adjList) {
        // Map que relaciona cada vertice a um index
        this.vertexIndexes = new HashMap<Integer, Vertice>();

        // Matriz de adjascencia
        this.adjMatrix = new ArrayList<List<Boolean>>(adjList.size());

        // Loop que atribui um index para cada vertice e tambem a ele uma lista com valores falsos
        int i = 0;
        for(Vertice v : adjList.keySet()) {
            this.vertexIndexes.put(i, new Vertice(v.getLabel()));
            ArrayList<Boolean> adjRow = new ArrayList<Boolean>(Collections.nCopies(adjList.size(), false));
            this.adjMatrix.add(i, adjRow);
            i++;
        }
        // Loop que pega os adjascentes de cada vertice e atribui valor TRUE na matriz de adjascencia
        for(Map.Entry<Vertice, List<Vertice>> entry : adjList.entrySet()) {
            int keyIndex = 0;
            // Pega o index do vertice
            for(Map.Entry<Integer, Vertice> indexEntry : vertexIndexes.entrySet()) {
                if(entry.getKey().equals(indexEntry.getValue())){
                   keyIndex = indexEntry.getKey();
                   break;
                }
            }

            // Para cada um de seus adjascentes descobre os seus index e
            // atribui os valores TRUE no lugar correspondente
            for (Vertice v : entry.getValue()) {
                for(Map.Entry<Integer, Vertice> indexEntry : vertexIndexes.entrySet()) {
                    if(v.equals(indexEntry.getValue())){
                        int valueIndex = indexEntry.getKey();
                        adjMatrix.get(keyIndex).set(valueIndex, true);
                        break;
                    }
                }
            };
        }
    }

    @Override
    public void printGraph() {
        // int columnSize = this.vertexIndexes.get(0).getLabel().length();
        System.out.printf("    ");
        for(Map.Entry<Integer, Vertice> entry : this.vertexIndexes.entrySet()) {
            System.out.printf("%s  ", entry.getValue().getLabel());
        }
        System.out.printf("\n");

        for(Map.Entry<Integer, Vertice> entry : this.vertexIndexes.entrySet()) {
            System.out.printf("%s  ", entry.getValue().getLabel());
            this.adjMatrix.get(entry.getKey()).forEach(v -> {
                if(v) System.out.print("1  ");
                else System.out.print("0  ");
            });
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }

    public void printCAL() {
        // int columnSize = this.vertexIndexes.get(0).getLabel().length();
        System.out.printf("    ");
        for(Map.Entry<Integer, Componente> entry : this.componentIndexes.entrySet()) {
            System.out.printf("%s  ", entry.getValue().toVertice().getLabel());
        }
        System.out.printf("\n");

        for(Map.Entry<Integer, Componente> entry : this.componentIndexes.entrySet()) {
            System.out.printf("%s  ", entry.getValue().toVertice().getLabel());
            this.componentAdjMatrix.get(entry.getKey()).forEach(v -> {
                if(v) System.out.print("1  ");
                else System.out.print("0  ");
            });
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }
}
