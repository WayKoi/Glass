javac *.java -d bin
del bin/Glass.jar
echo "Main-Class: Main" >> bin/manifest.txt
jar cvmf bin/manifest.txt bin/Glass.jar -C bin .
del bin/*.class bin/manifest.txt