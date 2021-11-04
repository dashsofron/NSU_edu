#define _CRT_SECURE_NO_WARNINGS 
#include <stdio.h> 
#include <stdlib.h> 
void* my_malloc(size_t size)
{
	static int allocs = 0;
	if (allocs > 20)
		return NULL;
	allocs++;
	return malloc(size);
}
#define malloc my_malloc
//структура авл дерева  
typedef struct bbtree
{
	int value;
	struct bbtree *left, *right;
	unsigned char height;
} btree;
void cleanspace(btree* tree)
{
	if (tree == NULL)
		return;
	cleanspace(tree->left);
	cleanspace(tree->right);
	free(tree);
}
unsigned char height(btree *tree)
{
	unsigned char height = 0;
	if (tree != NULL)
		height = tree->height;
	return height;
}
void fixheight(btree* tree)
{
	unsigned char hl = height(tree->left);
	unsigned char hr = height(tree->right);
	if (hl > hr)
		tree->height = hl + 1;
	else tree->height = hr + 1;
}
btree *turnr(btree *tree)
{
	btree *q = tree->left;
	tree->left = q->right;
	q->right = tree;
	fixheight(tree);
	fixheight(q);
	return q;
}
//правый малый поворот  
btree *turnl(btree *tree)
{
	btree *q = tree->right;
	tree->right = q->left;
	q->left = tree;
	fixheight(tree);
	fixheight(q);
	return q;
}
//левый малый поворот 
btree *turnbl(btree *tree)
{
	tree->right = turnr(tree->right);
	return turnl(tree);
}
//большой левый поворот - малый левый от малого правого 
btree *turnbr(btree *tree)
{
	tree->left = turnl(tree->left);
	return turnr(tree);
}
//большой правый поворот - малый правый от малого левого
int sub(btree *one)
{
	unsigned char left = 0;
	unsigned char right = 0;
	if (one == NULL)
		return 0;
	if (one->left != NULL)
		left = height(one->left);
	if (one->right != NULL)
		right = height(one->right);
	int result = right - left;
	return result;
}
//разность высот левого и правого поддерева  
btree* create(int val)
{
	btree *newt;
	newt = (btree*)malloc(sizeof(btree));
	if (newt == NULL)
		return NULL;
	newt->value = val;
	newt->left = NULL;
	newt->right = NULL;
	newt->height = 1;
	return newt;
}
//создаем новое поддерево
btree* insert(btree *tree, int pushed)
{
	if (pushed <= tree->value)
	{
		if (tree->left != NULL)
			tree->left = insert(tree->left, pushed);
		else {
			tree->left = create(pushed);
			if (tree->left == NULL)
				return NULL;
			fixheight(tree);
			return tree;
		}
	}
	else
	{
		if (tree->right != NULL)
			tree->right = insert(tree->right, pushed);
		else {
			tree->right = create(pushed);
			if (tree->right == NULL)
				return NULL;
			fixheight(tree);
			return tree;
		}
	}
	fixheight(tree);
	int t = sub(tree);
	if (t == -2)
	{
		if (sub(tree->left) > 0)
			return turnbr(tree);
		return turnr(tree);
	}
	if (t == 2)
	{
		if (sub(tree->right) < 0)
			return turnbl(tree);
		return turnl(tree);
	}
	return tree;
}
//Опускаемся рекурсивно до нижнего листа, пока не найдем пустое место,вставляем новый элемент, фиксим высоты и проверяем на необходимость поворотов
int main()
{
	int DIG, count;
	scanf("%d", &count);
		if (count == 0)
		{
			printf("0");
			return 0;
		}
	btree *Tree = (btree*)malloc(sizeof(btree));
	if(Tree==NULL)
	{
		printf("error");
		return 7;
	}
	Tree->left = NULL;
	Tree->right = NULL;
	
	scanf("%d", &DIG);
	Tree->value = DIG;
	Tree->height = 1;
	for (int i = 0; i < count - 1; i++)
	{
		scanf("%d", &DIG);
		Tree = insert(Tree, DIG);
		if (Tree == NULL)
		{
			printf("memory error");
			cleanspace(Tree);
			return 7;
		}
	}
	printf("%d", Tree->height);
	//освобождение памяти  
	cleanspace(Tree);
	return 0;
}
