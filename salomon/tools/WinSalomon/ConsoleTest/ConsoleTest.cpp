// This is the main project file for VC++ application project 
// generated using an Application Wizard.

//#include "stdafx.h"
#include <iostream>
#include <string>
#include <salomon.h>

#include "LibraryController.h"
#include "ManagerEngine.h"
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

	ManagerEngine* managerEngine = libraryController->getManagerEngine();

	if (managerEngine->getSolutionManager() != 0)
	{		
	}
	
	ProjectManager* projectManager = managerEngine->getProjectManger();
	if (projectManager != 0)
	{
		Project* project = projectManager->ceateProject();
	}

	TaskManager* taskManager = managerEngine->getTaskManager();
	if ( taskManager != 0)
	{	
		Task* task = taskManager->createTask();
		if ( task != 0)
		{		
			std::cout << "Task created" << std::endl;
		}
		task->setName("DLLTask");
	
		//taskManager->addTask(task);
		std::string url = "http://location.org/datasetunion.jar";
		
		std::string settings = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";							   
		settings += "<!DOCTYPE struct SYSTEM \"config/struct.dtd\">";
		settings += "<struct><string name=\"resultDataSet\" value=\"ResultDataSet\"/>";
		settings += "<string name=\"firstDataSet\" value=\"second\"/>";
		settings += "<string name=\"secondDataSet\" value=\"third\"/></struct>";		

		taskManager->addTask(task, url, settings);
		projectManager->saveProject();

		taskManager->start();
	}

	


	//if (managerEngine->getPluginManager() != 0)
	//{
	//	std::cout << "HURRA!!!" << std::endl;
	//}

	return 0;
}