#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct rec {
  char codigoLivro[7]; // chave
  char titulo[30];
  char nomeCompletoPrimeiroAutor[30];
  int anoPublicacao;
};
typedef struct rec recordNode;

struct bTreeNode {
  char validation;
  bool isLeaf;
  int pos;
  int noOfRecs;
  int keyRecArr[2 * t - 1]; // as chaves dos registros, que são no máximo 2t-1
  int posRecArr[2 * t - 1]; // posições dos registros no arquivo data.dat, que
                            // são no máximo 2t-1
  int children[2 * t]; // posições das páginas filhas no arquivo tree.dat, que
                       // são no máximo 2t
};
typedef struct bTreeNode bTreeNode;

struct tree {
  char fileName[20];
  FILE *fp;
  char dataFileName[20];
  FILE *datafp;
  int root;
  int nextPos;
  int dataNextPos;
};
typedef struct tree bTree;

bTree *createTree(char *fileName, char *dataFileName, bool mode);
bTreeNode *nodeInit(bTreeNode *node, bool isLeaf, bTree *tree);
void insert(bTree *tree, recordNode *record);
void writeFile(bTree *ptr_tree, bTreeNode *p, int pos);
void readFile(bTree *ptr_tree, bTreeNode *p, int pos);

void enterData(recordNode *record, int key, char titulo[],
               char nomeCompletoPrimeiroAutor[], int anoPublicacao);
recordNode *getData(char *filepath, int len);
recordNode *search(bTree *tree, int key);
recordNode *searchRecursive(bTree *tree, int key, bTreeNode *root);
bool removeFromTree(bTree *tree, int key);
bTreeNode *merge(bTree *tree, bTreeNode *node, int idx);
void borrowFromNext(bTree *tree, bTreeNode *node, int idx);
void borrowFromPrev(bTree *tree, bTreeNode *node, int idx);
void fill(bTree *tree, bTreeNode *node, int idx);
int getSucc(bTree *tree, bTreeNode *node, int idx);
int getPred(bTree *tree, bTreeNode *node, int idx);
void removeFromNonLeaf(bTree *tree, bTreeNode *node, int idx);
void removeFromLeaf(bTree *tree, bTreeNode *node, int idx);
void removeNode(bTree *tree, bTreeNode *node, int k);
int findKey(bTreeNode *node, int k);
