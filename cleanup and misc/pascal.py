def factorial(n):
	if n == 0 or n == 1:
		return float(1)
	else:
		return float(n) * float(factorial(n - 1))

def n_choose_k(n,k):
	return factorial(n)/(factorial(k)*factorial(n-k))

def pascal_row(n):
	row = []
	for x in range(n+1):
		row.append(n_choose_k(n,x))
	print row

def pascal_triangle(until):
	for x in range(until):
		pascal_row(x)

def stirling_second_kind(n,k):
	a = float(1)/float(factorial(float(k)))
	k = float(k)
	to_sum = [ float((-1**k-i)) * float(n_choose_k(k,i))*((i)**n) for float(i) in range(k)]
	for s in to_sum:
		print s, " in row"
	result = a * sum(to_sum)
	print result

def main():
	for x in range(4):
		for y in range(4):
			stirling_second_kind(x,y)
pascal_triangle(10)
main()

