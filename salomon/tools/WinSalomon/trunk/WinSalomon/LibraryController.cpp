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

#include "librarycontroller.h"
#include "winsalomon.h"


const char* LibraryController::CLASS_NAME = "salomon/engine/controller/LibraryController";

DLL_SHARE ManagerEngine* LibraryController::getManagerEngine()
{
	std::cout << "Getting ManagerEngine...";

	jmethodID getManagerEngineMethod = this->findMethod("getManagerEngine", "()Lsalomon/engine/platform/IManagerEngine;");

	jobject managerEngine = this->getEnv()->CallObjectMethod(this->getObject(), getManagerEngineMethod);

	ManagerEngine* result = new ManagerEngine(getEnv(), managerEngine);

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

//LibraryController::LibraryController(void)
//{
//}
//
//LibraryController::~LibraryController(void)
//{
//}
