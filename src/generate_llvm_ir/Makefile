# Makefile for the generate_llvm_ir project.

SRCS= \
	AddrName.java \
	BasicBlockSeqInitializer.java \
	BasicBlockSeq.java \
	CodeChunk.java \
	Function.java \
	Instruction.java \
	Main.java \
	OpcodeCharacteristics.java \
	RegContext.java \
	RegWithType.java \
	SeededRandom.java \
	TypeInteger.java

SRCS_W_PKG=$(addprefix generate_llvm_ir/, $(SRCS) ) 

OBJS=$(subst .java,.class, $(SRCS) )

default:
	cd ..; javac $(SRCS_W_PKG)

clean:
	rm -f $(OBJS)

debug:
	@echo SRCS=$(SRCS)
	@echo OBJS=$(OBJS)

# end of file
