#include "stringhelper.h"

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

#define NULL_CHECK0(e) if ((e) == 0) return 0 

jstring	StringHelper::getString(JNIEnv *env, char *s)
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

jobjectArray StringHelper::getStringArray(JNIEnv *env, char **strv, int strc)
{
    jclass cls;
	jobjectArray ary;
    int i;

    NULL_CHECK0(cls = env->FindClass("java/lang/String"));
    NULL_CHECK0(ary = env->NewObjectArray(strc, cls, 0));
    for (i = 0; i < strc; i++) {
		jstring str = getString(env, *strv++);
		NULL_CHECK0(str);
		env->SetObjectArrayElement(ary, i, str);
		env->DeleteLocalRef(str);
    }
    return ary;
}