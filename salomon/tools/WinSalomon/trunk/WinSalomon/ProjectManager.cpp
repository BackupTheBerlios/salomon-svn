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


#include "projectmanager.h"

const char* ProjectManager::CLASS_NAME = "salomon/engine/project/IProjectManager";

DLL_SHARE void ProjectManager::addProject(Project* project)
{
	std::cout << "Not imlemented yet" << std::endl;
}

DLL_SHARE Project* ProjectManager::ceateProject()
{
	std::cout << "createProject...";

	jmethodID ceateProjectMethod = this->findMethod("createProject", "()Lsalomon/engine/project/IProject;");	
	
	jobject project = this->getEnv()->CallObjectMethod(this->getObject(), ceateProjectMethod);
	
	Project* result = result = new Project(getEnv(), project);

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

//DLL_SHARE Project* getProjects();

DLL_SHARE Project* ProjectManager::getProject(int projectID)
{
	std::cout << "Not imlemented yet" << std::endl;
	return NULL;
}

DLL_SHARE void ProjectManager::saveProject()
{
	std::cout << "saveProject...";

	jmethodID saveProjectMethod = this->findMethod("saveProject", "()V");	
	
	this->getEnv()->CallVoidMethod(this->getObject(), saveProjectMethod);

	std::cout << "success" << std::endl;
}
    
DLL_SHARE Project* ProjectManager::getCurrentProject()
{
	std::cout << "Not imlemented yet" << std::endl;
	return NULL;
}

//ProjectManager::ProjectManager(void)
//{
//}
//
//ProjectManager::~ProjectManager(void)
//{
//}
