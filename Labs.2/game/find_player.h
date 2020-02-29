#pragma once
//структура расстояния от вершины до вводимой S
typedef struct path_1 {
	unsigned int length;//длина
	short int before;//вершина, через которую идет кратчайший путь из данной вершини в нужную - S
}path1;
//массив вершин для сортировки, для более удобного использования, чтобы исходный массив не мешался при сортировке 2 массива разных структур
typedef struct sortp {
	short int value;
	unsigned int length;
}p;
void swap(char* a, char* b);
int cmp_path(const void* a, const void* b);
void cleanspace(path1* a, p* b, unsigned int** e, int N);
char deikstra(int NV, int NE, p* paths, int S, int F, unsigned int** edges, path1* path);
int deikstra_main(char* array, int S, int F, int NV);