windres resources.rc -O coff -o resources.res
gcc -std=c11 Brainfuck-Studio.c resources.res -o Brainfuck-Studio 