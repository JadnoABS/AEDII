#include "headers.h"
#include <stdio.h>
int main(int argc, char const *argv[]) {

  bTree *tree = createTree("tree.dat", "data.dat", false);

  bool execute = true;
  while (execute) {

    char command[100];
    scanf("%s", command);
    switch (command[0]) {
    case 'I':;
      char bookData[100];
      scanf("%s", bookData);

      recordNode *bookRecord = getData(bookData, 1);
      insert(tree, bookRecord);
      traverse(tree, 0);
      break;
    case 'R':;
      int key;
      scanf("%d", &key);

      bTreeNode *treeNode = malloc(sizeof(bTreeNode));
      readFile(tree, treeNode, tree->root);

      removeNode(tree, treeNode, key);
      traverse(tree, 0);
      break;
    case 'B':;
      int k;
      scanf("%d", &k);

      recordNode *rec = search(tree, k);
      if (rec) {
        printf("%d %s %s %d\n", getIntKey(rec->codigoLivro), rec->titulo,
               rec->nomeCompletoPrimeiroAutor, rec->anoPublicacao);
      } else {
        printf("O livro com o código %d não existe\n", k);
      }
      break;
    case 'F':
      fclose(tree->fp);
      fclose(tree->datafp);
      execute = false;
      break;
    }
  }

  /*
  if (argc == 1) {
    printf("Please enter one of the options below:\n");
    printf("Commands:\n");
    printf("./run [OPTIONS]\n");
    printf("-b [KEY] -->for building a tree.\n");
    printf("-s [KEY] -->for searching a [KEY].\n");
    printf("-d [KEY] -->for deleting a [KEY].\n");
    printf("Exiting now\n");
    exit(0);
  }

  int len, i;
  printf("How many records do you want to build from dataset?");
  scanf("%d", &len);

  recordNode *records = getData("data/dataset.csv", len);
  bTree *tree = createTree("tree.dat", false);

  for (i = 0; i < len; i++) {
    insert(tree, &records[i]);
  }

  if (!strcmp(argv[1], "-d")) {
    int key;
    sscanf(argv[2], "%d", &key);
    bool res = removeFromTree(tree, key);
    if (res) {
      printf("Successfull Deletion.\n");
    } else {
      printf("Deletion not successful.\n");
    }
  }

  traverse(tree, tree->root);

  if (!strcmp(argv[1], "-s")) {
    // printf("Time Taken to build tree: %f seconds\n",timeToBuild);
    int key;
    sscanf(argv[2], "%d", &key);
    recordNode *res = search(tree, key);

    if (res != NULL) {
      printf("key\tcountry\tgrate\tscore\trate\n");
      printf("%d\t", res->key);
      printf("%s\t", res->country);
      printf("%s\t", res->grate);
      printf("%d\t", res->score);
      printf("%d\n", res->rate);

      free(res);
    } else
      printf("Record not found!");
  }

  free(records);
  free(tree);
  */
}
