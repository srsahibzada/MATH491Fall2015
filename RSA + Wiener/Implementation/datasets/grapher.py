''' CSV row : n, phi(n), e, d, cap(d), s/f 
	Graph: x-axis n, y-axis d, z-axis s/f

'''

import matplotlib.pyplot as pyplot
from mpl_toolkits.mplot3d import Axes3D
import csv

xlist = []
ylist = []
zlist = []

filename = raw_input("What CSV:")
with open(filename, 'rb') as csvfile:
	reader = csv.reader(csvfile)
	for row in reader:
		xlist.append(row[0])
		ylist.append(row[3])
		zlist.append(row[5])

graph = pyplot.figure()
ax = fig.add_subplot(111,projection='3d')

