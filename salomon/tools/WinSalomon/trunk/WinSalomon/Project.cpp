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


#include "project.h"
#include "stringhelper.h"

const char* Project::CLASS_NAME = "salomon/engine/project/IProject";

DLL_SHARE std::string Project::getInfo()
{
	return NULL;
}

DLL_SHARE std::string Project::getName()
{
	return NULL;
}

DLL_SHARE int Project::getProjectID()
{
	return NULL;
}

DLL_SHARE void Project::setInfo(std::string& info)
{
	std::cout << "setInfo...";

	jmethodID setInfoMethod = this->findMethod("setInfo", "(Ljava/lang/String;)V");	

	jstring strInfo = StringHelper::getString(getEnv(), info.c_str());
	
	this->getEnv()->CallVoidMethod(this->getObject(), setInfoMethod, strInfo);
}

DLL_SHARE void Project::setName(std::string& name)
{
	std::cout << "setName...";

	jmethodID setNameMethod = this->findMethod("setName", "(Ljava/lang/String;)V");	

	jstring strName = StringHelper::getString(getEnv(), name.c_str());
	
	this->getEnv()->CallVoidMethod(this->getObject(), setNameMethod, strName);
}

DLL_SHARE void Project::setProjectID(int projectId)
{
}

DLL_SHARE TaskManager* Project::getTaskManager()
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
