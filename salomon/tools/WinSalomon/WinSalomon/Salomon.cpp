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

#include <iostream>

#include "salomon.h"

DLL_SHARE LibraryController* Salomon::getLibraryController()
{
	std::cout << "Getting LibraryController...";

	jclass starterClass = _env->FindClass("salomon/engine/Starter");
	if (starterClass == 0)
	{
		std::cout << "Cannot find Starter!" << std::endl;
	}

	jmethodID createLibraryControllerMethod = _env->GetStaticMethodID(starterClass, "createLibraryController",
		"()Lsalomon/engine/controller/LibraryController;");

	if (createLibraryControllerMethod == 0)
	{
		std::cout << "Cannot find method createLibraryController!" << std::endl;
	}

	jobject libraryControllerJava = _env->CallStaticObjectMethod(starterClass, createLibraryControllerMethod);

	LibraryController* libraryController = new LibraryController(_env, libraryControllerJava);

	if (libraryController != 0)
	{
		std::cout << "success" << std::endl;
	}
	else
	{
		std::cout << "failure" << std::endl;
	}

	return libraryController;
}


#define NULL_CHECK0(e) if ((e) == 0) return 0 

//void Salomon::runTask()
//{
//	std::cout << "Run task method" << std::endl; 
//}


Salomon::Salomon()
{
	JavaVMOption options[1];
	//JNIEnv *env;
	//JavaVM *jvm;
	JavaVMInitArgs vm_args;
	long status;
	jclass starterClass;
	jmethodID mid;
	jint square;
	jboolean not;

//	options[0].optionString = "-Djava.class.path=.;Sample.jar;Notepad.jar";
	options[0].optionString = "-Djava.class.path=.;Notepad.jar";
	options[0].optionString = "-Djava.class.path=.;Salomon.jar;Notepad.jar";
	memset(&vm_args, 0, sizeof(vm_args));
	vm_args.version = JNI_VERSION_1_2;
	vm_args.nOptions = 1;
	vm_args.options = options;
	status = JNI_CreateJavaVM(&_jvm, (void**)&_env, &vm_args);

	jclass libraryControllerClass;
	if (status != JNI_ERR)
	{
		//cls = env->FindClass("sample/Sample2");
		//libraryControllerClass = env->FindClass("salomon/engine/database/DBManager");
//		libraryControllerClass = env->FindClass("salomon/engine/controller/LibraryController");
//		cls = env->FindClass("Notepad");
		//starterClass = env->FindClass("salomon/engine/Starter");
		//if(starterClass !=0)
		//{   
		//	std::cout << "Class \"Starter\" was found!" << std::endl;
		//	mid = env->GetStaticMethodID(starterClass, "createLibraryController",
		//		       "()Lsalomon/engine/controller/LibraryController;"); 

		//	if(mid !=0)
		//	{	
		//		jobject libraryController = env->CallStaticObjectMethod(starterClass, mid);
		//		if (libraryController != 0)
		//		{
		//			std::cout << "Super!!!" << std::endl;
		//		}
		//		else
		//		{
		//			std::cout << "I'am sad :-(" << std::endl;
		//		}
		//	}
		//	else
		//	{
		//		std::cout << "Cannot find \"createLibraryController\" method!" << std::endl;
		//	}
		//	
		//} else {
		//	std::cout << "Class not found " << std::endl;
		//}

	}
	else {
		std::cout << "JVM error" << std::endl;			
	}
}

Salomon::~Salomon(void)
{
	std::cout << "Destruktor" << std::endl;
	_jvm->DestroyJavaVM();
}

jstring
Salomon::NewPlatformString(JNIEnv *env, char *s)
{    
    int len = (int)strlen(s);
    jclass cls;
    jmethodID mid;
    jbyteArray ary;

	if (s == NULL){
		return 0;
	}

    ary = env->NewByteArray(len);
    if (ary != 0) {
        jstring str = 0;
		env->SetByteArrayRegion(ary, 0, len, (jbyte *)s);
		if (!env->ExceptionOccurred()) {
                NULL_CHECK0(cls = env->FindClass("java/lang/String"));
                NULL_CHECK0(mid = env->GetMethodID(cls, "<init>", 
	   				  "([B)V"));
				str = (jstring)env->NewObject(cls, mid, ary);
			}
		env->DeleteLocalRef(ary);
		return str;
    }
	return 0;
}

jobjectArray
Salomon::NewPlatformStringArray(JNIEnv *env, char **strv, int strc)
{
    jclass cls;
	jobjectArray ary;
    int i;

    NULL_CHECK0(cls = env->FindClass("java/lang/String"));
    NULL_CHECK0(ary = env->NewObjectArray(strc, cls, 0));
    for (i = 0; i < strc; i++) {
		jstring str = NewPlatformString(env, *strv++);
		NULL_CHECK0(str);
		env->SetObjectArrayElement(ary, i, str);
		env->DeleteLocalRef(str);
    }
    return ary;
} 
