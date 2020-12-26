compile:
	javac src/*.java

launch: compile
	java -cp src/ Launcher

launchtext: compile
	java -cp src/ Launcher text

test: compile
	java -cp src/ Test

clean:
	rm src/*.class
