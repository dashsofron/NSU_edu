#pragma once
#include "../graphics/visualizer.h"

class Elem {
private:
	int data;
	int index;
public:
    Elem(int data_, int index_)
	 : data(data_), index(index_) {}
 
    operator int() const {
        return data;
    }
};
