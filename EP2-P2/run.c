// EXERCICIO PROGRAMA 2 - PARTE 2
// DISCIPLINA: AEDII - ACH2024
// INTEGRANTES:
//    GUSTAVO ALMEIDA
//    JADNO BARBOSA

#include "headers.h"
#include <stdio.h>
#include <stdlib.h>
int main(int argc, char const *argv[]) {

  bTree *tree = createTree("tree.dat", "data.dat", false);

  bool execute = true;
  while (execute) {

    char command[100];
    scanf("%s", command);

    recordNode *rec = malloc(sizeof(recordNode));

    switch (command[0]) {
    // INSERIR LIVRO
    case 'I':;
      char bookData[100];
      fgets(bookData, sizeof(char) * 100, stdin);

      recordNode *bookRecord = getData(bookData, 1);
      insert(tree, bookRecord);
      break;

    // REMOVER LIVRO
    case 'R':;
      int key;
      scanf("%d", &key);

      bTreeNode *treeNode = malloc(sizeof(bTreeNode));
      readFile(tree, treeNode, tree->root);

      removeNode(tree, treeNode, key);
      break;

    // BUSCAR LIVRO
    case 'B':;
      int k;
      scanf("%d", &k);

      rec = search(tree, k);
      if (rec) {
        printf("%d %s %s %d\n", getIntKey(rec->codigoLivro), rec->titulo,
               rec->nomeCompletoPrimeiroAutor, rec->anoPublicacao);
      } else {
        printf("O livro com o código %d não existe\n", k);
      }
      break;

    // FECHAR O PROGRAMA
    case 'F':
      fclose(tree->fp);
      fclose(tree->datafp);
      execute = false;
      break;

    case 'P':
      switch (command[1]) {

      // LISTAR PAGINAS DO INDICE
      case '1':;
        bTreeNode *page = malloc(sizeof(bTreeNode));
        int pos = 0;
        while (pos < tree->nextPos) {
          readFile(tree, page, pos);
          if (page->validation != '#') {
            printf("%c %s %d %d ", page->validation,
                   page->isLeaf ? "true" : "false", page->pos, page->noOfRecs);
            for (int i = 0; i < page->noOfRecs; i++) {
              printf("%d ", page->keyRecArr[i]);
            }
            for (int i = 0; i < page->noOfRecs; i++) {
              printf("%d ", page->posRecArr[i]);
            }
            for (int i = 0; i < 2 * t; i++) {
              printf("%d ", page->children[i]);
            }
            printf("\n");
          }
          pos++;
        }
        free(page);

        break;

      // LISTAR PAGINAS DO INDICE INCLUINDO AS QUE FORAM ELIMINADAS
      case '2':;
        page = malloc(sizeof(bTreeNode));
        pos = 0;
        while (pos < tree->nextPos) {
          readFile(tree, page, pos);
          printf("%c %s %d %d ", page->validation,
                 page->isLeaf ? "true" : "false", page->pos, page->noOfRecs);
          for (int i = 0; i < page->noOfRecs; i++) {
            printf("%d ", page->keyRecArr[i]);
          }
          for (int i = 0; i < page->noOfRecs; i++) {
            printf("%d ", page->posRecArr[i]);
          }
          for (int i = 0; i < 2 * t; i++) {
            printf("%d ", page->children[i]);
          }
          printf("\n");
          pos++;
        }
        free(page);

        break;

      // LISTAR REGISTROS INCLUINDO OS ELIMINADOS
      case '3':;
        pos = 0;
        while (pos < tree->dataNextPos) {
          readData(tree, rec, pos);
          bool numberStarted = false;
          for (int i = 0; i < 7; i++) {
            if (rec->codigoLivro[i] == '0' && !numberStarted) {
              continue;
            }
            printf("%c", rec->codigoLivro[i]);
            if (i > 0)
              numberStarted = true;
          }
          printf(" %s %s %d\n", rec->titulo, rec->nomeCompletoPrimeiroAutor,
                 rec->anoPublicacao);
          pos++;
        }
        break;
      }
      break;
    }

    free(rec);
  }

  free(tree);
  return 0;
}
