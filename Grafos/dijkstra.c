#include <stdbool.h>

#define MAX_VERTICES 10
#define INFINITY 10000000

typedef int TIPOCHAVE;

typedef struct Vertice {
  TIPOCHAVE chave;
  int index;
  int peso;
  struct Vertice *anterior;
  bool temporario;
} Vertice;

typedef struct {
  Vertice *vertices[MAX_VERTICES];
  int adjMatrix[MAX_VERTICES][MAX_VERTICES];
  int nVertices;
} Grafo;

Vertice *r;

void relax(Grafo *g, Vertice *v, Vertice *w) {
  if (w->peso > v->peso + g->adjMatrix[v->index][w->index]) {
    w->peso = v->peso + g->adjMatrix[v->index][w->index];
    w->anterior = v;
  }
}

void dijkstra(Vertice *v, Vertice *w, Grafo *g) {

  for (int i = 0; i < g->nVertices; i++) {
    g->vertices[i]->peso = INFINITY;
    g->vertices[i]->anterior = -1;
    g->vertices[i]->temporario = true;
  }
  v->peso = 0;

  int verticesRodados = 0;
  while (verticesRodados < g->nVertices) {
    Vertice *r;
    int peso = INFINITY;
    for (int i = 1; i < g->nVertices; i++) {
      if (g->vertices[i]->temporario && g->vertices[i]->peso < peso) {
        peso = g->vertices[i]->peso;
        r = g->vertices[i];
      }
    }

    for (int i = 0; i < g->nVertices; i++) {
      if (g->adjMatrix[r->index][i] > 0) {
        relax(g, r, g->vertices[i]);
      }
    }
    r->temporario = false;
    verticesRodados++;
  }
}
