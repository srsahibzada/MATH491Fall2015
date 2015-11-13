import numpy
import matplotlib.pyplot as plt
import math
import csv
import linecache as lc

#simple recursive gcd
def gcd(a,b):
	if b == 0:
		return a
	else:
		return gcd(b, a%b)

def coprime(a,b):
	return gcd(a,b) == 1

def reduce_fraction(frac):
	if coprime(frac[0],frac[1]):
		return frac
	else:
		factor = gcd(frac[0], frac[1])
		numerator = frac[0] / factor
		denominator = frac[1] / factor
		#print numerator, denominator
		return (numerator, denominator)


#deal with abundancy as a tuple to avoid loss of precision
#using gcds to reduce the fraction
def abundancy(num):
	line = str(lc.getline('divisors_data_set.csv',num))
	line = line.strip()
	line = line.split(',')
	sum = 0
	for x in line:
		if x == '':
			break
		else:
			sum += int(x)
	
	return reduce_fraction((sum,num))

def is_prime(num):
	line = str(lc.getline('divisors_data_set.csv', num))
	line = line.strip()
	line = line.split(',')
	#print line
	if len(line) == 3:
		#print line
		return True
	else:
		return False

def abundancy_list():
	with open('divisors_data_set.csv', 'rb') as csvfile:
		reader = csv.reader(csvfile, delimiter=',')
		abundancy_list = [ abundancy(x) for x, line in enumerate(reader, start=1)]
	for pair in abundancy_list:
		print pair
		print
	return abundancy_list

def is_friendly(num1, num2):
	return abundancy(num1) == abundancy(num2)

def prime_list():
	with open('divisors_data_set.csv', 'rb') as csvfile:
		reader = csv.reader(csvfile, delimiter = ',')
		prime_list = filter(is_prime, range(100))
	return prime_list

def friendly_clubs():
	ab_list = abundancy_list()
	clubs = []
	
def search_for_clubs(friendly):
	
def main():
	to_graph = abundancy_list()	
	primes = prime_list()
	print to_graph
	to_graph2 = [ float(x[0])/float(x[1]) for x in to_graph] 
	to_graph3 = [ float(x[0])/float(x[1]) for x in primes]  
	print to_graph3
	plt.plot(to_graph2, 'ro')
	plt.plot(to_graph3, 'bs')
	plt.xlabel('n')
	plt.ylabel('sig(n)')
	plt.title('sig(n) vs n')
	plt.show()
	'''plt.plot(to_graph, 'ro')

	plt.xlabel('Numerator of Abundancy Function')
	plt.ylabel('Denominator of Abundancy Function')
	plt.title('Distribution of Numerator and Denominator of sig(n)/n, n=0..100')
	plt.show()'''
abundancy_list()
prime_list()
