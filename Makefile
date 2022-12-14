JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
JAVADOC=Javadocs

# User defined arguements
# Enter <inputImageName> <outputImageName> <windowWidth>
# Example: Image1.jpg Image1Out.jpg 3
ARGS = Image3.jpg Image3OutMedianP.jpg 11

ARGSALL1 = Image1.jpg Image1Out.jpg
ARGSALL2 = Image2.jpg Image2Out.jpg
ARGSALL3 = Image3.jpg Image3Out.jpg
ARGSALL4 = Image4.jpg Image4Out.jpg

 ARGSALL = $(ARGSALL1)
# ARGSALL = $(ARGSALL2)
# ARGSALL = $(ARGSALL3)
# ARGSALL = $(ARGSALL4)



$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<	
	
CLASSES = MeanFilterSerial.class MedianFilterSerial.class MeanFilterParallel.class MedianFilterParallel.class PictureMatch.class 
CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)
default: $(CLASS_FILES) 

docs: 
	javadoc -d Javadocs $(SRCDIR)/*.java

runAllMeanSerial: $(CLASS_FILES)
	java -cp bin MeanFilterSerial $(ARGSALL) 3 
	java -cp bin MeanFilterSerial $(ARGSALL) 5
	java -cp bin MeanFilterSerial $(ARGSALL) 11 
	java -cp bin MeanFilterSerial $(ARGSALL) 15
	java -cp bin MeanFilterSerial $(ARGSALL) 21

runAllMedianSerial: $(CLASS_FILES)
	java -cp bin MedianFilterSerial $(ARGSALL) 3 
	java -cp bin MedianFilterSerial $(ARGSALL) 5
	java -cp bin MedianFilterSerial $(ARGSALL) 11 
	java -cp bin MedianFilterSerial $(ARGSALL) 15
	java -cp bin MedianFilterSerial $(ARGSALL) 21

runAllMeanParallel: $(CLASS_FILES)
	java -cp bin MeanFilterParallel $(ARGSALL) 3 
	java -cp bin MeanFilterParallel $(ARGSALL) 5
	java -cp bin MeanFilterParallel $(ARGSALL) 11 
	java -cp bin MeanFilterParallel $(ARGSALL) 15
	java -cp bin MeanFilterParallel $(ARGSALL) 21

runAllMedianParallel: $(CLASS_FILES)
	java -cp bin MedianFilterParallel $(ARGSALL) 3 
	java -cp bin MedianFilterParallel $(ARGSALL) 5
	java -cp bin MedianFilterParallel $(ARGSALL) 11 
	java -cp bin MedianFilterParallel $(ARGSALL) 15
	java -cp bin MedianFilterParallel $(ARGSALL) 21

# Running user input

runMeanFilterSerial: $(CLASS_FILES)
	java -cp bin MeanFilterSerial $(ARGS) 

runMedianFilterSerial: $(CLASS_FILES)
	java -cp bin MedianFilterSerial $(ARGS)

runMeanFilterParallel: $(CLASS_FILES)
	java -cp bin MeanFilterParallel $(ARGS) 

runMedianFilterParallel: $(CLASS_FILES)
	java -cp bin MedianFilterParallel $(ARGS) 

clean:
	rm $(BINDIR)/*.class
