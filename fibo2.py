import math

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
	filename = "fibonacci_short3.txt"
	with open(filename, 'a') as filewriter:
		for x in range(0,15): 
			if (x == 0):
				continue
			filewriter.write(str(matrix_fibo(x)) + '\n')

main()
