import math
import numpy

def naive_fibonacci(which):
	if which <= 1:
		return 1
	else:
		return naive_fibonacci(which-1) + naive_fibonacci(which-2)

#naive implementation 
def matrix_mult(a,b):
	rows = len(a)
	cols = len(b[0])
	result = []
	if rows != cols:
		return -1 #error case
	else:
		for x in range(0,cols):
			newlist = []
			for y in range(0,rows):
				new = 0
				for z in range(0,rows):
					new += a[x][z]*b[z][y]
				newlist.append(new)
				#print newlist
			result.append(newlist)
	#print result 
	return result
def smarter_matrix_mult(a,b):
	x = numpy.matrix(a)
	y = numpy.matrix(b)
	return ((x*y).tolist())[0]

def smarter_matrix_mult2(a,b):
	a_cols = len(a[0])
	a_rows = len(a)
	b_cols = len(b[0])-1
	b_rows = len(b)

	result = []
	if a_rows != b_cols+1:
		return -1
	else:
		for t in range(a_rows):
			result.append([0])
		for t2 in range(a_rows):
			for t3 in range(b_cols):
				result[t2].append(0)
		for x in range(a_rows):
			for y in range(b_cols+1):
				for z in range(b_rows):
					print result[x][y]
					print a[x][z]
					print b[z][y]
					print "--------"
					result[x][y] += a[x][z] * b[z][y] 
	
	print result
	print
	return result

def factorial_list(quant):
	to_return = []
	for x in range(0,quant):
		to_return.append(math.factorial(x))

	for x in to_return:
		print x
	return to_return

def factorial_bounded(low,high):
	to_return = []
	for x in range(low,high+1):
		to_return.append(math.factorial(x))

	for x in to_return:
		print x
	return to_return


#please use this rather than the recursive one, it is substantially faster even with o(n^3) matrix mult
def matrix_fibo(which):
	ident = [[1,0],[0,1]] #immutable tuples. don't want to change this
	multiplier = [[1,1],[1,0]]
	for x in range(1,which):
		ident = matrix_mult(ident,multiplier)
	return ident[0][0]

#please use this rather than the recursive one, it is substantially faster even with o(n^3) matrix mult
def matrix_lucas(which):
	ident = [[1,2],[0,1]] #immutable tuples. don't want to change this
	multiplier = [[1,1],[1,0]]
	for x in range(1,which):
		ident = smarter_matrix_mult2(ident,multiplier)
		print ident
	else:
		if ident[0][1] == 2:
			return 1
		return ident[0][0]


def simple_conjecture_test(filename,mode):
	fibo_list = []
	product = 1
	
	with open(filename, 'rb') as filereader:
		for line in filereader:
			to_append = int(line)
			fibo_list.append(to_append)
	if mode == 'first':
		indices_to_multiply = [1,2,3,4,5,6,8,10,12]
		for ind in indices_to_multiply:
			product *= fibo_list[ind-1]
		print product
		print math.factorial(11)
	'''elif mode == 'diophantine_solutions':
		to_test = factorial_bounded(4,7)'''


def main():

	filename = raw_input("Enter the file to which to write: ")
	mode = raw_input("fibonacci (f) or lucas (l) ?")
	num = int(raw_input("Enter how many numbers to generate: "))
	with open(filename, 'a') as filewriter:
		for x in range(0,num): 
			if (x == 0):
				continue
			if mode == 'f':
				filewriter.write(str(matrix_fibo(x)) + '\n')
			elif mode == 'l':
				if x == 0:
					filewriter.write("1\n")
				else:
					filewriter.write(str(matrix_lucas(x)) + '\n')

main()
