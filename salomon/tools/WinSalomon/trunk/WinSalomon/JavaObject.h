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

#include <iostream>

#include "winsalomon.h"

class JavaObject
{
private:
	JNIEnv *_env;
	jobject _object;
	jclass _class;
	const char* _className;

protected:
	JavaObject(JNIEnv *env, const char* className, jobject object) : _className(className)
	{
		_env = env;
		_object = object;
		if (_object == 0)
		{
			std::cout << "Instance of class " << className << " cannot be null!" << std::endl;
		}
		_class = _env->FindClass(className);
		if (_class == 0)
		{
			std::cout << "Cannot find class " << className << std::endl;
		}
	}

	//JavaObject(JNIEnv *env, jclass clas, jobject object)
	//{
	//	_env = env;
	//	_class = clas;
	//	_object = object;
	//}

	virtual ~JavaObject(void)
	{
	}

	JNIEnv *getEnv()
	{
		return _env;
	}

	jclass getClass()
	{
		return _class;
	}

	jmethodID findMethod(const char* name, const char* sygnature)
	{
		jmethodID result = _env->GetMethodID(_class, name, sygnature);
		if (result == 0)
		{
			std::cout << "Cannot find method! " << _className << "." << name << "(" << sygnature << ")" << std::endl;
		}

		return result;
	}
public:
	jobject getObject()
	{
		return _object;
	}

};
