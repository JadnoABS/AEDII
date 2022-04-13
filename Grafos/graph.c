// ESSE CODIGO NAO RODA!! APENAS PARA ANOTACOES DE AULA!!

#include <stdio.h>
#include <stdlib.h>

#define TAM_GRAFO 10
#define INFINITY 1000000000

typedef struct no {
  struct no *prox;
  int chave;
  int index;
} NO;

typedef struct grafo {
  NO V[TAM_GRAFO];
} GRAFO;

char color[TAM_GRAFO];
int d[TAM_GRAFO];
int pai[TAM_GRAFO];

void inserirNaFila(int s);
int removerDaFIla();
int filaVazia();

void print(int s);

void printPath(GRAFO G, int s, int v) {
  if (s == v)
    print(s);
  else if (pai[v] == -1) {
    return print(-1);
  } else {
    printPath(G, s, pai[v]);
  }
}

// Complexidade da busca por largura: O(V+A)
// A = (V² - V)/2
// Logo, O(V+A) = O(V²)

void buscaLargura(GRAFO G, int vertice) {

  for (int i = 0; i < TAM_GRAFO; i++) {
    if (i == vertice)
      continue;
    color[i] = 'w';
    d[i] = INFINITY;
    pai[i] = -1;
  }

  color[vertice] = 'g';
  d[vertice] = 0;
  pai[vertice] = -1;

  inserirNaFila(vertice);
  while (!filaVazia()) {
    int u = removerDaFIla();
    NO *adj = NULL;
    do {
      adj = G.V[u].prox;
      if (color[adj->index] == 'w') {
        color[adj->index] = 'g';
        d[adj->index] = d[u] + 1;
        pai[adj->index] = u;
        inserirNaFila(adj->index);
      }
    } while (adj);
    color[u] = 'b';
  }
}

int time = 0;
int f[TAM_GRAFO];

void buscaProfundidade(GRAFO G, int u) {
  color[u] = 'g';
  time++;
  d[u] = time;

  NO *adj = NULL;
  do {
    adj = G.V[u].prox;
    if (color[adj->index] == 'w') {
      pai[adj->index] = u;
      buscaProfundidade(G, adj->index);
    }
  } while (adj);
  color[u] = 'b';
  time++;
  f[u] = time;
}

int main() { return 0; }
