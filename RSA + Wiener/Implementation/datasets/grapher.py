''' 
	CSV row : n, phi(n), e, d, cap(d), s/f 
	Graph: x-axis n, y-axis d, z-axis s/f
	Sarah Sahibzada
	MATH 491
	BS Comp Sci & Applied Math, Texas A&M 2016

'''

import matplotlib.pyplot as pyplot #graph rendering
from mpl_toolkits.mplot3d import Axes3D #graph rendering
import csv #read and write to csv files
import os #make directories to which to write files (don't need bash for this!!)
import glob #search in directory
import threading #speed up the writing of massive data sets
import numpy # the graph on http://matplotlib.org/examples/shapes_and_collections/scatter_demo.html was really pretty
			#  so i'm replicating it with our data

#distance global lists for s/f ratios, large graph of success rate
distances_global = []
final_global = []

#successful small key global list and success rates
small_keys = []
small_key_frequencies = []

#small keys with size field
small_keys2 = []
small_key_frequencies_distributed_over_size = []

#string constants that are useful
comma = ','
newline = '\n'
output_directory_path = "metadata"



def graphics3d(filename):
	xlist = []
	ylist = []
	zlist = []

	#filename = raw_input("What CSV:")
	with open(filename, 'rb') as csvfile:
		reader = csv.reader(csvfile)
		for row in reader:
			xlist.append(int(row[0]))
			ylist.append(int(row[3]))
			zlist.append(int(row[5]))

	graph = pyplot.figure()
	ax = graph.add_subplot(111,projection='3d')
	ax.scatter(xlist,ylist,zlist, c='b',marker='o')
	#print zlist
	pyplot.show()

def small_key_scatter():
	N = len(small_keys)
	x_axis = numpy.asarray([x[0] for x in small_key_frequencies])
	y_axis = numpy.asarray([x[1] for x in small_key_frequencies])
	colors = numpy.random.rand(N)
	area = numpy.pi * (50 * numpy.random.rand(N)) ** 2
	pyplot.scatter(x_axis,y_axis,s=area,c=colors)
	pyplot.show()

def small_key_over_size_scatter():
	x_axis = [x[0] for x in small_key_frequencies_distributed_over_size]
	y_axis = [x[1] for x in small_key_frequencies_distributed_over_size]
	z_axis = [x[2] for x in small_key_frequencies_distributed_over_size]
	print x_axis
	print y_axis
	print z_axis
	graph = pyplot.figure()
	ax = graph.add_subplot(111,projection='3d')
	ax.scatter(x_axis,y_axis,z_axis,c='g',marker='o')
	ax.set_xlabel("Key")
	ax.set_ylabel("1/3 n ^ 1/4")
	ax.set_zlabel("Number of Successes")
	pyplot.show()

#runnable to compute distance and s/f ratio
def reader_thread1(filename,lock):
	try:
		with open(filename, 'rb') as csvfile:
			reader = csv.reader(csvfile)
			for row in reader:
				print float(row[4]), float(row[3])
				dist = float(row[4]) - float(row[3])
				s = row[5]
				#critical section
				lock.acquire()
				if dist not in distances_global:
					distances_global.append(dist)
					if int(s) == 0:
						new = [dist,0,1]  #distance with 1 failure, 0 success
						final_global.append(new)
					else:
						new = [dist,1,0] #distance with 1 success, 0 failure
						final_global.append(new)
					#print "added distance of " + str(dist) + "\n"
				else:
					for fg in final_global:
						if fg[0] == dist:
							if int(s) == 1:
								fg[1] += 1
							else:
								fg[2] += 0

				lock.release()
	except IndexError:
		return #nothing has been locked yet
	except IOError:
		print "Error: file not found" 
		return

#runnable to find all small keys and their number of successes
def reader_thread2(filename,lock):
	try:
		with open(filename, 'rb') as csvfile:
			reader = csv.reader(csvfile)
			for row in reader:
				if int(row[5]) == 1:
					smallkey = int(row[3]) #grab small key
					lock.acquire()
					if smallkey not in small_keys:
						small_keys.append(smallkey)
						new = [smallkey,1]
						small_key_frequencies.append(new)
					else: 
						for skf in small_key_frequencies:
							if skf[0] == smallkey:
								skf[1] += 1
					lock.release()
	except IndexError:
		return #nothing has been locked yet
	except IOError:
		print "Error: file not found" 
		return

#runnable to find all small keys and their number of successes
def reader_thread3(filename,lock):
	try:
		with open(filename, 'rb') as csvfile:
			reader = csv.reader(csvfile)
			for row in reader:
				if int(row[5]) == 1:
					smallkey = int(row[3]) #grab small key
					lock.acquire()
					if smallkey not in small_keys2:
						small_keys2.append(smallkey)
						new = [smallkey,float(row[4]),1]
						small_key_frequencies_distributed_over_size.append(new)
					else: 
						for skf in small_key_frequencies_distributed_over_size:
							if skf[0] == smallkey:
								skf[2] += 1
					lock.release()
	except IndexError:
		return #nothing has been locked yet
	except IOError:
		print "Error: file not found" 
		return

#checking my work
def search_successful_key(filename,num):
	with open(filename, 'rb') as csvfile:
		count = 0
		reader = csv.reader(csvfile)
		for row in reader:
			if int(row[3]) == num and int(row[5]) == 1:
				count += 1
		print count


def small_key_writer():
	path = "MATH491Project3/RSA + Wiener/Implementation/datasets"
	file_names = [f for f in glob.glob('*.csv')] 
	#file_names = ['pqsize8_1.csv','pqsize8.csv']
	lock = threading.Lock()
	tds = []
	for fn in file_names:
		t = threading.Thread(target=reader_thread2,args=(fn,lock))
		tds.append(t)
		t.start()
	main = threading.currentThread()
	for t in tds:
		if t is not main:
			t.join()
	'''if os.path.isdir(output_directory_path):
		os.chdir(output_directory_path)	
		with open('copyfrequency.csv','a') as csvfile:
			writer = csv.writer(csvfile)
			for skf in small_key_frequencies:
				writer.writerow([str(skf[0])] + [str(skf[1])] )
	else:
		os.mkdir(output_directory_path)
		os.chdir(output_directory_path)
		with open('copyfrequency.csv','a') as csvfile:
			writer = csv.writer(csvfile)
			for skf in small_key_frequencies:
				writer.writerow([str(skf[0])] + [str(skf[1])] )''' #commenting out to make scatter

def small_key_distributed():
	path = "MATH491Project3/RSA + Wiener/Implementation/datasets"
	file_names = [f for f in glob.glob('*.csv')] 
	#file_names = ['pqsize8_1.csv','pqsize8.csv']
	lock = threading.Lock()
	tds = []
	for fn in file_names:
		t = threading.Thread(target=reader_thread3,args=(fn,lock))
		tds.append(t)
		t.start()
	main = threading.currentThread()
	for t in tds:
		if t is not main:
			t.join()
	#before mtg: finish this up / update it
	'''if os.path.isdir(output_directory_path):
		os.chdir(output_directory_path)	
		with open('copyfrequency.csv','a') as csvfile:
			writer = csv.writer(csvfile)
			for skf in small_key_frequencies:
				writer.writerow([str(skf[0])] + [str(skf[1])] )
	else:
		os.mkdir(output_directory_path)
		os.chdir(output_directory_path)
		with open('copyfrequency.csv','a') as csvfile:
			writer = csv.writer(csvfile)
			for skf in small_key_frequencies:
				writer.writerow([str(skf[0])] + [str(skf[1])] )'''


def success_rate_writer():
	path = "MATH491Project3/RSA + Wiener/Implementation/datasets"
	file_names = [f for f in glob.glob('*.csv')]
	#file_names = ['pqsize8_1.csv']
	#reader_thread('pqsize8_1.csv')
	lock = threading.Lock()
	tds = []
	for fn in file_names:
		t = threading.Thread(target=reader_thread1,args=(fn,lock))
		tds.append(t)
		t.start()
	main = threading.currentThread()
	for t in tds:
		if t is not main:
			t.join()
	if os.path.isdir(output_directory_path):
		os.chdir(output_directory_path)	
		with open('distance_output.csv', 'a') as csvfile:
			writer = csv.writer('distance_output.csv')
			for fg in final_global:
				writer.writerow([str(fg[0])] + [str(fg[1])]  + [str(fg[2])] )
	else: 
		os.mkdir(output_directory_path)
		os.chdir(output_directory_path)
		with open('distance_output.csv', 'a') as csvfile:
			writer = csv.writer('distance_output.csv')
			for fg in final_global:
				writer.writerow([str(fg[0])] + [str(fg[1])]  + [str(fg[2])] )
	
		
#small_key_writer()
#success_rate_writer()
#small_key_scatter()

small_key_distributed()
small_key_over_size_scatter()
'''
#spot check to verify frequency file
search_successful_key('pqsize8_1.csv',11)
search_successful_key('pqsize8_1.csv',13)
search_successful_key('pqsize8_1.csv',27073)
search_successful_key('pqsize8_1.csv',5)
search_successful_key('pqsize8_1.csv',17)
'''
