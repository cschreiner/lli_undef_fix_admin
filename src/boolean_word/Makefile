
.PHONY: default

default: boolean_word_o0 boolean_word_o3

boolean_word_o0: boolean_word.c Makefile
	clang -emit-llvm -O0 -S -o boolean_word_o0 boolean_word.c
	mv boolean_word.ll boolean_word_o0.ll

boolean_word_o3: boolean_word.c Makefile
	clang -emit-llvm -O3 -S -o boolean_word_o3 boolean_word.c
	mv boolean_word.ll boolean_word_o3.ll


