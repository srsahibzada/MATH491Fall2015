import pandas as pd 
import matplotlib.pyplot as pyplot #graph rendering
from mpl_toolkits.mplot3d import Axes3D #graph rendering
import collections as ct
import math

def process_frame(df):
	return len(df)


def process_standard_wiener_attack(filename):
	df = pd.read_csv(filename,header=True,names=['n', 'phi(n)', 'e', 'd', 'cap(d)', 's/f' ])
	#print df
	d_values = df['d'].tolist()
	sf_values = df['s/f'].tolist()
	cap_values = df['cap(d)'].tolist()
	distance_data = [((actual - cap),sf) for actual , cap,sf in (zip(d_values, cap_values,sf_values))]
	distance_data.sort(key=lambda tup: tup[0])
	for d in distance_data:
		if d[1] == 1:
			print d
	#counter = ct.Counter(distance_data)
	#print counter
	#process distances



	#the writing part
	'''
	graph = pyplot.figure()
	ax = graph.add_subplot(111)

	ax.scatter(distances,sf_values, c='b',marker='o')
	#print zlist
	pyplot.show()
	'''
#n e d p q k del  u v s
def process_modified_wiener_attack(filename):
	df = pd.read_csv(filename,header=True,names = ['n','e','d','p','q','k','del','u','v','s'] )
	d_values = df['d'].tolist()
	sf_values = df['s'].tolist()
	n_values = df['n'].tolist()
	cap_values = [((float(n))**(1./4))/3 for n in n_values ]
	distance_data = [((actual-cap),sf) for actual,cap,sf in zip(d_values,cap_values,sf_values)]
	for d in distance_data:
		if d[1] == 1:
			print d
		else:
			print 'did not pass here, distance = ' + str(d[0])

process_modified_wiener_attack('output_135545527681.csv')
