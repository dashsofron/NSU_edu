#ifndef DSV_TUPLEMAKER_H
#define DSV_TUPLEMAKER_H
#include <vector>
#include <string>
#include <utility>
#include <sstream>
#include <tuple>
#include <iostream>

using namespace std;

template<typename T>
T getValue(string str, size_t index){
    T res;
    istringstream iss(str);
    iss >> res;
    if(!iss.eof()){
        throw invalid_argument("Mismatching type of argument "+to_string(index)+" which is "+str);
    }

    return res;
}

template<>
string getValue<string>(string str, size_t index){
    return str;
}


template <typename Tuple,size_t i, size_t N>
class tupleMaker{
public:
    static void makeTuple(vector<string>& str_values,Tuple& value){
        typename tuple_element<i,Tuple>::type type_i;
        get<i>(value)=getValue<decltype(type_i)>(str_values[i],i);
        tupleMaker<Tuple,i+1,N>::makeTuple(str_values, value);
    };
};

template <typename Tuple, size_t N>
class tupleMaker<Tuple,N,N>{
public:
    static void makeTuple(vector<string>& str_values,Tuple& value){
        typename tuple_element<N,Tuple>::type type_i;
        get<N>(value)=getValue<decltype(type_i)>(str_values[N],N);
    };
};

template<typename ...Args>
void buildTuple(vector<string>& str_values,tuple<Args...>& value){
    tupleMaker<tuple<Args...>,0,sizeof...(Args)-1>::makeTuple(str_values,value);
}

template<class Ch, class Tr, size_t I, typename...Args>
struct tuple_print
{
	static void print(std::basic_ostream<Ch, Tr>& out, const std::tuple<Args...>& t)
	{
		out << std::get<sizeof...(Args) - I>(t) << " ";
		if (I > 1)
		{
			tuple_print<Ch, Tr, I - 1, Args...>::print(out, t);
		}
	}
};
template<class Ch, class Tr, typename... Args>
struct tuple_print<Ch, Tr, 0, Args...>
{
	static void print(std::basic_ostream<Ch, Tr>& out, const std::tuple<Args...>& t)
	{
	}
};

template<class Ch, class Tr, typename... Args>
std::ostream& operator<<(std::basic_ostream<Ch, Tr>& out, const std::tuple<Args...>& t)
{

	tuple_print<Ch, Tr, sizeof...(Args), Args...>::print(out, t);
	return out;
}


#endif //DSV_TUPLEMAKER_H

