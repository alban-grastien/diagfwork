GFILES = $(wildcard *.g)
LEXERGENERATEDFILES = $(patsubst %.g,%Lexer.java,$(GFILES))

all: $(LEXERGENERATEDFILES)
	@echo "=== Compilation ok."
ifeq ($(MODIFIED),yes) 
	echo "!!! Don't forget to recompile in Eclipse !!!"
endif

%Lexer.java: %.g
	@echo "--- Generating Lexer & Parser from $<"
	java -cp ../../antlr-3.2.jar:. org.antlr.Tool $<
	@touch $*Lexer.java
	@touch $*Parser.java

