import numpy
import matplotlib.pyplot as pyplot
import math
import csv


def proper_divisors_list(arg):
	divisors = []
	print "Writing ", arg
	for x in range(1, int(arg/2)+1):
		if (arg % x == 0):
			divisors.append(x)
	divisors.append(arg)
	return divisors

def write_to_file(name, max_range):
	filename = name + ".csv"
	with open(filename, 'a') as csvwriter:
		writer = csv.writer(csvwriter, delimiter = ',')
		for x in range(1,max_range):
			divisors = proper_divisors_list(x)
			for divisor in divisors:
				csvwriter.write(str(divisor) + ",")
			csvwriter.write("\n")

write_to_file("divisors_data_set_to_1billion", 1000000000)


