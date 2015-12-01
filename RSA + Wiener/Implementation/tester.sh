#!/bin/bash
#sarah sahibzada
#math 491
echo "Name the working directory for tests, or enter NaN if you want to use the existing one"
read dirname
if [ dirname = "NaN" ]; then
	dirname = "OUTPUT"
else
	echo "Naming the file $filename"
fi
mkdir -p $dirname
cp *.java $dirname
cp grapher.py $dirname
cd $dirname
javac Globals.java ContinuedFraction.java WienerAttack.java WienerTester.java

MINBITSIZE=7
MAXBITSIZE=10
NUMTESTS=100 #simple test for right now
FILENAME="pqsize"
OUT="output"
DOTCSV=".csv"
while [ $MINBITSIZE -le $MAXBITSIZE ]; do 
	let MINBITSIZE=MINBITSIZE+1
	java WienerTester $MINBITSIZE $MINBITSIZE $NUMTESTS $FILENAME$MINBITSIZE$DOTCSV
done
echo "completed"