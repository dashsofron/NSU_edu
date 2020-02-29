#define _CRT_SECURE_NO_WARNINGS 
#define MAX 1001
#include <stdio.h> 
#include <stdlib.h> 

/*int max(int a, int b)
{
	if (a > b)
		return a;
	return b;
}*/

typedef struct ribs
{
	int val;
	int second_val;
}ribs;
int compare(ribs* one, ribs* two)
{
	return(one->val - two->val);
}
void swap(ribs* one, ribs*two)
{
	ribs temp = *one;
	*one = *two;
	*two = temp;
}
void topsort(int counter, short *rel, int *answ, int num, ribs*mas)
{
	int c = counter, elem = 0, ans = 0;
	while (counter > 0)
	{
		for (int i = 1; i <= c; i++)
			if (rel[i] == 0)
			{
				elem = i;
				answ[ans] = i;
				ans++;
				break;
			}
		for (int i = 0; i < num; i++)
			if (mas[i].second_val == elem)
			{
				rel[mas[i].val]--;
				//swap(&mas[num-1], &mas[i]);
				//num--;
				//i--;
			}
		rel[elem]--;
		counter--;
	}
	//выводим первый элемент, у которого нет зависимости, стираем зависимость других элементов от него 
	//если есть возможность сортировки, то все элементы когда-нибудь останутся без связей и выведутся 
	//если нет возможности сортировки, то зависимость между некоторыми элементами будет замкнутой и не получится вывести все
}
int main()
{
	//printf("%d", sizeof(ribs));
	short* rel;
	int* answ;
	int cnt;
	ribs* mas;
	if (scanf("%d", &cnt) == -1)
	{
		printf("bad number of lines");
		return 0;
	}
	if ((cnt < 0) || (cnt >= MAX))
	{
		printf("bad number of vertices");
		return 0;
	}
	int oper;
	if (scanf("%d", &oper) == -1)
	{
		printf("bad number of lines");
		return 0;
	}
	if ((oper < 0) || (oper > cnt*(cnt + 1) / 2))
	{
		printf("bad number of edges");
		return 0;
	}
	if(!(mas = (ribs*)malloc(sizeof(ribs)*oper)))
{
printf("memory error");
}

	for (int i = 0; i < oper; i++)
	{
		mas[i].val = 0;
		mas[i].second_val = 0;
	}
	if(!(rel = (short*)calloc(cnt + 1,sizeof(short))))
{
printf("memory error");
free(mas);
}
	for (int i = 0; i < oper; i++)
	{
		int B, A;
		if (scanf("%d %d", &B, &A) != 2)
		{
			printf("bad number of lines");
			free(rel);
			free(mas);
			return 0;
		}
		if ((A < 1) || (A > cnt) || (B < 1) || (B > cnt))
		{
			printf("bad vertex");
			free(rel);
			free(mas);
			return 0;
		}

		mas[i].val = A;
		mas[i].second_val = B;
		rel[A]++;
	}
	if(!(answ = (int*)malloc(sizeof(int)*cnt)))
{
printf("memory error");
free(mas);
free(rel);
}
	//qsort(mas, oper, sizeof(ribs), compare);
	for (int i = 0; i < cnt; i++)
		answ[i] = 0;
	topsort(cnt, rel, answ, oper, mas);
	free(rel);
	free(mas);
	if (answ[cnt - 1] != 0)
		for (int i = 0; i < cnt; i++)
			printf("%d ", answ[i]);
	else
		printf("impossible to sort");
	//проверка на возможность сортировки 
	free(answ);
	return 0;
}
