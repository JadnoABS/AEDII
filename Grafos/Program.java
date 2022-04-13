package Grafos;

public class Program {

  public static void main(String[] args) {

    Grafo grafo = new Grafo();

    grafo.generateRandom(10);

    Grafo transpose = grafo.getTraspose();

    grafo.imprimeMermaid();
    System.out.println();
    transpose.imprimeMermaid();

  }

}
