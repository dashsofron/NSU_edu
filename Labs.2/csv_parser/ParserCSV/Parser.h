#ifndef DSV_PARSER_H
#define DSV_PARSER_H

template<typename ...Args>
class Parser;

template<typename ...Args>
class Iterator;

enum state{finished,running, last};

#include <tuple>
#include <vector>
#include <fstream>
#include <string>
#include "Iterator.h"

using namespace std;

template<typename ...Args>
class Parser{
public:
    const string filename;
    Parser(string fname, char col_delim = ',', char row_delim = '\n', char escape = '"');
    Iterator<Args...> begin();
    Iterator<Args...> end();
    vector<string> parseRecord(ifstream&, ifstream::pos_type&, ifstream::pos_type&, state&) const;
    size_t getId() const;
private:
    static size_t next_id;
    const char col_delim;
    const char row_delim;
    const char escape;
    const size_t id;
};

template <typename ...Args>
size_t Parser<Args...>::next_id=0;

template <typename ...Args>
Parser<Args...>::Parser(string fname, char col_delim, char row_delim, char escape):filename(fname), col_delim(col_delim), row_delim(row_delim),escape(escape),id(Parser::next_id){
    Parser<Args...>::next_id++;
}

template <typename ...Args>
size_t Parser<Args...>::getId()const{
    return id;
}

template<typename ...Args>
Iterator<Args...> Parser<Args...>::begin(){
    return Iterator<Args...>(this,running);
}

template<typename ...Args>
Iterator<Args...> Parser<Args...>::end(){
    return Iterator<Args...>(this,finished);
}

template <typename ...Args>
vector<string> Parser<Args...>::parseRecord(ifstream & stream, ifstream::pos_type& start_pos, ifstream::pos_type& next_pos, state & status) const {
    stream.seekg(start_pos);
    bool escaped = false;
    char c;
    vector<string> res;
    string value;
    while(!stream.eof()){
        stream.get(c);
        if(stream.eof()){
            res.push_back(value);
            break;
        }
        if(escaped){
            if(escape==c){
                stream.get(c);
                if(escape==c){
                    value+=c;
                    continue;
                } else{
                    if(stream.eof()|| col_delim ==c|| row_delim ==c){
                        res.push_back(value);
                        value.clear();
                        escaped=false;
                        continue;
                    }
                    throw invalid_argument("Invalid format");
                }
            } else{
                value+=c;
                continue;
            }

        } else{
            if(escape==c){
                escaped=true;
				//cout << "HEH" << endl;
                continue;
            } else if(col_delim ==c){
                res.push_back(value);
                value.clear();
                continue;
            } else if(row_delim ==c){
                res.push_back(value);
                break;
            } else{
                value+=c;
                continue;
            }
        }
    }
    stream.get();
    if (stream.eof()){
        status = last;
    }
    stream.unget();
    if (escaped)throw invalid_argument("Invalid escape sequence");
    next_pos = stream.tellg();
	/*cout << sizeof...(Args) << res.size() << endl;
	for (string R : res)
		cout << R << endl;*/
	if(sizeof...(Args)!=res.size())throw invalid_argument("Invalid number of elemets");
    return res;

}
#endif //DSV_PARSER_H
