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


#pragma once

#include <string>

#include "winsalomon.h"

#include "TaskManager.h"
#include "JavaObject.h"


DLL_SHARE class Project : public JavaObject
{
private:
	JNIEnv *env;
	static const char* CLASS_NAME;


public:
	Project(JNIEnv *env, jobject object) : JavaObject(env, CLASS_NAME, object)
	{
		// empty body
	}
	~Project(void)
	{
	}

	DLL_SHARE std::string getInfo();

	DLL_SHARE std::string getName();

	DLL_SHARE int getProjectID();

	DLL_SHARE void setInfo(std::string& info);

	DLL_SHARE void setName(std::string& name);

	DLL_SHARE void setProjectID(int projectId);

	DLL_SHARE TaskManager* getTaskManager();
};
