package Grafos;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class GrafoMatriz implements Grafo{

   private List<List<Boolean>> adjMatrix;
   private Map<Integer, Vertice> vertexIndexes;

    public List<List<Boolean>> getAdjMatrix() {
        return adjMatrix;
    }

    @Override
    public Grafo getTranspose() {
        return null;
    }

    @Override
    public void readAdjList(Map<Vertice, List<Vertice>> adjList) {
        // Map que relaciona cada vertice a um index
        this.vertexIndexes = new HashMap<Integer, Vertice>();

        // Matriz de adjascencia
        this.adjMatrix = new ArrayList<List<Boolean>>(adjList.size());

        // Loop que atribui um index para cada vertice e tambem a ele uma lista com valores falsos
        int i = 0;
        for(Map.Entry<Vertice, List<Vertice>> entry : adjList.entrySet()) {
            this.vertexIndexes.put(i, new Vertice(entry.getKey().getLabel()));
            ArrayList<Boolean> adjRow = new ArrayList<Boolean>(Collections.nCopies(adjList.size(), false));
            this.adjMatrix.add(i, adjRow);
            i++;
        }
        // Loop que pega os adjascentes de cada vertice e atribui valor TRUE na matriz de adjascencia
        for(Map.Entry<Vertice, List<Vertice>> entry : adjList.entrySet()) {
            int keyIndex = 0;
            // Pega o index do vertice
            for(Map.Entry<Integer, Vertice> indexEntry : vertexIndexes.entrySet()) {
                if(entry.getKey().compareTo(indexEntry.getValue()) == 0){
                   keyIndex = indexEntry.getKey();
                   break;
                }
            }

            int finalKeyIndex = keyIndex;
            // Para cada um de seus adjascentes descobre os seus index e
            // atribui os valores TRUE no lugar correspondente
            entry.getValue().forEach(v -> {
                int valueIndex = 0;
                for(Map.Entry<Integer, Vertice> indexEntry : vertexIndexes.entrySet()) {
                    if(v.compareTo(indexEntry.getValue()) == 0){
                        valueIndex = indexEntry.getKey();
                        break;
                    }
                }
                adjMatrix.get(finalKeyIndex).add(valueIndex, true);
            });
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
                if(v) System.out.printf("1  ");
                else System.out.printf("0  ");
            });
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }
}
