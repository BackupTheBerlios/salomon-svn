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

#include "JavaObject.h"
#include "Solution.h"


DLL_SHARE class SolutionManager : public JavaObject
{
private:
	JNIEnv *env;

	static const char* CLASS_NAME;


public:
	SolutionManager(JNIEnv *env, jobject object) : JavaObject(env, CLASS_NAME, object)
	{
		// empty body
	}

	~SolutionManager(void)
	{
	}

	DLL_SHARE Solution* getSolutions();

	DLL_SHARE Solution getSolution(std::string name);

	DLL_SHARE Solution createSolution();

	DLL_SHARE void addSolution(Solution* solution);
};
