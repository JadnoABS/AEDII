import Grafos.*;
import Ferramentas.*;

import java.util.List;
import java.util.Map;

public class Program {

    public static void main(String[] args) {

        InputHandler leitorDeInput = new InputHandler();
        Map<Vertice, List<Vertice>> adjList = leitorDeInput.readGrafo();


        if(leitorDeInput.isListGraph) {

            GrafoLista grafo = new GrafoLista(adjList);

            GrafoLista stronglyConnectedGraph = grafo.Kosaraju();

            List<Vertice> sortedVertexes = stronglyConnectedGraph.topologicalSort();

            if(sortedVertexes.size() == 1)
                System.out.println("Sim");
            else
                System.out.println("Não");

            System.out.println(sortedVertexes.size());

            for (Vertice v : sortedVertexes) {
               System.out.print(v.getLabel() + " ");
            }
            System.out.println();

            stronglyConnectedGraph.printGraph();
        } else {

            GrafoMatriz grafo = new GrafoMatriz(adjList);

            GrafoMatriz stronglyConnectedGraph = grafo.Kosaraju();

            List<Vertice> sortedVertexes = stronglyConnectedGraph.topologicalSort();

            if(sortedVertexes.size() == 1)
                System.out.println("Sim");
            else
                System.out.println("Não");

            System.out.println(sortedVertexes.size());

            for (Vertice v : sortedVertexes) {
                System.out.print(v.getLabel() + " ");
            }
            System.out.println();

            stronglyConnectedGraph.printGraph();
        }

    }

}