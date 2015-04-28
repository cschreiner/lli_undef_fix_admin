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

default: .build_complete

.build_complete:
	cd ..; javac $(SRCS_W_PKG)
	@touch $@

clean:
	rm -f $(OBJS) .build_complete

debug:
	@echo SRCS=$(SRCS)
	@echo OBJS=$(OBJS)

# end of file
