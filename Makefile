compile:
	javac src/*.java -d bin/

launch: compile
	java -cp bin/ Launcher

launchtext: compile
	java -cp bin/ Launcher text

test: compile
	java -cp bin/ Test

clean:
	rm bin/*.class
