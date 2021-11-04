#define _CRT_SECURE_NO_WARNINGS 
#define MAX_NODES 5001 // Максимальное количество вершин 
#include <stdio.h> 
#include <stdlib.h> 
typedef struct edge_t {
	int n1, n2; // вершины ребра
	int l; // длина ребра 
} edge; // Ребра графа 
// Функция "сравнения" двух ребер, используемая для сортировки 
int cmp(const void *a, const void *b) {
	edge *c = (edge*)a, *d = (edge*)b;
	return (c->l - d->l);
}
void swap(edge* one, edge* two)
{
	edge temp = *one;
	*one = *two;
	*two = temp;
} //меняем местами ребра, тобы не проходить те, которые уже в каркасе
int kruskal(int*nodes,edge* edges,edge* answer,int NV,int NE)
{
int k = 0;//счетчик массива ответа
	nodes[edges[0].n2] = edges[0].n2;
	nodes[edges[0].n1] = edges[0].n2;
	//берем первое( кратчайшее) ребро и даем вершинам один цвет, цвет запоминаем
	int col = edges[0].n2;
	answer[k] = edges[0];
	k++;
	int t = 1;
	while ((k != NV - 1)&&(k==t))
	{
		int j = t; //от 0 до t лежат ребра, которые уже добавлены в массив ответов, поэтому их можно не учитывать и искать ребра начиая с t
		//ребра, которые прошли переносятся в ячейку t в конце
		for (j ; j < NE; j++)  //пробегаем все ребра в поиске наименьшего. Алгоритм предполагает поиск наименьшего ребра, которое соединено со строящимся каркасом
			//одни из вершин реьра закрашена
		{ // пока не прошли все ребра 
			int c2 = nodes[edges[j].n2]; 
			int c1 = nodes[edges[j].n1];
			if ((c1==col)&&(c2!=col))
				//берем 2 вершины, если цвет одной совпадает с цветом каркаса( одна из вершин находится в каркасе), добавляем ребро и меняем цвет второй вершины
			{

				nodes[edges[j].n2] = col;
				answer[k] = edges[j];
				k++;
				for (j; j > t; j--)
					swap(&edges[j], &edges[j - 1]);//после использованное ребро переносится в ячейку t, t=t+1 в конце цикла поэтому ээто ребро больше не учитывается
				break;
			}
			if ((c1 != col) && (c2 == col))//аналогично но совпала другая вершина
			{
				nodes[edges[j].n1] = col;
				answer[k] = edges[j];
				k++;
				for (j; j > t; j--)
					swap(&edges[j], &edges[j-1]);
				break;
			}
		}
		t++;//увеличиваем параметр t для пропуска используемого ребра
	}
	//каркс не предполагает зацикливания т.к не учитываются вершины одного цвета( только цвет оной совпадает с цветом вершин каркаса)
	//выход из цикла while осуществляется при соединении всех вершин ребрами каркаса( k=NV-1)
	//либо если мы пробежали весь массив ребер, но не нашли реьро удовлетворяющее условиям. В этом случае параметр t увеличится на 1 в конце
	// а k нет ( т.к к увеличивается только при нахождении ребра). Условие k!=t
	return k;
}
int main() {
	int i, NV, NE, last_n = 0;
	int* nodes; // массив цветов вершин 
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
	if (NV == 1)
		return 0;
	if  (NE == 0)
	{
		printf("no spanning tree");
		return 0;
	}
	// если 1 вершина, то каркас состоит из одной вершины и ничего выводить не надо.Eсли 0 ребер и не 1 вершина, то дерево не сортируется
	edge *edges = (edge*)malloc(sizeof(edge)*NE); // заполнен с 0
	//массив ребер 
	nodes = (int*)malloc(sizeof(int)*(NV+1));// заполняется с 1, т.к первая вершина - 1
	for (i = 0; i < NV; i++) nodes[i] = -1;
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
		if ((edges[i].n1 == edges[i].n2) && (NE == 1)&&(NV>1)) // если всего одно ребро, соединяющее 1 вершину а количество вершин больше 1
		{
			printf("no spanning tree");
				free(edges);
				free(nodes);
				return 0;
		}
		if (edges[i].n1 == edges[i].n2)
			/*зануляем вершины, чтобы данное ребро не учитывалось в каркасе, даем максмальную длину
			чтобы после сортирвки находилось дальше всех и не участвовало в построении каркаса*/
		{
			edges[i].n1 = 0;
			edges[i].n2 = 0;
			edges[i].l = INT_MAX;
		}
	}

	edge *answer = (edge*)calloc(NV,sizeof(edge));
	// Сортируем все ребра в порядке возрастания длин 
	qsort(edges, NE, sizeof(edge), cmp);
	int k = kruskal(nodes,edges, answer, NV,NE);
	free(edges);
	free(nodes);
	//после окончания поиска каркаса можно освободить память всех массивов кроме ответа
	//проверка на существование каркаса и выход, либо запись о том, что каркас составить невозможно
	
	if (k == NV - 1)//если все вершины соединены в каркасе, выводим его
	{
		for (int i = 0; i < k; i++)
			printf("%d %d\n", answer[i].n1, answer[i].n2);//как только нашли все ребря для каркаса печатаем их и выходим(ребер на 1 меньше чем вершин)
		free(answer);
		return 0;
	}
	printf("no spanning tree"); //если не нашли каркас 
	free(answer);
	return 0;
}