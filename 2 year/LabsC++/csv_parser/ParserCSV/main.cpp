#include <iostream>
#include "Parser.h"
#include<tuple>
using namespace std;
int main() {
	try {
		Parser<int, string, string, string, float> parser("C:/Users/Dasha/source/repos/f_p/Debug/input.csv");
		for (auto tup : parser) {
			cout << tup << endl;
		}
	}
	catch (exception & error) {
		cout << error.what() << endl;
	}
	return 0;
}