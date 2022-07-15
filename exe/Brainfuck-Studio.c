#pragma comment(linker, "/SUBSYSTEM:windows /ENTRY:mainCRTStartup")

#include <stdio.h>
#include <stdlib.h>
#include <windows.h>

int main() {
	HWND console = GetConsoleWindow();
	ShowWindow(console, SW_HIDE);
	system("java -jar jar/Brainfuck-Studio.jar");
	return 0;
}