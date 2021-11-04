#include "circular_buffer.h"
#include <iostream>


using namespace std;

CircularBuffer::CircularBuffer()
{
	buffer_ = new char[0];
	size_ = 0;
	capacity_ = 0;
	first_ = 0;
}
CircularBuffer::~CircularBuffer()
{
	delete[] buffer_;
}

CircularBuffer::CircularBuffer(const CircularBuffer& cb)
{
	size_ = cb.size_;
	capacity_ = cb.capacity_;
	first_ = 0;// почему так работает, а с 0 нет???
	buffer_ = new char[capacity_];
	int t = 1;
	for (int i = 0; i < size_; i++)
		buffer_[i] = cb[t++];//можно i+1
}

//Конструирует буфер заданной ёмкости, целиком заполняет его элементом
CircularBuffer::CircularBuffer(int capacity, const char& elem)
{
	size_ = capacity;
	capacity_ = capacity;
	first_ = 0;
	buffer_ = new char[capacity_];
	for (int i = 0; i < capacity_; i++)
		buffer_[i] = elem;
}

//Конструирует буфер заданной ёмкости.
CircularBuffer::CircularBuffer(int capacity)
{
	size_ = 0;
	capacity_ = capacity;
	first_ = 0;
	buffer_ = new char[capacity_];
}

//Оператор присваивания.
CircularBuffer& CircularBuffer::operator= (const CircularBuffer& cb)
{
	delete[] buffer_;
	size_ = cb.size_;
	capacity_ = cb.capacity_;
	first_ = 0;
	buffer_ = new char[capacity_];
	for (int i = 0; i < size_; i++)
		buffer_[i] = cb.buffer_[(cb.first_ + i) % capacity_];
	return(*this);
}
void  CircularBuffer::print()
{
	for (int i = 0; i < capacity_; i++)
		printf("%c", buffer_[i]);
	printf("\n");
}
void  CircularBuffer::print_in_order()
{
	if (size_ == 0)
		return;
	for (int i = first_; i < first_ + size_; i++)
		printf("%c", buffer_[i%capacity_]);
	printf("\n");
}

//Доступ по индексу. Не проверяют правильность индекса.
char& CircularBuffer::operator[](int i)
{
	if (capacity_ == 0)
	{
		char*k = nullptr;
		char &nul = *k;
		return nul;
	}
	return(buffer_[(first_ + i - 1 + capacity_) % capacity_]);
}
const char& CircularBuffer::operator[](int i) const
{
	if (capacity_ == 0)
	{
		char*k = nullptr;
		char &nul = *k;
		return nul;
	}
	return(buffer_[(first_ + i - 1 + capacity_) % capacity_]);
}

//Доступ по индексу. Методы бросают исключение в случае неверного индекса.
char& CircularBuffer::at(int i)
{
	if ((i > size_) || (size_ == 0))
	{
		throw range_error("недостаточно элементов");
		char*k = nullptr;
		char &nul = *k;
		return nul;
	}
	return(buffer_[(first_ + i - 1 + capacity_) % capacity_]);
}
const char& CircularBuffer::at(int i) const
{
	if ((i > size_) || (size_ == 0))
	{
		throw range_error("недостаточно элементов");
		char*k = nullptr;
		char &nul = *k;
		return nul;
	}
	return(buffer_[(first_ + i - 1 + capacity_) % capacity_]);
}

//Ссылка на первый элемент
char& CircularBuffer::front()
{
	return(buffer_[first_]);
}
const char& CircularBuffer::front() const
{
	return(buffer_[first_]);
}

//Ссылка на последний элемент.
char& CircularBuffer::back()
{
	if (capacity_ == 0)
	{
		char*k = nullptr;
		char &nul = *k;
		return nul;
	}
	return(buffer_[(size_ + first_ - 1) % capacity_]);
}
const char& CircularBuffer::back() const
{
	if (capacity_ == 0)
	{
		char*k = nullptr;

	}
	return(buffer_[(size_ + first_ - 1) % capacity_]);
}

//Количество элементов, хранящихся в буфере.
int CircularBuffer::size() const
{
	return(size_);
}
void CircularBuffer::get_first_place()
{
	printf("first_place = %d\n", first_);
}
//ёмкость буфера
int CircularBuffer::capacity() const
{
	return(capacity_);
}

//Количество свободных ячеек в буфере.
int CircularBuffer::reserve() const
{
	return(capacity_ - size_);
}

//true, если size() == capacity().
bool CircularBuffer::full() const
{
	if (size() == capacity()) return true;
	else return false;
}

//Проверяем, пустой ли буфер (если ёмкость = 0, то false)
bool CircularBuffer::empty() const
{
	if (capacity_ == 0) return (false);
	if (size_ == 0) return (true);
	else return(false);
}

//Добавляет элемент в конец буфера.
//Если текущий размер буфера равен его ёмкости, то переписывается
//первый элемент буфера (т.е., буфер закольцован).
void CircularBuffer::push_back(const char& item /* = char()*/)
{
	if (capacity_ == 0)
	{
		throw logic_error("буфер на 0 элементов");
		return;

	}
	buffer_[(first_ + size_) % capacity_] = item;
	if (full())
		first_ = (first_ + 1) % capacity_;
	else
		size_ += 1;

}

//Добавляет новый элемент перед первым элементом буфера.
//Аналогично push_back, может переписать последний элемент буфера.
void CircularBuffer::push_front(const char& item/* = char()*/)
{
	if (capacity_ == 0)
	{
		throw logic_error("буфер на 0 элементов");
		return;
	}
	buffer_[first_ = (capacity_ + first_ - 1) % capacity_] = item;
	if (size_ < capacity_)
		size_ += 1;
}

//удаляет последний элемент буфера.
void CircularBuffer::pop_back()
{
	if (size_ == 0)
	{
		throw logic_error("здесь нечего удалять ");
		return;
	}
	size_--;
}

//удаляет первый элемент буфера.
void CircularBuffer::pop_front()
{
	if (size_ == 0)
	{
		throw logic_error("здесь нечего удалять ");
		return;
	}
	first_++;
	size_--;
}

//Сдвигает буфер так, что по нулевому индексу окажется элемент
//с индексом new_begin.
void CircularBuffer::rotate(int new_start)
{
	if (size_ == 0)
		return;
	if (capacity_ == 0)
	{
		throw logic_error("буфер на 0 элементов");
		return;
	}
	CircularBuffer a(capacity_);
	a.buffer_[a.first_ = (capacity_ - new_start + 1) % capacity_] = buffer_[first_];
	a.size_++;
	for (int i = 1; i < size_; i++)
	{
		a.push_back(buffer_[(first_ + i) % capacity_]);
	}
	swap(a);
}

//Линеаризация - сдвинуть кольцевой буфер так, что его первый элемент
//переместится в начало аллоцированной памяти. Возвращает указатель
//на первый элемент.
char* CircularBuffer::linearize()
{
	if (capacity_ == 0)
	{
		return NULL;
	}
	rotate(1);
	return(buffer_);
}

//Проверяет, является ли буфер линеаризованным.
bool CircularBuffer::is_linearized() const
{
	if (first_ == 0)
		return(true);
	else return(false);
}

void CircularBuffer::set_capacity(int new_capacity_)
{
	if (new_capacity_ < 0)
	{
		throw logic_error("не может быть отрицательных размеров");
		return;
	}
	if ((size_ == 0) || (new_capacity_ == 0))
	{
		delete[] buffer_;
		capacity_ = new_capacity_;
		buffer_ = new char[capacity_];
		size_ = 0;
		return;
	}
	char* a = new char[new_capacity_];
	for (int i = 1; i <= size_; i++)
		a[i] = buffer_[i];
	capacity_ = new_capacity_;
	char* tmp = buffer_;
	buffer_ = a;
	delete[] tmp;
}

//Изменяет размер буфера.
//В случае расширения, новые элементы заполняются элементом item.
void CircularBuffer::resize(int new_size, const char& item/* = char()*/)
{
	if (new_size == size_)
		return;
	if (new_size < 0)
	{
		throw logic_error("не может быть отрицательных размеров");
		return;
	}
	if (new_size > capacity_)
		set_capacity(new_size);
	if (new_size > size_)
		for (int i = size_; i < new_size; i++)
			push_back(item);
	size_ = new_size;
}

//Обменивает содержимое буфера с буфером cb.
void CircularBuffer::swap(CircularBuffer& cb)
{
	char* temp = buffer_;
	buffer_ = cb.buffer_;
	cb.buffer_ = temp;
	int i_temp = size_;
	size_ = cb.size_;
	cb.size_ = i_temp;
	i_temp = capacity_;
	capacity_ = cb.capacity_;
	cb.capacity_ = i_temp;
	i_temp = first_;
	first_ = cb.first_;
	cb.first_ = i_temp;
}

void CircularBuffer::insert(int pos, const char& item/* = char()*/)
{
	//printf("size before insert: %d   ",size());
	if (capacity_ == 0)
	{
		throw logic_error("буфер на 0 элементов");
		return;
	}
	int k = (capacity_ + pos) % capacity_;
	if (size_ == 0)
	{
		//printf("im here\n");
		buffer_[k] = item;
		first_ = k;
		size_++;
		return;
	}
	if (k == 0)
	{
		if (pos == size_)
			push_back(item);
		else
			//printf("im here1\n");
			push_front(item);
		return;
	}
	if (k == (size_ + 1) % capacity_)
	{
		//printf("im here0\n");
		push_back(item);
		return;
	}
	if (k > size_ + 1)
	{
		throw range_error("вы не можете вставить элемент на это место");
		return;
	}
	//printf("im here2\n");

	for (int i = first_ + size_ - 1; i >= first_ + k - 1; i--)
		buffer_[(i + 1) % capacity_] = buffer_[i%capacity_];
	buffer_[(first_ + k - 1) % capacity_] = item;
	if (!full())
		size_ += 1;
}

//Удаляет элементы из буфера в интервале [first, last).
void CircularBuffer::erase1(int first, int last)
{
	if ((capacity_ == 0) || (size_ == 0))
	{
		throw logic_error("нечего удалять");
		return;
	}
	int last1 = (capacity_ + last) % (capacity_ + 1);
	int first1 = (capacity_ + first) % (capacity_ + 1);
	if ((first1 > size_) || (last1 > size_ + 1))
	{
		throw range_error("вы хотите удалить то, чего нет");
		return;
	}
	int t = 0;
	for (int i = last1; i <= size_; i++)
	{
		buffer_[(first_ + first1 - 1 + t++) % capacity_] = buffer_[(first_ + i - 1) % capacity_];
	}
	size_ = size_ - (last - first);
}
void CircularBuffer::erase(int first, int last)
{
	if (size_ == 0)
	{
		throw logic_error("нечего удалять");
		return;
	}
	int last1 = (capacity_ + last) % (capacity_ + 1);
	int first1 = (capacity_ + first) % capacity_;
	if (last != capacity_) last1 = last % capacity_;
	if (last1 < first1) last1 += capacity_;
	if ((first1 > size_) || (last1 > size_))
	{
		throw range_error("вы хотите удалить то, чего нет");
		return;
	}
	int t = 0;
	for (int i = last1 + 1; i <= size_; i++)
	{
		buffer_[(first_ + first1 - 1 + t++) % capacity_] = buffer_[(first_ + i - 1) % capacity_];
	}
	size_ = size_ - (last1 + 1 - first1);
}
//Очищает буфер.
void CircularBuffer::clear()
{
	first_ = 0;
	size_ = 0;
}
bool operator==(const CircularBuffer &a, const CircularBuffer &b)
{
	if (a.size() != b.size())
		return (false);
	for (int i = 1; i <= a.size(); i++)
		if (a[i] != b[i])
			return(false);
	return(true);
}

bool operator!=(const CircularBuffer &a, const CircularBuffer &b)
{
	return(!(a == b));
}
