#include "gtest/gtest.h"
#include "D:/GIT/labs/LaboratoryWorks/Lab_1/main/circular_buffer.h"
#include <stdlib.h>
#include <exception>
#include <excpt.h>
//simple example
using namespace std;
TEST(Test_1, DefaultConstructor)
{
	EXPECT_NO_THROW(CircularBuffer{});
}
//���� �� ������ � ������ �� []
//�������� ��������� ==
TEST(Test_2, EQ) 
{
	int n = 10;
	CircularBuffer a(n);

	CircularBuffer b(n);
	EXPECT_TRUE(a==b);
	a.push_back('1');
	EXPECT_TRUE(a != b);
	b.push_back('1');
	EXPECT_TRUE(a == b);
}
//�������� ������ �������������
TEST(Test_3,set_class) {
	int n = 10;
	int mas[3] = { '2','3','5' };
	CircularBuffer a(n);
	EXPECT_TRUE(a.capacity()==n);
	a.push_back('2');
	a.push_back('3');
	//a.print();
	//a.print_in_order();
	//printf("a == %d\n",a.size());
	CircularBuffer s(a);
	//s.print();
	//s.print_in_order();
	//printf("s == %d\n",s.size());
	CircularBuffer k = a;
	CircularBuffer l = s;
	/*k.print();
	k.print_in_order();
	printf("k == %d\n",k.size());
	l.print();
	l.print_in_order();
	printf("l == %d\n",l.size());
	*/
	EXPECT_TRUE(s == k);
	EXPECT_TRUE(a == k);
	EXPECT_TRUE(s == l);
	EXPECT_TRUE(k == l);
	a.push_back('5');
	EXPECT_TRUE(a.size()==3);
	CircularBuffer b = a;
	EXPECT_TRUE(k != b);
	//b.print();
	//b.print_in_order();
	for (int i = 1; i <= b.capacity(); i++)
		{
			b.insert(0,'e');
			//b.print();
			//b.print_in_order();
		}
	CircularBuffer e(n,'e');
	/*b.print();
	b.print_in_order();
	e.print();
	e.print_in_order();
	printf("b == %d\n",b.size());
	printf("e == %d\n",e.size());
	*/
	EXPECT_TRUE(e == b);
	//printf("end\n");
	CircularBuffer c;
	//printf("end\n");
	EXPECT_NO_THROW( c[0]);
	//printf("end\n");
	EXPECT_THROW(c.at(0),range_error);
	//printf("end\n");
	c.set_capacity(3);
	for (int i = 0; i < c.capacity(); i++)
		c.push_back(mas[i]);
	EXPECT_TRUE(a== c);
	//printf("end\n");
}

TEST(Test_4, at)
{

	CircularBuffer a(5);
	CircularBuffer b(5,7);
	EXPECT_THROW(a.at(5), range_error);
	EXPECT_NO_THROW(b.at(5));
}

TEST(Test_5, ssilki)
{
	CircularBuffer a(7);
	a.push_back('1');
	a.push_back('2');
	a.push_back('3');
	a.push_back('4');
	a.push_back('5');
	a.push_back('6');
	a.print();
	EXPECT_TRUE(a.front()=='1');
	EXPECT_TRUE(a.back()=='6');
	EXPECT_TRUE(a.capacity()==7);
	EXPECT_TRUE(a.size() == 6);
	EXPECT_TRUE(a.reserve() == 1);
	EXPECT_FALSE(a.full());
	EXPECT_FALSE(a.empty());
	printf("end8\n");
	a.rotate(3);
	printf("end9\n");
	EXPECT_TRUE(a.front() == '1');
	printf("end10\n");
	EXPECT_TRUE(a.back() == '6');
	printf("end11\n");
	a.push_back('7');
	a.push_back('8');
	printf("end12\n");
	EXPECT_TRUE(a.front() == '2');
	printf("end13\n");
	EXPECT_TRUE(a.back() == '8');
}

TEST(Test_6, push_pop)
{
	CircularBuffer a(10);
	for (int i = 0; i< 5; i++)
		{
			a.push_back((char)i+1+48);
		}
	//a.print();
	//a.print_in_order();	
	EXPECT_TRUE(a.front() == '1');
	//printf("a front = %c\n",a.front());
	EXPECT_TRUE(a.back() == '5');
	//printf("a back = %c\n",a.back());
	a.push_front('0');
	a.push_front('8');
	//a.print();
	//a.print_in_order();
	EXPECT_TRUE(a.front() == '8');
	//printf("a front = %c\n",a.front());
	EXPECT_TRUE(a.size() == 7);
	//printf("a size = %d\n",a.size());
	a.pop_back();
	a.pop_front();
	//a.print();
	//a.print_in_order();
	EXPECT_TRUE(a.back() == '4');
	//printf("a back = %c\n",a.back());
	EXPECT_TRUE(a.front() == '0');
	//printf("a front = %c\n",a.front());
	EXPECT_NO_THROW(a.erase(1,3));
	//a.print();
	//a.print_in_order();
	EXPECT_TRUE(a.back() == '4');
	//printf("a back = %c\n",a.back());
	EXPECT_TRUE(a.size() == 2);
	//printf("a size = %d\n",a.size());
	//printf("\n\n\n");
	CircularBuffer b(a);
	//b.get_first_place();
	//printf("bfirst - %c   ", b.front());
	//b.print();
	//b.print_in_order();
	EXPECT_TRUE(b.is_linearized());
	a.insert(3, '2');
	b.push_back('2');
	//printf("bfirst - %c\n\n", b.front());
	//a.print();
	//a.print_in_order();		
	//b.print();
	//b.print_in_order();	
	//printf("bsize -%d   ", b.size());
	//printf("bfirst - %c", b.front());
	//printf("\n\nого\n");
	b.rotate(1);
	//b.print();
	//b.print_in_order();
	b.rotate(2);
	//b.get_first_place();
	//printf("b: ");
	//b.print();
	//b.print_in_order();
	//printf("a: ");
	//a.print();
	//a.print_in_order();	
	EXPECT_TRUE(!(b.is_linearized()));
	EXPECT_TRUE(a==b);
	//a.print();
	//a.print_in_order();
	a.insert(4,'3');
	//a.print();
	//a.print_in_order();
	EXPECT_TRUE(a[4]=='3');
	//a.print();
	//a.print_in_order();
	//b.print();
	//b.print_in_order();
	b.linearize();
	EXPECT_TRUE(b.is_linearized());
	//b.print();
	//b.print_in_order();
	EXPECT_NO_THROW(b[0]);
	
}

TEST(Test_7, push_pop)
{
	CircularBuffer a(10);
	CircularBuffer b(15);
	if (a.empty())
	{
	a.insert(5, '1');
	a.push_back('2');
	a.push_front('0');
	}
	EXPECT_TRUE(a.front() == '0');
	EXPECT_TRUE(a.back() == '2');
	a.clear();
	//printf("a size = %d\n", a.size());
	//printf("b.size = %d\n", b.size());
	EXPECT_THROW(a.at(1),range_error);
	for (int i = 0; i < 7; i++)
		b.push_back('9');
	EXPECT_NO_THROW(b.at(1));
	//a.print();
	//b.print();
	//printf("after swap:\n");
	a.swap(b);
	/*printf("a size = %d\n", a.size());
	printf("b.size = %d\n", b.size());
	a.print();
	b.print();
	*/
	EXPECT_NO_THROW(a.at(1));
	EXPECT_THROW(b.at(1), range_error);
}

TEST(Test_8, cap_size_change)
{
	CircularBuffer a;
	if(a.capacity()==0)
	a.set_capacity(15);
	EXPECT_TRUE(a.capacity() == 15);
	EXPECT_NO_THROW(a.resize(20,'3'));
 }

TEST(Test_9, big_test_1)
{
	CircularBuffer a;
	CircularBuffer b(17,'17');
	CircularBuffer c(20);
	CircularBuffer d(a);
	EXPECT_TRUE(b[10] == b[11]);
	EXPECT_TRUE(b.at (10) == b.at(11));
	EXPECT_THROW(a.at(1), range_error);
	a.set_capacity(17);
	a.push_back('15');
	EXPECT_TRUE(a.capacity()==17);
	EXPECT_TRUE(b.front() == b.back());
	EXPECT_TRUE(a.front() == a.back());
	EXPECT_TRUE(a.size() != d.size());
	for (int i = 0; i < a.capacity(); i++)
		a.push_back(char((i + 45) % 3));
	EXPECT_TRUE(a.full());
	EXPECT_TRUE(a.reserve()==0);
	EXPECT_TRUE(b.full());
	b.clear();
	EXPECT_TRUE(b.empty());
	a.clear();
	a.push_back('0');
	a.push_back('1');
	a.push_back('2');
	a.push_back('3');
	a.push_back('4');
	a.push_back('5');
	a.push_back('6');
	a.push_back('7');
	a.push_back('8');
	a.push_back('9');
	a.push_back('a');
	a.push_back('b');
	a.push_back('c');
	a.push_back('d');
	a.push_back('e');
	a.push_back('f');
	a.push_back('g');
	//a.print();
	//a.print_in_order();
	b.insert(9,'0');
	int i=2;
	//b.print();
	b.insert(i++,'1');
	//b.print();
	b.insert(i++,'2');
	//b.print();
	b.insert(i++,'3');
	//b.print();
	b.insert(i++,'4');
	//b.print();
	b.insert(i++,'5');
	//b.print();
	b.insert(i++,'6');
	//b.print();
	b.insert(i++,'7');
	//b.print();
	b.insert(i++,'8');
	//b.print();
	b.insert(i++,'9');
	//b.print();
	b.insert(i++,'a');
	//b.print();
	b.insert(i++,'b');
	//b.print();
	b.insert(i++,'c');
	//b.print();
	b.insert(i++,'d');
	//b.print();
	b.insert(i++,'e');
	//b.print();
	b.insert(i++,'f');
	//			b.print();
	//printf("AAAAAAAAA   %d\n\n",i);
	//b.print();
	//if(!b.full())
	//printf("omg");
	b.push_back('g');
	//printf("size after inser: %d\n",b.size());
	//a.print();
	//printf("\n\n1:\n");
	//a.print_in_order();
	/*b.print();
	b.print_in_order();*/
	EXPECT_TRUE(a==b);
	b.rotate(1);
	//b.print();
	//b.print_in_order();
	EXPECT_TRUE(a==b);
	int z = b.front();
	a.push_back('8');
	//printf("\n\n2:\n");
	//a.print_in_order();
	EXPECT_TRUE(b.front()==z);
	b.push_back(z);
	EXPECT_TRUE(b.back() == z);
	a.clear();
	//printf("\n\n3:\n");
	//a.print_in_order();
	//printf("\n\n");
	//a.get_first_place();
	//printf("%d",a.size());
	for (int i = 0; i < a.capacity() - 4; i++)
			a.push_back('6');
	//printf("\n\n4:\n");
	a.print_in_order();
	a.rotate(4);
	//printf("\n\n5:\n");
	a.print_in_order();
	a.push_back('5');
	a.push_front('1');
	/*printf("\n\n6:\n");
	a.print_in_order();*/
	a.linearize();
	EXPECT_TRUE(a.back() == '5');
	EXPECT_TRUE(a.front() == '1');
	EXPECT_TRUE(a.is_linearized());
	//printf("size ::%d  , cap::%d\n",a.size(),a.capacity());
	//a.print();
	//a.print_in_order();
	EXPECT_THROW(a.erase(a.size()-1,a.size()+2), range_error);
	//printf("\n\n\n\n\n\n\n");
	/*printf("\n\n7:\n");
	a.print_in_order();
			printf("%d\n",a.size());*/
	EXPECT_NO_THROW(a.erase(1,a.size()));
	//EXPECT_TRUE(!(a.empty()));;
	/*a.pop_front();
	printf("\n\n8:\n");
	a.print_in_order();
	printf("size = %d\n",a.size());*/
	EXPECT_TRUE(a.empty());
	a.push_back('5');
	EXPECT_TRUE(a.front()==a.back());
	//EXPECT_ANY_THROW(a.pop_back());
	EXPECT_THROW(a.resize(-2), logic_error);
	a.resize(5);
	/*printf("\n\n9:\n");
	a.print_in_order();*/
	a.resize(10,'6');
	/*printf("\n\n10:\n");
	a.print_in_order();
	b.print_in_order();*/
	EXPECT_TRUE(a.at(7)==a.at(9));
	EXPECT_TRUE(a.at(7) == '6');
	//b.get_first_place();
	b.insert(9, '9');
	//b.get_first_place();
	//b.print_in_order();
	b.insert(7, '7');
	/*printf("\n\n11 before swap:\n");
	a.print_in_order();
	b.print_in_order();*/
	a.swap(b);
	/*printf("\n\n12 after swap:\n");
	a.print_in_order();
	b.print_in_order();*/
	EXPECT_TRUE(a.at(7) =='7');
	EXPECT_TRUE(b.at(7) == b.at(9));
	b.clear();
	for (int i = 0; i < b.capacity() - 7; i++)
		b.push_back('7');
	EXPECT_THROW(b.insert(b.size()+2,2), range_error);
	
}
/*TEST(Test_10, breaking)
{
	int n;
	scanf("%d\n", &n);
	CircularBuffer a(n);
	for (int i = 0; i < a.capacity() / 2; i++)
		{
			a.push_back(rand() % 200);
			a.push_front(rand() % 100);
		}
	if (a.capacity() % 2 == 0)	EXPECT_TRUE(a.full());
	else
	{
		a.push_front('5');
		EXPECT_TRUE(a.full());
		EXPECT_TRUE(a.front() == '5');
		EXPECT_NO_THROW(a.erase(1, a.size()));
		EXPECT_NO_THROW(a.pop_back());
		if(n==1)EXPECT_ANY_THROW(a.pop_front());
		for (int i = 0; i < a.capacity() / 2; i++)
			a.push_back(rand() % 200);
		EXPECT_ANY_THROW(a.rotate(-5));
		a.linearize();
		EXPECT_ANY_THROW(a.resize(-5));
		EXPECT_ANY_THROW(a.resize(a.size(),'5'));
		a.resize(n / 2);
		EXPECT_TRUE(a.size() == n / 2);
		EXPECT_NO_THROW(a.resize(0));
		a.clear();
		a.resize(10, 6);
		EXPECT_TRUE((a.front() == a.back()) && (a.back() == '6'));
		EXPECT_NO_THROW(a.set_capacity(0));
		EXPECT_ANY_THROW(a.set_capacity(-n));
		a.set_capacity(n);
		EXPECT_NO_THROW(a.insert(n + 5, '5'));
		a.resize(0);
		for (int i = 0; i < n / 2; i++)
			a.insert(i + 10, rand() % 100);
		EXPECT_ANY_THROW(a.insert(n / 2 + n / 4, '7'));
		EXPECT_ANY_THROW(a.erase(1, n / 2 + n / 4));
		a.erase(1, n / 2 +1);
		EXPECT_TRUE(a.empty());
		EXPECT_ANY_THROW(a[0]);
	}
}*/


/*TEST(Test_1_1, 1) {
	int n = 10;
	CircularBuffer a(n);
	CircularBuffer b(n);
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
	char elem = 1;
	a.push_back(elem);
	EXPECT_TRUE(a != b);
	EXPECT_TRUE(a.capacity() == b.capacity());
	b.push_back(elem);
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
}

//тот же что и 1, только не равных
TEST(Test_1_2, 2) {
	int n = 10;
	CircularBuffer a(n);
	CircularBuffer b(n);
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
	char elem = 1;
	a.push_front(elem);
	EXPECT_TRUE(a != b);
	EXPECT_TRUE(a.capacity() == b.capacity());
	elem++;
	b.push_front(elem);
	EXPECT_TRUE(a != b);
	EXPECT_TRUE(a.capacity() == b.capacity());
}

//на заполнение в обратоном порядке
TEST(Test_2_1, 3) {
	int n = 3;
	CircularBuffer a(n);
	CircularBuffer b(n);
	for (int i = 0; i < n; i++) {
		a.push_back(i);
		b.push_front(n - i - 1);
	}
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
}

//на перезапись первого
TEST(Test_2_2, 4) {
	int n = 3;
	CircularBuffer a(n);
	CircularBuffer b(n);
	for (int i = 0; i < n+1; i++) {
		a.push_back(i);
	}
	b.push_back(1);
	b.push_back(2);
	b.push_back(3);
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
}

//на перезапись последнего
TEST(Test_2_3, 5) {
	int n = 3;
	CircularBuffer a(n);
	CircularBuffer b(n);
	for (int i = 0; i < n + 1; i++) {
		a.push_front(i);
	}
	b.push_front(1);
	b.push_front(2);
	b.push_front(3);
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
}

//на копирование
TEST(Test_3_1, 6) {
	int n = 3;
	CircularBuffer a(n);
	for (int i = 0; i < n; i++) {
		a.push_back(i);
	}
	CircularBuffer b(a);
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.front() == b.front());
	EXPECT_TRUE(a.capacity() == b.capacity());
}

//на копирование size_ = 0; capacity != 0;
TEST(Test_3_2, 7) {
	int n = 3;
	CircularBuffer a(n);
	CircularBuffer b(a);
	EXPECT_TRUE(a == b);
	//EXPECT_TRUE(a.front() == b.front());
	EXPECT_TRUE(a.capacity() == b.capacity());
}

//на копирование capacity_ = 0;
TEST(Test_3_3, 8) {
	int n = 0;
	CircularBuffer a(n);
	CircularBuffer b(a);
	EXPECT_TRUE(a == b);
	//EXPECT_TRUE(a.front() == b.front());
	EXPECT_TRUE(a.capacity() == b.capacity());
}

//на объявление
TEST(Test_4_1, 9) {
	int n = 3;
	CircularBuffer a(n,3);
	for (int i = 0; i < n; i++) {
		EXPECT_TRUE(a.at(i) == 3);
	}
}

TEST(Test_4_2, 10) {
	int n = 3;
	CircularBuffer a(n);
	for (int i = 0; i < n; i++) {
		a.push_back(3);
	}
	for (int i = 0; i < n; i++) {
		EXPECT_TRUE(a.at(i) == 3);
	}
}

TEST(Test_4_3, 11) {
	int n = 3;
	CircularBuffer a(n);
	for (int i = 0; i < n; i++) {
		a.push_front(3);
	}
	for (int i = 0; i < n; i++) {
		EXPECT_TRUE(a.at(i) == 3);
	}
}

TEST(Test_4_4, 12) {
	int n = 3;
	CircularBuffer a(0);
	a.resize(3, 3);
	for (int i = 0; i < n; i++) {
		EXPECT_TRUE(a.at(i) == 3);
	}
}

TEST(Test_4_5, 13) {
	int n = 3;
	CircularBuffer a(0);
	a.set_capacity(3);
	for (int i = 0; i < n; i++) {
		a.push_front(3);
	}
	for (int i = 0; i < n; i++) {
		EXPECT_TRUE(a.at(i) == 3);
	}
}

TEST(Test_4_6, 14) {
	CircularBuffer a(6, 5);
	a.set_capacity(3);
	for (int i = 0; i < 3; i++) {
		a.push_front(3);
	}
	for (int i = 0; i < 3; i++) {
		EXPECT_TRUE(a.at(i) == 3);
	}
}

TEST(Test_4_7, 15) {
	CircularBuffer a(6, 5);
	a.clear();
	for (int i = 0; i < 3; i++) {
		a.push_front(3);
	}
	for (int i = 1; i < 4; i++) {
		EXPECT_TRUE(a.at(i) == 3);
	}
}

//тесты на удаление элементов
TEST(Test_5_1, 16) {
	CircularBuffer a(6, 5);
	CircularBuffer b(6);
	for (int i = 0; i < 6; i++) {
		a.pop_back();
	}
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
	EXPECT_TRUE(a.capacity() == 6);
	EXPECT_TRUE(a.size() == 0);
}

TEST(Test_5_2, 17) {
	CircularBuffer a(6, 5);
	CircularBuffer b(6);
	for (int i = 0; i < 6; i++) {
		a.pop_front();
	}
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
	EXPECT_TRUE(a.capacity() == 6);
	EXPECT_TRUE(a.size() == 0);
}

TEST(Test_5_3, 18) {
	CircularBuffer a(7);
	CircularBuffer b(7);
	for (int i = 0; i < 7; i++)a.push_back(i);
		a.print();
	a.print_in_order();
		printf("\n\n\n");
	a.erase(3, 4);
	b.push_front(0);
	for (int i = 3; i < 7; i++)b.push_back(i);
	a.print();
	a.print_in_order();
	printf("\n\n\n");
	b.print();
	b.print_in_order();
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
}

//на прочую хрень, в которой я 100% не ошибся
TEST(Test_6_1, 19) {
	CircularBuffer a(7);
	CircularBuffer b(7);
	CircularBuffer c(7);
	CircularBuffer d(7);
	for (int i = 0; i < 7; i++) {
		a.push_back(i);
		b.push_back(i);
		c.push_back(7 - i - 1);
		d.push_back(7 - 1 - i);
	}
	a.swap(c);
	EXPECT_TRUE(a == d);
	EXPECT_TRUE(a != b);
	EXPECT_TRUE(c == b);
}

TEST(Test_6_2, 20) {
	CircularBuffer a(7);
	for (int i = 0; i < 6; i++)
		if (i % 2) a.push_back(i);
		else a.push_front(i);
	CircularBuffer b(a);
	a.linearize();
	EXPECT_TRUE(a == b);
	EXPECT_TRUE(a.capacity() == b.capacity());
	EXPECT_TRUE(a.size() == b.size());
	EXPECT_TRUE(a.is_linearized());
}

TEST(Test_6_3, 21) {
	CircularBuffer a(7);
	//EXPECT_ANY_THROW(a[3]);
	EXPECT_ANY_THROW(a.at(3));
	a.push_back(1);
	EXPECT_NO_THROW(a[3]);
	EXPECT_ANY_THROW(a.at(3));
	a.push_back(1);
	EXPECT_NO_THROW(a[3]);
	EXPECT_ANY_THROW(a.at(3));
	a.push_back(1);
	EXPECT_TRUE(a[3]==1);
}

TEST(Test_6_4, 22) {
	CircularBuffer a(7);
	for (int i = 0; i < 7; i++) {
		a.push_back(i);
	}
	EXPECT_TRUE(a.is_linearized());
}

TEST(Test_6_5, 22) {
	CircularBuffer a(8);
	for (int i = 0; i < 7; i++) {
		a.push_front(i);
	}
	EXPECT_FALSE(a.is_linearized());
}

//напряг тесты
TEST(Test_7_1, 23) {
	int n = 13000;
	CircularBuffer a(n, 3);
	CircularBuffer b(a);
	for (int i = 0; i < n - 1; i++) {
		a.erase(2, 2);
	}
	EXPECT_TRUE(a.is_linearized());
	EXPECT_TRUE(a.size() == 1);
	EXPECT_TRUE(a.capacity() == n);
	EXPECT_ANY_THROW(a.at(2));
	EXPECT_TRUE(a.at(1) == 3);
	for (int i = 0; i < n - 1; i++) {
		a.push_back(2);
	}
	EXPECT_TRUE(a.full());
}

TEST(Test_7_2, 23) {
	int n = 10000;
	CircularBuffer a(n, 3);
	CircularBuffer b(a);
	for (int i = 0; i < n - 1; i++) {
		a.erase(2, 2);
	}
	EXPECT_TRUE(a.is_linearized());
	EXPECT_TRUE(a.size() == 1);
	EXPECT_TRUE(a.capacity() == n);
	EXPECT_ANY_THROW(a.at(2));
	EXPECT_TRUE(a.at(1) == 3);
	for (int i = 0; i < n - 1; i++) {
		a.push_back(2);
	}
	EXPECT_TRUE(a.full());
	for (int i = 2; i < n+1; i++) {
		EXPECT_TRUE(a.at(i) == b.at(i) - 1);
	}
}*/