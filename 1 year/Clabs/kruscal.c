#define _CRT_SECURE_NO_WARNINGS 
#define MAX_NODES 5001 // Максимальное количество вершин 
#include <stdio.h> 
#include <stdlib.h> 
typedef struct edge_t {
	int n1, n2; // направление 
	int l; // длина ребра 
} edge; // Ребра графа 
// Функция "сравнения" двух ребер, используемая для сортировки 
int cmp(const void *a, const void *b) {
	edge *c = (edge*)a, *d = (edge*)b;
	return (c->l - d->l);
}

/*
Берем число из массива "цветов", если оно меньше 0, то с данной вершиной еще никакая не связана( не зависит от нее), ставим цвет по номеру вершины и меняем last_n
Если больше 0, то с ней связана какая-то вершина, получаем цвет вершины, связанной с данной рекурсивно : берем цвета связанных вершин, пока не найдем ту, которая зависит от всех
Всем вершинам, от которых зависит посоедняя, даем ее цвет
*/
int getColor(int n, int *lastn, int*node) {
	int c;
	if (node[n] < 0)
		return node[(*lastn) = n];
	c = getColor(node[n] - 1, lastn, node);
	node[n] = (*lastn) + 1;
	return c;
	//если цвет вершины больше 0, то все вершины, с которыми она связана, рекурсивно получают одинаковый цвет
}

int main() {
	int i, NV, NE, last_n = 0;
	int* nodes; // Вершины графа. Значение - "верхняя вершина" 
	// Считываем вход 
	if (scanf("%d", &NV) == -1)//проверка на считывание 
	{
		printf("bad number of lines");
		return 0;
	}
	//проверка на правильное считывание 
	if ((NV < 0) || (NV >= MAX_NODES))
	{
		printf("bad number of vertices");
		return 0;
	}
	if (scanf("%d", &NE) == -1)
	{
		printf("bad number of lines");
		return 0;
	}
	if ((NE < 0) || (NE > ((NV*(NV + 1)) / 2)))
	{
		printf("bad number of edges");
		return 0;
	}
	if ((NV == 1) && (NE == 0))
		return 0;
	edge *edges;
	if (!(edges = (edge*)malloc(sizeof(edge)*NE)))
	{
		printf("memory error");
		return 0;
	}
	//массив ребер 
	if(!(nodes = (int*)malloc(sizeof(int)*NV)))//массив цветов вершин
	{
		printf("memory error");
		free(edges);
		return 0;
	}
	for (i = 0; i < NV; i++) nodes[i] = -1 - i;
	for (i = 0; i < NE; i++)
	{
		if (scanf("%d %d %d", &edges[i].n1, &edges[i].n2, &edges[i].l) != 3)
		{
			printf("bad number of lines");
			free(edges);
			free(nodes);
			return 0;
		}
		if ((edges[i].l < 0) || (edges[i].l > INT_MAX))
		{
			printf("bad length");
			free(edges);
			free(nodes);
			return 0;
		}
		if ((edges[i].n1 < 1) || (edges[i].n1 > NV) || (edges[i].n2 < 1) || (edges[i].n2 > NV))
		{
			printf("bad vertex");
			free(edges);
			free(nodes);
			return 0;
		}
	}
	edge *answer;
	if(!(answer = (edge*)malloc(sizeof(edge)*NV)))
	{
		printf("memory error");
		free(edges);
		free(nodes);
		return 0;
	}
	// Алгоритм Крускала 

	// Сортируем все ребра в порядке возрастания длин 
	qsort(edges, NE, sizeof(edge), cmp);
	int k = 0;
	for (i = 0; i < NE; i++) { // пока не прошли все ребра 
		int c2 = getColor((edges[i].n2) - 1, &last_n, nodes);// берем цвет конца (-1 т.к вершины начинаются с 1, а массив цветов с 0)
		if (getColor((edges[i].n1) - 1, &last_n, nodes) != c2)//сравниваем с цветом начала 
		{
			// Если ребро соединяет вершины различных цветов-мы его добавляем 
			// и перекрашиваем вершины 
			nodes[last_n] = edges[i].n2;
			answer[k] = edges[i];
			k++;
			if (k == NV - 1)
			{
				free(edges);
				for (int i = 0; i < k; i++)
					printf("%d %d\n", answer[i].n1, answer[i].n2);//как только нашли все ребра для каркаса печатаем их и выходим(ребер на 1 меньше чем вершин) 
				free(answer);
				free(nodes);
				return 0;
			}
		}
	}
	printf("no spanning tree"); //если не нашли каркас 
	free(edges);
	free(answer);
	free(nodes);
	return 0;
}