# Makefile for the IRTxtLib (LLVM IR Textual Format Processing Library) project.

SRCS= \
	FileToFtnParser.java \
	FtnParts.java \
	IRToken.java \
	IRTokenType.java \
	IRTxtLib.java \
	RegWithType.java \
	SeededRandom.java \
	TypeInteger.java

SRCS_W_PKG=$(addprefix IRTxtLib/, $(SRCS) ) 

OBJS=$(subst .java,.class, $(SRCS) )

default: .build_complete

.build_complete: $(SRCS)
	cd ..; javac $(SRCS_W_PKG)
	@touch $@

clean:
	rm -f $(OBJS) .build_complete

debug:
	@echo SRCS=$(SRCS)
	@echo OBJS=$(OBJS)

# end of file
