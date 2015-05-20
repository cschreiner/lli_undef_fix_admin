# Makefile for the ir_ftn_mktest project.

SRCS= \
	Main.java

SRCS_W_PKG=$(addprefix ir_ftn_mktest/, $(SRCS) ) 

OBJS=$(subst .java,.class, $(SRCS) )

default: .build_complete

.build_complete: ../IRTxtLib/.build_complete $(SRCS)
	cd ..; javac $(SRCS_W_PKG)
	@touch $@

../IRTxtLib/.build_complete:
	cd ../IRTxtLib; make

clean:
	cd ../IRTxtLib; make clean
	rm -f $(OBJS) .build_complete

debug:
	@echo SRCS=$(SRCS)
	@echo OBJS=$(OBJS)

# end of file
