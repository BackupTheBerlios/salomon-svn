/*
 * Copyright (C) 2005 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

#include "managerengine.h"

const char* ManagerEngine::CLASS_NAME = "salomon/engine/platform/IManagerEngine";

DLL_SHARE SolutionManager* ManagerEngine::getSolutionManager()
{
	std::cout << "Getting SolutionManager...";

	jmethodID getSolutionManagerMethod = this->findMethod("getSolutionManager", "()Lsalomon/engine/solution/ISolutionManager;");

	jobject solutionManager = this->getEnv()->CallObjectMethod(this->getObject(), getSolutionManagerMethod);

	SolutionManager* result = new SolutionManager(getEnv(), solutionManager);

	if (result != 0)
	{
		std::cout << "success" << std::endl;
	}
	else
	{
		std::cout << "failure" << std::endl;
	}

	return result;

}

DLL_SHARE ProjectManager* ManagerEngine::getProjectManger()
{
	std::cout << "Getting ProjecManager...";

	jmethodID getProjectManagerMethod = this->findMethod("getProjectManager", "()Lsalomon/engine/project/IProjectManager;");

	jobject projectManager = this->getEnv()->CallObjectMethod(this->getObject(), getProjectManagerMethod);

	ProjectManager* result = new ProjectManager(getEnv(), projectManager);

	if (result != 0)
	{
		std::cout << "success" << std::endl;
	}
	else
	{
		std::cout << "failure" << std::endl;
	}

	return result;

}

DLL_SHARE TaskManager* ManagerEngine::getTaskManager()
{
	std::cout << "Getting TaskManager...";

	jmethodID getTaskManagerMethod = this->findMethod("getTasksManager", "()Lsalomon/engine/task/ITaskManager;");

	jobject taskManager = this->getEnv()->CallObjectMethod(this->getObject(), getTaskManagerMethod);

	TaskManager* result = new TaskManager(getEnv(), taskManager);

	if (result != 0)
	{
		std::cout << "success" << std::endl;
	}
	else
	{
		std::cout << "failure" << std::endl;
	}

	return result;

}


//ManagerEngine::ManagerEngine(void)
//{
//}
//
//ManagerEngine::~ManagerEngine(void)
//{
//}
