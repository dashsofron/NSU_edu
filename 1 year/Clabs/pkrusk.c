#define _CRT_SECURE_NO_WARNINGS 
#define MAX_NODES 5001 // ������������ ���������� ������ 
#include <stdio.h> 
#include <stdlib.h> 
typedef struct edge_t {
	int n1, n2; // ������� �����
	int l; // ����� ����� 
} edge; // ����� ����� 
// ������� "���������" ���� �����, ������������ ��� ���������� 
int cmp(const void *a, const void *b) {
	edge *c = (edge*)a, *d = (edge*)b;
	return (c->l - d->l);
}
void swap(edge* one, edge* two)
{
	edge temp = *one;
	*one = *two;
	*two = temp;
} //������ ������� �����, ���� �� ��������� ��, ������� ��� � �������
int kruskal(int*nodes,edge* edges,edge* answer,int NV,int NE)
{
int k = 0;//������� ������� ������
	nodes[edges[0].n2] = edges[0].n2;
	nodes[edges[0].n1] = edges[0].n2;
	//����� ������( ����������) ����� � ���� �������� ���� ����, ���� ����������
	int col = edges[0].n2;
	answer[k] = edges[0];
	k++;
	int t = 1;
	while ((k != NV - 1)&&(k==t))
	{
		int j = t; //�� 0 �� t ����� �����, ������� ��� ��������� � ������ �������, ������� �� ����� �� ��������� � ������ ����� ������ � t
		//�����, ������� ������ ����������� � ������ t � �����
		for (j ; j < NE; j++)  //��������� ��� ����� � ������ �����������. �������� ������������ ����� ����������� �����, ������� ��������� �� ���������� ��������
			//���� �� ������ ����� ���������
		{ // ���� �� ������ ��� ����� 
			int c2 = nodes[edges[j].n2]; 
			int c1 = nodes[edges[j].n1];
			if ((c1==col)&&(c2!=col))
				//����� 2 �������, ���� ���� ����� ��������� � ������ �������( ���� �� ������ ��������� � �������), ��������� ����� � ������ ���� ������ �������
			{

				nodes[edges[j].n2] = col;
				answer[k] = edges[j];
				k++;
				for (j; j > t; j--)
					swap(&edges[j], &edges[j - 1]);//����� �������������� ����� ����������� � ������ t, t=t+1 � ����� ����� ������� ���� ����� ������ �� �����������
				break;
			}
			if ((c1 != col) && (c2 == col))//���������� �� ������� ������ �������
			{
				nodes[edges[j].n1] = col;
				answer[k] = edges[j];
				k++;
				for (j; j > t; j--)
					swap(&edges[j], &edges[j-1]);
				break;
			}
		}
		t++;//����������� �������� t ��� �������� ������������� �����
	}
	//����� �� ������������ ������������ �.� �� ����������� ������� ������ �����( ������ ���� ���� ��������� � ������ ������ �������)
	//����� �� ����� while �������������� ��� ���������� ���� ������ ������� �������( k=NV-1)
	//���� ���� �� ��������� ���� ������ �����, �� �� ����� ����� ��������������� ��������. � ���� ������ �������� t ���������� �� 1 � �����
	// � k ��� ( �.� � ������������� ������ ��� ���������� �����). ������� k!=t
	return k;
}
int main() {
	int i, NV, NE, last_n = 0;
	int* nodes; // ������ ������ ������ 
	// ��������� ���� 
	if (scanf("%d", &NV) == -1)//�������� �� ���������� 
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
	// ���� 1 �������, �� ������ ������� �� ����� ������� � ������ �������� �� ����.E��� 0 ����� � �� 1 �������, �� ������ �� �����������
	edge *edges = (edge*)malloc(sizeof(edge)*NE); // �������� � 0
	//������ ����� 
	nodes = (int*)malloc(sizeof(int)*(NV+1));// ����������� � 1, �.� ������ ������� - 1
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
		if ((edges[i].n1 == edges[i].n2) && (NE == 1)&&(NV>1)) // ���� ����� ���� �����, ����������� 1 ������� � ���������� ������ ������ 1
		{
			printf("no spanning tree");
				free(edges);
				free(nodes);
				return 0;
		}
		if (edges[i].n1 == edges[i].n2)
			/*�������� �������, ����� ������ ����� �� ����������� � �������, ���� ����������� �����
			����� ����� ��������� ���������� ������ ���� � �� ����������� � ���������� �������*/
		{
			edges[i].n1 = 0;
			edges[i].n2 = 0;
			edges[i].l = INT_MAX;
		}
	}

	edge *answer = (edge*)calloc(NV,sizeof(edge));
	// ��������� ��� ����� � ������� ����������� ���� 
	qsort(edges, NE, sizeof(edge), cmp);
	int k = kruskal(nodes,edges, answer, NV,NE);
	free(edges);
	free(nodes);
	//����� ��������� ������ ������� ����� ���������� ������ ���� �������� ����� ������
	//�������� �� ������������� ������� � �����, ���� ������ � ���, ��� ������ ��������� ����������
	
	if (k == NV - 1)//���� ��� ������� ��������� � �������, ������� ���
	{
		for (int i = 0; i < k; i++)
			printf("%d %d\n", answer[i].n1, answer[i].n2);//��� ������ ����� ��� ����� ��� ������� �������� �� � �������(����� �� 1 ������ ��� ������)
		free(answer);
		return 0;
	}
	printf("no spanning tree"); //���� �� ����� ������ 
	free(answer);
	return 0;
}