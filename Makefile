compile:
	javac src/*.java

launch: compile
	java -cp src/ Launcher

test: compile
	java -cp src/ Test
