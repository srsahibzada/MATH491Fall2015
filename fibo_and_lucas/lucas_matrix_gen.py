

# THIS ONLY WORKS WITH SAGEMATH
# EITHER IN THE SAGE TERMINAL, ON CLOUD.SAGEMATH.COM, OR 
# IF YOU HAVE A SAGEMATH PYTHON MODULE


def naive_fibo(n):
    if n == 0:
        return 0
    elif n == 1:
        return 1
    else:
        return naive_fibo(n-1) + naive_fibo(n-2)


def naive_lucas(n):
    if n == 0:
        return 2
    elif n == 1:
        return 1
    else:
        return naive_lucas(n-1) + naive_lucas(n-2)

def matrix_test(n):
    lucas_col_matrix = matrix(2,1,[naive_lucas(n+1),naive_lucas(n)])
    fibo_col_matrix = matrix(2,1,[naive_fibo(n),naive_fibo(n-1)])
    fibo_col_matrix2 = matrix(2,1,[naive_fibo(n+1),naive_fibo(n)])
    lucas_col_matrix2 = matrix(2,1,[naive_lucas(n),naive_lucas(n-1)])
    lucas_multiplier = matrix(2,2,[3,1,1,2])
    print lucas_col_matrix, " are lucas numbers "
    print "------"
    print lucas_multiplier*fibo_col_matrix, " should equal lucas numbers "
    print "------------------------"
    print 5 * fibo_col_matrix2, " five times fibo col"
    print "------"
    print lucas_multiplier * lucas_col_matrix2, " should equal five times fibo col"
    print "--------------------------------------------------------"

def main():
    for x in range(2,100):
        matrix_test(x)