# Makefile for the generate_llvm_ir project.

SRCS= \
	AddrName.java \
	BasicBlockSeq.java \
	Function.java \
	Instruction.java \
	Main.java \
	RegContext.java \
	TypeInteger.java 

OBJS=$(subst .java,.class, $(SRCS) )

default:
	javac $(SRCS)

clean:
	rm -f $(OBJS)

debug:
	@echo SRCS=$(SRCS)
	@echo OBJS=$(OBJS)

# end of file
