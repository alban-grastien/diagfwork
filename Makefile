SRC_DIRS := src/
SRC_FILES := $(shell find $(SRC_DIRS) -name '*.java')
CLASS_FILES := $(patsubst src/%.java,classes/%.class,$(SRC_FILES))

# Actually won't regenerate the parser files.
#G_FILES := $(shell find $(SRC_DIRS) -name '*.g')
#G_JAVA_FILES := $(patsubst src/%.g,src/%.java,$(G_FILES))

LIBS := ../lib/antlr-3.2.jar:../lib/javabdd-1.0b2.jar:../lib/org.eclipse.draw2d_3.5.2.v20091126-1908.jar:../lib/swt.jar

all: $(CLASS_FILES)
	@echo KTHXBAY

classes/%.class: src/%.java
	@echo Compiling $*
	@cd src && javac -nowarn -Xlint:-deprecation -d ../classes/ -classpath .:$(LIBS) $(patsubst /,.,$*.java) && cd ../

# eof
