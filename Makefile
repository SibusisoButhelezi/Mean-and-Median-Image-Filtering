JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
JAVADOC=Javadocs
$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<	
	
CLASSES = MeanFilterSerial.class MedianFilterSerial.class MeanFilterParallel.class MedianFilterParallel.class PictureMatch.class 
CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)
default: $(CLASS_FILES) 

docs: 
	javadoc -d Javadocs $(SRCDIR)/*.java

run: $(CLASS_FILES)
	java -cp bin MeanFilterSerial $(ARGS)

clean:
	rm $(BINDIR)/*.class
