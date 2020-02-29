#ifndef DSV_ITERATOR_H
#define DSV_ITERATOR_H


#include <memory>
#include <unordered_map>
#include <fstream>
#include "Parser.h"
#include "tupleHandler.h"

using namespace std;


template<typename ...Args>
class Iterator {
public:
    Iterator(Parser<Args...>*,state);
    Iterator(const Iterator&);
    //Iterator& operator=(const Iterator&);
    Iterator& operator++();
    Iterator operator++(int);
    tuple<Args...> operator*()const;
    bool operator==(const Iterator&)const;
    bool operator!=(const Iterator&)const;
private:
    static unordered_map<size_t,vector<ifstream::pos_type>> table;
    ifstream file;
    Parser<Args...>* parser;
    tuple<Args...> value;
    state status;
    size_t rec;// номер строки, который берем с файла, увеличивается при никременте 
};

//
//
//

template<typename ...Args>
unordered_map<size_t,vector<ifstream::pos_type>> Iterator<Args...>::table = unordered_map<size_t,vector<ifstream::pos_type>>();

template<typename ...Args>
Iterator<Args...>::Iterator(Parser<Args...> * parser_ptr, state st) {
	parser = parser_ptr;
    if(Iterator<Args...>::table.find(parser->getId())==Iterator::table.end()){
        Iterator<Args...>::table[parser->getId()]=vector<ifstream::pos_type>();
        Iterator<Args...>::table[parser->getId()].resize(2);
        Iterator<Args...>::table[parser->getId()][0]=0;
    }
	file = ifstream(parser->filename);
    status = st;

    char c;
	file.get(c);
    if(file.eof()){
        throw invalid_argument("Empty file");
    } else{
		file.unget();
    }


    rec=0;
    vector<string> str_values = parser->parseRecord(file,Iterator::table[parser->getId()][0],Iterator::table[parser->getId()][1], status);
    buildTuple<Args...>(str_values,value);

}

template<typename ...Args>
Iterator<Args...>::Iterator(const Iterator& other) {//зачем
	parser = other.parser;
	file = ifstream(parser->filename);
    rec = other.rec;
    status = other.status;
    if(finished!=status){
        value = other.value;
    }
}

/*template<typename ...Args>
Iterator<Args...>& Iterator<Args...>::operator=(const Iterator& other) {
	parser = other.parser;
    rec = other.rec;
	file = ifstream(parser->filename);
    status = other.status;
    if(finished!=status){
        value = other.value;
    }

} часть, которая вообще ничего не меняет и по-моему тут навредил копипаст
*/

template<typename ...Args>
Iterator<Args...>& Iterator<Args...>::operator++() {
    if (finished == status){
        return *this;
    }
    if(last == status){
        status = finished;
        return *this;
    }
    rec++;
    if(Iterator<Args...>::table[parser->getId()].size()>=rec+1){
        Iterator<Args...>::table[parser->getId()].resize(rec+2);
    }
    vector<string> str_values = parser->parseRecord(file,Iterator<Args...>::table[parser->getId()][rec],Iterator::table[parser->getId()][rec+1], status);
    buildTuple<Args...>(str_values,value);

    return *this;
}

template<typename ...Args>
Iterator<Args...> Iterator<Args...>::operator++(int) {
    Iterator res(*this);
    this->operator++();
    return res;
}

template<typename ...Args>
bool Iterator<Args...>::operator==(const Iterator & other) const {
    if(finished==status&&finished==other.status){
        return true;
    } else{
        if(running==status&&running==other.status){
            return (rec==other.rec);
        } else{
            return false;
        }
    }
}

template<typename ...Args>
tuple<Args...> Iterator<Args...>::operator*() const {
    return value;
}

template<typename ...Args>
bool Iterator<Args...>::operator!=(const Iterator & other) const {
    return !(this->operator==(other));
}

#endif //DSV_ITERATOR_H
