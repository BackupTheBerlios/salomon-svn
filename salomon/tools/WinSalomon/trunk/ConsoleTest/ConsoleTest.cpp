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
	Salomon salomon;
	std::cout << "Test start!" << std::endl;
	LibraryController* libraryController = salomon.getLibraryController();
	std::cout << "Test...";
	if (libraryController != 0)
	{
		std::cout << "sucess" << std::endl;
	}
	else
	{
		std::cout << "failure" << std::endl;
		return -1;
	}
	
	// getting ManagerEngine
	ManagerEngine* managerEngine = libraryController->getManagerEngine();

	if (managerEngine->getSolutionManager() == 0)
	{		
		std::cout << "failure" << std::endl;
		return -1;
	}
	
	// getting ProjectManager
	ProjectManager* projectManager = managerEngine->getProjectManger();
	if (projectManager != 0)
	{
		// creating project
		Project* project = projectManager->ceateProject();
		std::string projectName = "DLL test project";
		project->setName(projectName);
	} else {
		std::cout << "failure" << std::endl;
		return -1;
	}

	// getting TaskManager
	TaskManager* taskManager = managerEngine->getTaskManager();
	if ( taskManager != 0)
	{	
		// creating task
		Task* task = taskManager->createTask();
		if ( task != 0)
		{		
			std::cout << "Task created" << std::endl;
		} else {
			std::cout << "failure" << std::endl;
			return -1;
		}
		
		// setting task name
		std::string name = "DLLTask";
		task->setName(name);
	
		// setting plugin URL
		std::string url = "http://location.org/datasetunion.jar";
		
		// sample settings
		std::string settings = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";							   
		settings += "<!DOCTYPE struct SYSTEM \"config/struct.dtd\">";
		settings += "<struct><string name=\"resultDataSet\" value=\"ResultDataSet\"/>";
		settings += "<string name=\"firstDataSet\" value=\"second\"/>";
		settings += "<string name=\"secondDataSet\" value=\"third\"/></struct>";		
		
		// adding task
		taskManager->addTask(task, url, settings);

		// saving project
		projectManager->saveProject();
		
		// running task
		taskManager->start();
	}

	return 0;
}