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
TEST(Test_3,set_class) {
	int n = 10;
	int mas[3] = { '2','3','5' };
	CircularBuffer a(n);
	EXPECT_TRUE(a.capacity()==n);
	a.push_back('2');
	a.push_back('3');
	CircularBuffer s(a);
	CircularBuffer k = a;
	CircularBuffer l = s;
	EXPECT_TRUE(s == k);
	EXPECT_TRUE(a == k);
	EXPECT_TRUE(s == l);
	EXPECT_TRUE(k == l);
	a.push_back('5');
	EXPECT_TRUE(a.size()==3);
	CircularBuffer b = a;
	EXPECT_TRUE(k != b);
	for (int i = 1; i <= b.capacity(); i++)
		{
			b.insert(0,'e');
		}
	CircularBuffer e(n,'e');
	EXPECT_TRUE(e == b);
	CircularBuffer c;
	EXPECT_NO_THROW( c[0]);
	EXPECT_THROW(c.at(0),range_error);
	c.set_capacity(3);
	for (int i = 0; i < c.capacity(); i++)
		c.push_back(mas[i]);
	EXPECT_TRUE(a== c);
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
	EXPECT_TRUE(a.front() == '1');
	EXPECT_TRUE(a.back() == '5');
	a.push_front('0');
	a.push_front('8');
	EXPECT_TRUE(a.front() == '8');
	EXPECT_TRUE(a.size() == 7);
	a.pop_back();
	a.pop_front();
	EXPECT_TRUE(a.back() == '4');
	EXPECT_TRUE(a.front() == '0');
\	EXPECT_NO_THROW(a.erase(1,3));
	EXPECT_TRUE(a.back() == '4');
	EXPECT_TRUE(a.size() == 2);
	CircularBuffer b(a);
	EXPECT_TRUE(b.is_linearized());
	a.insert(3, '2');
	b.push_back('2');
	b.rotate(1);
	b.rotate(2);	
	EXPECT_TRUE(!(b.is_linearized()));
	EXPECT_TRUE(a==b);
	a.insert(4,'3');
	EXPECT_TRUE(a[4]=='3');
	b.linearize();
	EXPECT_TRUE(b.is_linearized());
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
	EXPECT_THROW(a.at(1),range_error);
	for (int i = 0; i < 7; i++)
		b.push_back('9');
	EXPECT_NO_THROW(b.at(1));
	a.swap(b);
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
	b.insert(9,'0');
	int i=2;
	b.insert(i++,'1');
	b.insert(i++,'2');
	b.insert(i++,'3');
	b.insert(i++,'4');
	b.insert(i++,'5');
	b.insert(i++,'6');
	b.insert(i++,'7');
	b.insert(i++,'8');
	b.insert(i++,'9');
	b.insert(i++,'a');
	b.insert(i++,'b');
	b.insert(i++,'c');
	b.insert(i++,'d');
	b.insert(i++,'e');
	b.insert(i++,'f');
	b.push_back('g');

	EXPECT_TRUE(a==b);
	b.rotate(1);
	EXPECT_TRUE(a==b);
	int z = b.front();
	a.push_back('8');
	EXPECT_TRUE(b.front()==z);
	b.push_back(z);
	EXPECT_TRUE(b.back() == z);
	a.clear();
	for (int i = 0; i < a.capacity() - 4; i++)
			a.push_back('6');
	a.print_in_order();
	a.rotate(4);
	a.print_in_order();
	a.push_back('5');
	a.push_front('1');
	a.linearize();
	EXPECT_TRUE(a.back() == '5');
	EXPECT_TRUE(a.front() == '1');
	EXPECT_TRUE(a.is_linearized());
	EXPECT_THROW(a.erase(a.size()-1,a.size()+2), range_error);
	EXPECT_NO_THROW(a.erase(1,a.size()));
	EXPECT_TRUE(a.empty());
	a.push_back('5');
	EXPECT_TRUE(a.front()==a.back());
	EXPECT_THROW(a.resize(-2), logic_error);
	a.resize(5);
	a.resize(10,'6');
	EXPECT_TRUE(a.at(7)==a.at(9));
	EXPECT_TRUE(a.at(7) == '6');
	b.insert(9, '9');

	b.insert(7, '7');
	a.swap(b);
	EXPECT_TRUE(a.at(7) =='7');
	EXPECT_TRUE(b.at(7) == b.at(9));
	b.clear();
	for (int i = 0; i < b.capacity() - 7; i++)
		b.push_back('7');
	EXPECT_THROW(b.insert(b.size()+2,2), range_error);
	
}
