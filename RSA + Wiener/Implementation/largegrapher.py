import pandas as pd 
import matplotlib.pyplot as pyplot #graph rendering
from mpl_toolkits.mplot3d import Axes3D #graph rendering
import collections 
from collections import Counter
import math
import threading
#import multiprocessing as mp #using pandas instead, but i definitely did enough with this to mention
import glob
import os
import pylab #bulldog2.redlands.edu/facultufolder/deweerd/tutorials/histogrambin.html
from pylab import *
global_x = []
global_y = []

#extra stuff to compute successful keys in opt-- doing this here due to large number of files
small_keys = []
small_key_frequency = []

path1 = 'metadata'
path2 = 'optimization'
def process_standard_wiener_attack(filename,lock):
	df = pd.read_csv(filename,header=True,names=['n', 'phi(n)', 'e', 'd', 'cap(d)', 's/f' ])
	#print df
	d_values = df['d'].tolist()
	sf_values = df['s/f'].tolist()
	cap_values = df['cap(d)'].tolist()
	distance_data = [((actual - cap),sf) for actual , cap,sf in (zip(d_values, cap_values,sf_values)) if sf != 0]
	distance_data.sort(key=lambda tup: tup[0])
	#filter(lambda match: match[1] != 0, distance_data)
	#print distance_data
	lock.acquire()
	global_x.append([x[0] for x in distance_data])
	global_y.append([x[1] for x in distance_data])

	#print global_x #it worked!
	#print global_y #it worked!
	lock.release()
	'''for d in distance_data:
		if d[1] == 1:
			print d'''
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
def process_modified_wiener_attack(filename,lock):
	df = pd.read_csv(filename,header=True,names = ['n','e','d','p','q','k','del','u','v','s'] )
	d_values = df['d'].tolist()
	sf_values = df['s'].tolist()
	n_values = df['n'].tolist()
	cap_values = [((float(n))**(1./4))/3 for n in n_values ]
	distance_data = [((actual-cap),sf) for actual,cap,sf in zip(d_values,cap_values,sf_values) if (sf ==1)]
	local_good_keys = [(d,sf) for d,sf in zip(d_values,sf_values) if sf == 1]
	count_keys = [[local_key,local_good_keys.count(local_key)] for local_key in set(local_good_keys)]
	print count_keys
	#opt data is ridden with NaNs because of the file layout
	distance_data.sort(key=lambda tup: tup[0])
	lock.acquire()
	global_x.append([x[0] for x in distance_data])
	global_y.append([x[1] for x in distance_data])

	lock.release()
	'''for d in distance_data:
		if d[1] == 1:
			print d
		else:
			print 'did not pass here, distance = ' + str(d[0])'''



def success_scatter(mode):
	if mode == 'reg':
		file_names = [f for f in glob.glob('*.csv')]
		lock = threading.Lock()
		for fn in file_names:
			process_standard_wiener_attack(fn,lock) #process all of the little ones
		local_x = [x for sublist in global_x for x in sublist]
		local_y = [y for sublist in global_y for y in sublist]

		#and now graph
		graph = pyplot.figure()
		ax = graph.add_subplot(111)
		ax.set_title('Standard Attack Successful Keys')
		ax.set_xlabel('d - 1/3 n ^ 1/4')
		ax.set_ylabel('Success')
		ax.scatter(local_x,local_y, c='b',marker='o')
		#print zlist
		pyplot.show()
	elif mode == 'opt':
		if os.path.isdir(path2):
			os.chdir(path2)
		file_names = [f for f in  glob.glob('*.csv')]
		lock = threading.Lock()
		for fn in file_names:
			process_modified_wiener_attack(fn,lock)
		local_x = [x for sublist in global_x for x in sublist]
		local_y = [y for sublist in global_y for y in sublist]
		print local_x
		print local_y
		graph = pyplot.figure()
		ax = graph.add_subplot(111)
		ax.set_title('Optimized Attack Successful Keys')
		ax.set_xlabel('d - 1/3 n ^ 1/4')
		ax.set_ylabel('Success')
		ax.scatter(local_x,local_y, c='g',marker='o')
		#print zlist
		pyplot.show()

def make_scatters():
	#success_scatter('reg')
	success_scatter('opt')

def success_histogram(mode):
	if mode == 'reg':
		file_names = [f for f in glob.glob('*.csv')]
		lock = threading.Lock()
		for fn in file_names:
			process_standard_wiener_attack(fn,lock) #process all of the little ones
		local_x = [x for sublist in global_x for x in sublist]
		local_y = [y for sublist in global_y for y in sublist]
		
		test = array(local_x)
		pylab.ylabel('Number of Successes')
		pylab.xlabel('d - 1/3 n ^ 1/4')
		pylab.title('Success Distribution, Standard Wiener Attack')
		figure()
		hist(test)
		show()
	elif mode == 'opt':
		if os.path.isdir(path2):
			os.chdir(path2)
		file_names = [f for f in  glob.glob('*.csv')]
		lock = threading.Lock()
		for fn in file_names:
			process_modified_wiener_attack(fn,lock)
		local_x = [x for sublist in global_x for x in sublist]
		local_y = [y for sublist in global_y for y in sublist]

		test = array(local_x)
		
		figure()
		pylab.ylabel('Number of Successes')
		pylab.xlabel('d - 1/3 n ^ 1/4')
		pylab.title('Success Distribution, Optimized Wiener Attack')
		hist(test)
		show()




	
lock = threading.Lock()

process_modified_wiener_attack('output_135545527681.csv',lock)
#make_scatters()
#success_histogram('opt')
