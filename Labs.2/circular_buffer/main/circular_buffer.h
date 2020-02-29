#pragma once

class CircularBuffer {
private:
	char* buffer_;
	int size_;
	int capacity_;
	int first_;
public:

	CircularBuffer();
	~CircularBuffer();

	CircularBuffer(const CircularBuffer& cb);

	//Конструирует буфер заданной ёмкости, целиком заполняет его элементом
	CircularBuffer(int capacity, const char& elem);

	//Конструирует буфер заданной ёмкости.
	explicit CircularBuffer(int capacity);

	//Оператор присваивания.
	CircularBuffer& operator=(const CircularBuffer& cb);
	void print();
	void print_in_order();
	void get_first_place();
	//Доступ по индексу. Не проверяют правильность индекса.
	char& operator[](int i);
	const char& operator[](int i) const;

	//Доступ по индексу. Методы бросают исключение в случае неверного индекса.
	char& at(int i);
	const char& at(int i) const;

	//Ссылка на первый элемент
	char& front();
	const char& front() const;

	//Ссылка на последний элемент.
	char& back();
	const char& back() const;

	//Количество элементов, хранящихся в буфере.
	int size() const;

	//ёмкость буфера
	int capacity() const;

	//Количество свободных ячеек в буфере.
	int reserve() const;

	//true, если size() == capacity().
	bool full() const;

	//Проверяем, пустой ли буфер (если ёмкость = 0, то false)
	bool empty() const;

	//Добавляет элемент в конец буфера.
	//Если текущий размер буфера равен его ёмкости, то переписывается
	//первый элемент буфера (т.е., буфер закольцован).
	void push_back(const char& item = char());
	void push_backs(const char& item = char());

	//Добавляет новый элемент перед первым элементом буфера.
	//Аналогично push_back, может переписать последний элемент буфера.
	void push_front(const char& item = char());

	//удаляет последний элемент буфера.
	void pop_back();

	//удаляет первый элемент буфера.
	void pop_front();

	//Сдвигает буфер так, что по нулевому индексу окажется элемент
	//с индексом new_begin.
	void rotate(int new_start);

	//Линеаризация - сдвинуть кольцевой буфер так, что его первый элемент
	//переместится в начало аллоцированной памяти. Возвращает указатель
	//на первый элемент.
	char* linearize();

	//Проверяет, является ли буфер линеаризованным.
	bool is_linearized() const;

	void set_capacity(int new_capacity_);

	//Изменяет размер буфера.
	//В случае расширения, новые элементы заполняются элементом item.
	void resize(int new_size, const char& item = char());

	//Обменивает содержимое буфера с буфером cb.
	void swap(CircularBuffer& cb);

	//Вставляет элемент item по индексу pos. Ёмкость буфера остается неизменной.
	void insert(int pos, const char& item = char());

	//Удаляет элементы из буфера в интервале [first, last).
	void erase(int first, int last);
	void erase1(int first, int last);

	//Очищает буфер.
	void clear();
};
bool operator==(const CircularBuffer &a, const CircularBuffer &b);
bool operator!=(const CircularBuffer &a, const CircularBuffer &b);