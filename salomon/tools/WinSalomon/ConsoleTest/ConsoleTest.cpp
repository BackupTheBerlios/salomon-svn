// This is the main project file for VC++ application project 
// generated using an Application Wizard.

//#include "stdafx.h"
#include <iostream>
#include <salomon.h>

#include "LibraryController.h"
//__declspec(dllimport) void __stdcall void myfoo();

//extern void myfoo();

int main()
{
	Salomon a;
	std::cout << "Test start!" << std::endl;
	LibraryController* libraryController = a.getLibraryController();
	std::cout << "Test...";
	if (libraryController != 0)
	{
		std::cout << "sucess" << std::endl;
	}
	else
	{
		std::cout << "failure" << std::endl;
	}

	if (libraryController->getManagerEngine() != 0)
	{
		std::cout << "HURRA!!!" << std::endl;
	}

	return 0;
}