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


#include "taskmanager.h"
#include "stringhelper.h"

const char* TaskManager::CLASS_NAME = "salomon/engine/task/ITaskManager"; 

DLL_SHARE void TaskManager::addTask(Task* task)
{
	std::cout << "addTask...";

	jmethodID addTaskMethod = this->findMethod("addTask", "(Lsalomon/engine/task/ITask;)V");	
	
	this->getEnv()->CallVoidMethod(this->getObject(), addTaskMethod, task->getObject());	

	std::cout << "success" << std::endl;
}

DLL_SHARE void TaskManager::addTask(Task* task, std::string & url, std::string & settings)
{
	std::cout << "addTask...";

	jmethodID addTaskMethod = this->findMethod("addTask", "(Lsalomon/engine/task/ITask;Ljava/lang/String;Ljava/lang/String;)V");

	jstring strUrl = StringHelper::getString(getEnv(), url.c_str());

	jstring strSettings = StringHelper::getString(getEnv(), settings.c_str());
	
	this->getEnv()->CallVoidMethod(this->getObject(), addTaskMethod, task->getObject(), strUrl, strSettings);	
	
	this->getEnv()->DeleteLocalRef(strUrl);
	this->getEnv()->DeleteLocalRef(strSettings);

	std::cout << "success" << std::endl;
}

DLL_SHARE void TaskManager::clearTaskList()
{
	std::cout << "Not imlemented yet" << std::endl;
}

DLL_SHARE Task* TaskManager::createTask()
{
	std::cout << "createTask...";

	jmethodID createTaskMethod = this->findMethod("createTask", "()Lsalomon/engine/task/ITask;");	
	
	jobject task = this->getEnv()->CallObjectMethod(this->getObject(), createTaskMethod);
	
	Task* result = result = new Task(getEnv(), task);

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

DLL_SHARE Task* TaskManager::getCurrentTask()
{
	std::cout << "Not imlemented yet" << std::endl;
	return NULL;
}

DLL_SHARE void TaskManager::start()
{
	std::cout << "start..." << std::endl;

	jmethodID startMethod = this->findMethod("start", "()V");	
	
	this->getEnv()->CallVoidMethod(this->getObject(), startMethod);	
}
