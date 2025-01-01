javac *.java -d bin
rm bin/Glass.jar
echo "Main-Class: Main" >> bin/manifest.txt
jar cvmf bin/manifest.txt bin/Glass.jar -C bin .
rm bin/*.class bin/manifest.txt