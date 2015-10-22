
.PHONY: default clean

default: boolean_word_o0 boolean_word_o3

boolean_word_o0: boolean_word.c Makefile
	@echo ==========================================================
	@echo making at o0
	clang -emit-llvm -c -O0 -S boolean_word.c
	mv boolean_word.ll boolean_word_o0.ll
	clang -O0 -o boolean_word_o0 boolean_word.c

boolean_word_o3: boolean_word.c Makefile
	@echo ==========================================================
	@echo making at o3
	clang -emit-llvm -c -O3 -S boolean_word.c
	mv boolean_word.ll boolean_word_o3.ll
	clang -O3 -o boolean_word_o3 boolean_word.c

clean:
	rm *.ll boolean_word_o*


