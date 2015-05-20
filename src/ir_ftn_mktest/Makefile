# Makefile for the ir_ftn_mktest project.

SRCS= \
	Main.java

SRCS_W_PKG=$(addprefix ir_ftn_mktest/, $(SRCS) ) 

OBJS=$(subst .java,.class, $(SRCS) )

default: prereqs .build_complete

prereqs:
	cd ../IRTxtLib; make 

.build_complete: prereqs $(SRCS)
	cd ..; javac $(SRCS_W_PKG)
	@touch $@

clean:
	cd ../IRTxtLib; make clean
	rm -f $(OBJS) .build_complete

debug:
	@echo SRCS=$(SRCS)
	@echo OBJS=$(OBJS)

# end of file
