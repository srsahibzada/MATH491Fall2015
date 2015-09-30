#include <iostream>
using namespace std;


//calulate gcd of (a, b);

//swaps the numbers and returns the larger
int main() {
int a, b, remdr;
int temp;
cout << "enter two numbers separated by a space" << endl;
cin >> a >> b;

while (b != 0) {
	temp = b;
	b = a % b;
	a = temp;

}

cout << "the gcd of a and b is " << a << endl;

}
