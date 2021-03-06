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


#include "task.h"
#include "stringhelper.h"

const char* Task::CLASS_NAME = "salomon/engine/task/ITask";

DLL_SHARE std::string Task::getName()
{
	std::cout << "Not imlemented yet" << std::endl;
	return NULL;
}

DLL_SHARE std::string Task::getResult()
{
	std::cout << "Not imlemented yet" << std::endl;
	return NULL;
}

DLL_SHARE int Task::getTaskId()
{
	std::cout << "Not imlemented yet" << std::endl;
	return 0;
}

DLL_SHARE void Task::setName(std::string & name)
{
	std::cout << "setName...";

	jmethodID setNameMethod = this->findMethod("setName", "(Ljava/lang/String;)V");	

	jstring strName = StringHelper::getString(getEnv(), name.c_str());
	
	this->getEnv()->CallVoidMethod(this->getObject(), setNameMethod, strName);
}

DLL_SHARE void Task::setSettings(std::string & settings)
{
	std::cout << "Not imlemented yet" << std::endl;
}

DLL_SHARE void Task::setTaskId(int taskId)
{
	std::cout << "Not imlemented yet" << std::endl;
}

