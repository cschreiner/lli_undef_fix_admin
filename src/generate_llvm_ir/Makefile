# Makefile for the generate_llvm_ir project.

SRCS= \
	AddrName.java \
	BasicBlockSeq.java \
	Function.java \
	Instruction.java \
	Main.java \
	RegContext.java \
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
