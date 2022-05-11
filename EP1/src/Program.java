import Grafos.*;
import Ferramentas.*;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Program {

    public static void main(String[] args) {

//        InOut leitorDeInput = new InOut();
//        GrafoLista grafo = new GrafoLista();
//        GrafoMatriz grafo2 = new GrafoMatriz();
//
//        Map<Vertice, List<Vertice>> adjList = leitorDeInput.readGrafo();
//        grafo.readAdjList(adjList);
//        grafo2.readAdjList(adjList);
//
//        grafo.printGraph();
//        System.out.println();
//        grafo2.printGraph();

        InOut leitorDeInput = new InOut();

        GrafoMatriz graph = new GrafoMatriz();
        graph.readAdjList(leitorDeInput.readGrafo());

        graph.printGraph();
        System.out.println();
        graph.getTranspose().printGraph();
        System.out.println();
        Grafo SCG = graph.Kosaraju();
        SCG.printGraph();


//        GrafoLista grafo = new GrafoLista();
//
//        Map<Vertice, List<Vertice>> adjList = leitorDeInput.readGrafo();
//        grafo.readAdjList(adjList);
//
//        Grafo SCG = grafo.Kosaraju();
//
//        SCG.printGraph();
    }

}