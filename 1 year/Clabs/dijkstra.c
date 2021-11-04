#define _CRT_SECURE_NO_WARNINGS 
#define MAX_NODES 5001 // ������������ ���������� ������ 
#define UINT_MAX 4292967295
#include <stdio.h> 
#include <stdlib.h> 
// ������� "���������" ���� �����, ������������ ��� ���������� 
void swap(int *a, int*b)
{
	int t = *a;
	*a = *b;
	*b = t;
}
//��������� ���������� �� ������� �� �������� S
typedef struct path_1
{
	unsigned int length;//�����
	short int before;//�������, ����� ������� ���� ���������� ���� �� ������ ������� � ������ - S
}path1;
//������ ������ ��� ����������, ��� ����� �������� �������������, ����� �������� ������ �� ������� ��� ���������� 2 ������� ������ ��������
typedef struct sortp
{
	short int value;
	unsigned int length;
}p;
//����������
int cmp_path(const void *a, const void *b)
{
	p *c = (p*)a, *z = (p*)b;
	if (c->length > z->length)
		return 1;
	else if (c->length < z->length)
		return -1;
	if (c->value > z->value)
		return 1;
	return -1;
}
//������� �������� ������, ������, �.� ��� ������ ����� ������������� ������������ 
void cleanspace(path1*a, p*b, unsigned int ** e, int N)
{
	free(a);
	free(b);
	for (int i = 0; i < N; i++)
		free(e[i]);
	free(e);
}
char deikstra(int NV, int NE, p *paths, int S, int F, unsigned int**edges, path1* path)
{
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
				for (k; k < NV ; k++)//��������� ��� �������
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

int main()
{
	int  NV, NE, S, F;//��������� ����� 
	// ��������� ���� 
	if (scanf("%d", &NV) == -1)//�������� �� ���������� ���������� ������  
	{
		printf("bad number of lines");
		return 0;
	}
	//�������� �� ���������� ���������� 
	if ((NV < 0) || (NV >= MAX_NODES))
	{
		printf("bad number of vertices");
		return 0;
	}

	if (scanf("%d %d", &S, &F) != 2)//�������� �� ���������� ������ � ���
	{
		printf("bad number of lines");
		return 0;
	}
	//�������� �� ���������� ���������� 
	if ((S <= 0) || (S > NV) || (F <= 0) || (F > NV))
	{
		printf("bad vertex");
		return 0;
	}
	//�������� �� ���������� ����������� �����
	if (scanf("%d", &NE) == -1)
	{
		printf("bad number of lines");
		return 0;
	}
	//�������� �� ���������� ���������� 
	if ((NE < 0) || (NE > ((NV*(NV - 1)) / 2)))
	{
		printf("bad number of edges");
		return 0;
	}
	if ((NV == 1) && (S == F)) //���� ������� ���� � ����� ������� �� ��� � ��� ��, �� ������� 0 � 1
	{
		printf("0\n1");
		return 0;
	}
	if ((NV == 1) && (NE == 0))//���� ������� ���� � ����� ���, ������ �� ������� 
		return 0;

	path1 *path;
	if (!(path = (path1*)malloc(sizeof(path1)*NV)))//������ ���������� ���������� �� ����� S �� ���� ������
	{
		printf("memory error");
		return 0;
	}
	p *paths;//�������� ������ ��� �������� ���������� 
	if (!(paths = (p*)malloc(sizeof(p)*NV)))
	{
		printf("memory error");
		free(path);
		return 0;
	}

	for (int i = 0; i < NV; i++)
	{
		paths[i].value = i;
		path[i].before = S-1;//������ ���� ���������� ������� S, ��� ��� ����� ����� ���������� �� ��� 
		path[i].length = UINT_MAX;//���� ��� ������� ���� �� S, ���  �������� ������ 
		paths[i].length = UINT_MAX;
	}
	path[S-1].length = 0;
	paths[S - 1].length = 0;
	//�� ��������� ���������� �� ����� ������� �� ��� �� ����� 0
	unsigned int **edges;//������� ���������� ����� ��������� 
	if (!(edges = (unsigned int**)malloc(sizeof(unsigned int*)*NV)))//NV+1 ����� ������� ������� � 1
	{
		printf("memory error");
		free(path);
		free(paths);
		return 0;
	}
	for (int i = 0; i < NV ; i++)
	{
		if (!(edges[i] = (unsigned int*)malloc(sizeof(unsigned int)*NV)))
		{
			printf("memory error");
			free(path);
			free(paths);
			for (int j = 0; j < i; j++)
				free(edges[j]);
			free(edges);
			return 0;
		}
		for (int j = 0; j < NV ; j++)
		{
			edges[i][j] = INT_MAX + 1;//���� ��� ������� ���� ����� ��������� 
			edges[i][i] = 0;
		}
	}
	//INT_MAX+1,���� �� �������� � ������ �������( ��� �����0
	int A, B, L;
	for (int i = 0; i < NE; i++)//��������� ����� 
	{
		if (scanf("%d %d %d", &A, &B, &L) != 3)
		{
			printf("bad number of lines");// �������� �� ���������� ���������� 
			cleanspace(path, paths, edges, NV);
			return 0;
		}
		if ((L < 0) || (L > INT_MAX))
		{
			printf("bad length");
			cleanspace(path, paths, edges, NV);
			return 0;
		}
		if ((A < 1) || (A > NV) || (B < 1) || (B > NV))
		{
			printf("bad vertex");
			cleanspace(path, paths, edges, NV);
			return 0;
		}
		if ((A == S) && (A != B))
		{
			path[B-1].length = L;
			paths[B - 1].length = L;//���������� ���������� �� S �� ������, � �������� ��� ����� �������, ���� ����� ���, �� ���������� ����+2
		}
		if ((B == S) && (A != B))
		{
			path[A-1].length = L;
			paths[A - 1].length = L;
		}
		edges[A-1][B-1] = L;
		edges[B-1][A-1] = L;
	}
	char pat = deikstra(NV, NE, paths, S-1, F-1, edges, path);
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
	if ((path[F-1].length == INT_MAX + 1) && (pat >= 2))//�������� ������� �� ���������� ������ INT_MAX
	{
		printf("overflow");
		cleanspace(path, paths, edges, NV);
		return 0;
	}
	if (path[F-1].length == UINT_MAX)//���� ��� ���������� 
	{
		printf("no path");
		cleanspace(path, paths, edges, NV);
		return 0;
	}
	printf("%d ", F);//�������� ����� � ������� F
	int K = F-1;
	while (K != S-1)//���� �� ����� �� S ������� ��� �������, ����� ������� ���� 
	{
		printf("%d ", path[K].before +1);
		K = path[K].before;
	}
	cleanspace(path, paths, edges, NV);
	return 0;
}