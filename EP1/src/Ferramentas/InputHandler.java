package Ferramentas;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import Grafos.Vertice;

public class InputHandler{

    public boolean isListGraph;
    public Map<Vertice, List<Vertice>> readGrafo() {
        Scanner input = new Scanner(System.in);
        Map<Vertice, List<Vertice>> adjList = new HashMap<Vertice, List<Vertice>>();
        Map<Vertice, List<Vertice>> auxList = new HashMap<Vertice, List<Vertice>>();

        int nOfVertices = Integer.parseInt(input.nextLine());
        // Para leitura de cada uma das linhas do input (cada vertice)
        for(int x = 0; x < nOfVertices; x++) {
            Vertice vertice;
            List<Vertice> verticeList = new ArrayList<Vertice>();
            String line = input.nextLine();
            String label;

            // Pega o label do vertice
            int i = 0;
            while(line.charAt(i) != ':') {
                i++;
            }
            label = line.substring(0, i);
            vertice = new Vertice(label);

            // Pega o label de cada um dos seus adjascentes
            int j = i;
            while(line.length() - 1 > j) {
                while (line.charAt(j) != ';') {
                    j++;
                }
                i += 2;
                verticeList.add(new Vertice(line.substring(i, j)));
                i = j;
                j++;
            }
            auxList.put(vertice, verticeList);
        }

        for (Map.Entry<Vertice, List<Vertice>> entry : auxList.entrySet()) {
            List<Vertice> verticeList = new ArrayList<>();
            entry.getValue().forEach(v -> {
                auxList.keySet().forEach(k -> {
                    if (v.getLabel().equals(k.getLabel())){
                        verticeList.add(k);
                    }
                });
            });
            adjList.put(entry.getKey(), verticeList);
        }

        this.isListGraph = input.nextLine().equals("1") ? true : false;

        return adjList;
    }
}
