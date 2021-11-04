#define _CRT_SECURE_NO_WARNINGS 
#define MAX_NODES 5001 // ������������ ���������� ������ 
#define UINT_MAX 4292967295
#include <stdio.h> 
#include <stdlib.h> 
#include "D:\GIT\c-18208kopia2\LaboratoryWorks\Lab_3\find_player.h"
// ������� "���������" ���� �����, ������������ ��� ���������� 
void swap(char* a, char* b) {
	char t = *a;
	*a = *b;
	*b = t;
}
//����������
int cmp_path(const void* a, const void* b) {
	p* c = (p*)a, * z = (p*)b;
	if (c->length > z->length)
		return 1;
	else if (c->length < z->length)
		return -1;
	if (c->value > z->value)
		return 1;
	return -1;
}
//������� �������� ������, ������, �.� ��� ������ ����� ������������� ������������ 
void cleanspace(path1* a, p* b, unsigned int** e, int N) {
	free(a);
	free(b);
	for (int i = 0; i < N; i++)
		free(e[i]);
	free(e);
}
char deikstra(int NV, int NE, p* paths, int S, int F, unsigned int** edges, path1* path) {
	char pat = 0;
	// ��������� ��� ����� � ������� ����������� ���� 
	qsort(paths, NV, sizeof(p), cmp_path);
	if (NE != 0)//���� ���� �����, �������� � ����
	{
		int t = 1;//������������� ��� ����������, ��������� � ��������, �� ����� ���� �� ��� � ���������, ��� �������� � �� ����� ���������, �������� � 1, �.� ����������
		//����� 0 ����� � �������(S), ���������� �� ������� ����, �� ����� �� �������������, ��� ��� ��� ����� �� ��� ����� ���������� �� ���� �������(S) �� ����
		while (t < NV)//���� �� ����������� ��� �������
		{
			int i = paths[t].value;//����� ������� � ����� �������� ����������� �� S � ������������� ���� ������ ���� ������ ������ �� S ����� ������ i
			int k = 0;
			if (i != S)//���� ����� S, �� ���������� ���������� �� ���� ������ �� i �� ������ ���� ��� ���������, ���������� 
			{
				for (k; k < NV; k++)//��������� ��� �������
				{
					if ((k != S) && (edges[k][i] != 0) && (path[i].length != UINT_MAX) && (edges[k][i] != (unsigned int)(INT_MAX + 1)))
						//1.���������� ��������� �� i �� S ��� �������, ���������� 2. ���� ����� �� ����� 0 � ������ k=i
						//3. ���� ���������� ���� �� i � S, ����� ����������� ������ ���� ����� ������� S 4. ���� ���������� ���� �� � � i, ����� ����������� �������������
					{
						unsigned int K = edges[k][i];//����� ���������� ����� i � k
						if ((path[i].length + K > INT_MAX) && (k == F))//���� �� ���� ���������� �� ����� F, �� ��������� ���������� ���������� >MAXINT �� �������
							pat++;
						if (path[k].length > path[i].length + K)
						{//���� ������ ���������� �� � ������, ��� ���������� �� � ����� ������� i(�.� �����), � ���� ����� i ����������
							K = path[i].length + K; //������ ���� �� �
							if (K <= INT_MAX)
								path[k].length = K;//���� �� ������ MAXINT, �� ������ ����� ���
							else path[k].length = (unsigned int)(INT_MAX + 1);//INT_MAX+1 - ���������� ����, ��� ���������� ������ INT_MAX, INT_MAX+2 - ���� � ������ ������� �� ���������
							path[k].before = i;//����������, ����� ����� ������� ���� � ������ ����� 
						}
					}
				}
			}
			t++;//����������� �������, ����� �� ����������� ��������������
			if ((i != S) && (k != S))//���� ���-�� ����������( �� ����� � ����� �� ������ ��������), ���� ����� ����������� ������ �.� � ��������� ����� ����� ����� ����������
			{
				for (int j = 0; j < NV; j++)
					paths[j].length = path[paths[j].value].length;//�������� � �������� ������� ����� �� �����
				qsort(paths + t, NV - t, sizeof(p), cmp_path);//��������� 
			}
		}
	}
	return pat;//��������� ������� 
}


int deikstra_main(char* array, int S, int F, int NV)
{
	/*int S, F;//start,final

	int** numbers = (int**)malloc(sizeof(int*));

	int NV = 0;
	for (int i = 0; i < number_of_simbols; i++) {
		if (array[i]) NV++;
		if ((i % 32 == x_start) && (i / 32 == y_start)) S = NV;
		if ((i % 32 == x_end) && (i / 32 == y_end)) F = NV;
	}
	for (int i = 0; i < number_of_simbols / 32 - 1; i++)
		for (int j = 0; j < number_of_simbols / 32 - 1; j++) {
			const bool is_here_there_is_an_vertical_edge = ((array[i * 32 + j]) && (array[i * 32 + j + 1]));
			const bool is_here_there_is_an_horizontal_edge = ((array[i * 32 + j]) && (array[i * 32 + j + 32])
			if (is_here_there_is_an_vertical_edge)
		}*/


	path1* path;
	if (!(path = (path1*)malloc(sizeof(path1) * NV)))//������ ���������� ���������� �� ����� S �� ���� ������
	{
		printf("memory error");
		return 0;
	}
	p* paths;//�������� ������ ��� �������� ���������� 
	if (!(paths = (p*)malloc(sizeof(p) * NV)))
	{
		printf("memory error");
		free(path);
		return 0;
	}

	for (int i = 0; i < NV; i++)
	{
		paths[i].value = i;
		path[i].before = S - 1;//������ ���� ���������� ������� S, ��� ��� ����� ����� ���������� �� ��� 
		path[i].length = UINT_MAX;//���� ��� ������� ���� �� S, ���  �������� ������ 
		paths[i].length = UINT_MAX;
	}
	path[S - 1].length = 0;
	paths[S - 1].length = 0;
	//�� ��������� ���������� �� ����� ������� �� ��� �� ����� 0
	unsigned int** edges;//������� ���������� ����� ��������� 
	if (!(edges = (unsigned int**)malloc(sizeof(unsigned int*) * NV)))//NV+1 ����� ������� ������� � 1
	{
		printf("memory error");
		free(path);
		free(paths);
		return 0;
	}
	for (int i = 0; i < NV; i++)
	{
		if (!(edges[i] = (unsigned int*)malloc(sizeof(unsigned int) * NV)))
		{
			printf("memory error");
			free(path);
			free(paths);
			for (int j = 0; j < i; j++)
				free(edges[j]);
			free(edges);
			return 0;
		}
		for (int j = 0; j < NV; j++)
		{
			edges[i][j] = INT_MAX + 1;//���� ��� ������� ���� ����� ��������� 
			edges[i][i] = 0;
		}
	}
	//INT_MAX+1,���� �� �������� � ������ �������( ��� �����0


	//�������������� ������ ����
	for (int i = 0; i < 256; i++)
		for (int j = 0; j < 256; j++)
			edges[i][j] = 1000000;

	//��������� ������������ ����
	for (int i = 0; i < 15; i++) {
		if (!array[i * 16])	if (!array[i * 16 + 16]) 
			{ edges[i * 16][i * 16 + 16] = 1; edges[i * 16 + 16][i * 16] = 1; 
			if (i * 16 == S) { path[i * 16 + 16 - 1].length = 1; paths[i * 16 + 16 - 1].length = 1; }
			if (i * 16 + 16 - 1 == S) { path[i * 16].length = 1; paths[i * 16].length = 1; }
			}
		if (!array[i]) if (!array[i + 1]) 
			{ edges[i][i + 1] = 1; edges[i + 1][i] = 1; 
			if (i== S) { path[i + 1].length = 1; paths[i + 1].length = 1; }
			if (i + 1 == S) { path[i ].length = 1; paths[i].length = 1; }
			}
		if (!array[255 - i]) if (!array[255 - i - 1]) 
			{ edges[255 - i][255 - i - 1] = 1; edges[255 - i - 1][255 - i] = 1; 
			if (255 - i == S) { path[255 - i - 1].length = 1; paths[255 - i - 1].length = 1; }
			if (255 - i - 1 == S) { path[255 - i].length = 1; paths[255 - i].length = 1; }
			}
		if (!array[255 - i * 16]) if (!array[255 - i * 16 - 16]) 
			{ edges[255 - i * 16][255 - i * 16 - 16] = 1; 
			if (255 - i * 16 == S) { path[255 - i * 16 - 16].length = 1; paths[255 - i * 16 - 16].length = 1; }
			if (255 - i * 16 - 16 == S) { path[255 - i * 16].length = 1; paths[255 - i * 16].length = 1; }
			}

	}
	for (int i = 1; i < 15; i++)
		for(int j = 1; j < 15; j++)
			if (!array[i * 16 + j]) {
				if (!array[i * 16 + j - 1]) 
					{ edges[i * 16 + j][i * 16 + 1 + j] = 1; edges[i * 16 + 1 + j][i * 16 + j] = 1; 
					if (i * 16 + j == S) { path[i * 16 + j - 1].length = 1; paths[i * 16 + j - 1].length = 1; }
					}
				if (!array[i * 16 + j - 16]) 
					{ edges[i * 16 + j][i * 16 - 16 + j] = 1; edges[i * 16 - 16 + j][i * 16 + j] = 1; 
					if (i * 16 + j == S) { path[i * 16 + j - 16].length = 1; paths[i * 16 + j - 16].length = 1; }
					}
				if (!array[i * 16 + j + 1]) 
					{ edges[i * 16 + j][i * 16 - 1 + j] = 1; edges[i * 16 - 1 + j][i * 16 + j] = 1; 
					if (i * 16 + j == S) { path[i * 16 + j + 1].length = 1; paths[i * 16 + j + 1].length = 1; }
					}
				if (!array[i * 16 + j + 16]) 
					{ edges[i * 16 + j][i * 16 + 16 + j] = 1; edges[i * 16 + 16 + j][i * 16 + j] = 1;
					if (i * 16 + j == S) { path[i * 16 + j + 16].length = 1; paths[i * 16 + j + 16].length = 1; }
					}
			}


	/*for (int i = 0; i < NE; i++)//��������� ����� 
	{
		
		if ((A == S) && (A != B))
		{
			path[B - 1].length = L;
			paths[B - 1].length = L;//���������� ���������� �� S �� ������, � �������� ��� ����� �������, ���� ����� ���, �� ���������� ����+2
		}
		if ((B == S) && (A != B))
		{
			path[A - 1].length = L;
			paths[A - 1].length = L;
		}
		edges[A - 1][B - 1] = L;
		edges[B - 1][A - 1] = L;
	}*/
	char pat = deikstra(NV, 452, paths, S, F, edges, path);
	//�����!
	for (int i = 0; i < NV; i++)
	{

		if (path[i].length == UINT_MAX)
			printf("oo ");//���� UINT_MAX, �� ���������� �� S �� ������ ����� �� ���������� ( ���� ����� ������, �.� �� �������� ��������)
		else if (path[i].length == INT_MAX + 1)
			printf("INT_MAX+ ");//���� ���������� ����, �� ��� ������ INT_MAX
		else printf("%d ", path[i].length);//���� ���������� <= INT_MAX
	}
	printf("\n");
	if ((path[F - 1].length == INT_MAX + 1) && (pat >= 2))//�������� ������� �� ���������� ������ INT_MAX
	{
		printf("overflow");
		cleanspace(path, paths, edges, NV);
		return 0;
	}
	if (path[F - 1].length == UINT_MAX)//���� ��� ���������� 
	{
		printf("no path");
		cleanspace(path, paths, edges, NV);
		return 0;
	}
	printf("%d ", F);//�������� ����� � ������� F
	int K = F - 1;
	int answer[2];
	while (K != S - 1)//���� �� ����� �� S ������� ��� �������, ����� ������� ���� 
	{
		answer[0] = answer[1];
		answer[1] = path[K].before + 1;
		printf("%d ", path[K].before + 1);
		K = path[K].before;
	}
	printf("\n\n%d %d", answer[0], answer[1]);
	cleanspace(path, paths, edges, NV);
	return answer[0];
}