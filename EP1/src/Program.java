// EP 1
// Disciplina: ACH2024
// Turma: 2022103
// Integrantes:
//      Jadno Augusto Barbosa da Silva - NUSP: 12608618
//      Gustavo Macedo Ribeiro - NUSP: 11366336


import Grafos.*;
import Ferramentas.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        InputHandler leitorDeInput = new InputHandler();
        Map<Vertice, List<Vertice>> adjList = leitorDeInput.readGrafo();

        Grafo grafo;
        Grafo stronglyConnectedGraph;


        if(leitorDeInput.isListGraph) {

            grafo = new GrafoLista(adjList);

            stronglyConnectedGraph = grafo.Kosaraju();

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

            grafo = new GrafoMatriz(adjList);

            stronglyConnectedGraph = grafo.Kosaraju();

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

        if(args.length > 0){
            if(stronglyConnectedGraph != null && args[0].equals("covid")) {
                realLifeProblem(stronglyConnectedGraph);
            }
        }

    }

    public static void realLifeProblem(Grafo stronglyConnectedGraph) {

        System.out.println();
        System.out.println("A dinamica trasmissao de diversas doenças, como por exemplo a COVID-19, pode ser modelada a partir de grafos direcionados");
        System.out.println("Suponha que temos um grupo de pessoas formado por esses vertices previamente dados," +
                " cujas adjascencias representam as pessoas que este encontrou pessoalmente sem usar mascara, e que os componentes sejam aglomeraçoes dessas pessoas");
        System.out.println("Deve-se levar em conta que a aresta direcionada sai da pessoa que nao usa mascara para a pessoa que usa mascara");
        System.out.println("Digite o nome de um dos vertices para representar uma pessoa infectada:");

        Scanner in = new Scanner( System.in );
        String label = in.nextLine();

        boolean isValidInput = false;
        Vertice grupoInfectado = null;
        Map<Vertice, List<Vertice>> compMap = stronglyConnectedGraph.vertexMap();

        for (Vertice componente : compMap.keySet()) {
            if(componente.getLabel().contains(label)){
                isValidInput = true;
                grupoInfectado = componente;
            }
            componente.setColor('w');
            for (Vertice vertice : compMap.get(componente)) {
                vertice.setColor('w');
            }
        }

        if(isValidInput && grupoInfectado != null) {
            System.out.println("De acordo com os relacionamentos dessa pessoa, temos que os grupos futuramente infectados serao:");
            printAllInfected(grupoInfectado, compMap);
            System.out.println();
        }
    }
    public static void printAllInfected(Vertice comp, Map<Vertice, List<Vertice>> map) {
        if(comp.getColor().equals('w')){
            System.out.print(comp.getLabel() + " ");
            comp.setColor('b');
        }
        for (Vertice vertice : map.get(comp)) {
            printAllInfected(vertice, map);
        }
    }

}