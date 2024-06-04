#include <math.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct no {
  int key;
  struct no *prox;
} NO;

int main(int argc, char **argv) {

  int key;
  int m;
  double A = (sqrt(5) - 1) / 2;

  scanf("%d", &m);

  NO *T[m];

  if (argc > 0) {

    if (argv[1][1] == 'd') {

      scanf("%d", &key);
      while (key != 0) {
      }
    }
  }
}
