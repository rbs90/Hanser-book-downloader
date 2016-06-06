JCC = javac
JAR = jar
JFLAGS = -g -cp "lib/*"

default: classes jar

classes: 
	$(JCC) $(JFLAGS) src/*.java

jar:
	mkdir -p jar_build
	unzip -od jar_build "lib/*.jar"
	cp src/*.class jar_build/
	#create jar file: c-create, f-file, m-no manifest
	jar cmf src/META-INF/MANIFEST.MF Hanser.jar -C jar_build . 

clean: 
	$(RM) *.class
	$(RM) -R jar_build/
