#define SALOMON_DLL

#include <iostream>

#include "salomon.h"

#define NULL_CHECK0(e) if ((e) == 0) return 0 

void Salomon::runTask()
{
	std::cout << "Run task method" << std::endl; 
}


Salomon::Salomon()
{
	JavaVMOption options[1];
	JNIEnv *env;
	JavaVM *jvm;
	JavaVMInitArgs vm_args;
	long status;
	jclass cls;
	jmethodID mid;
	jint square;
	jboolean not;

	options[0].optionString = "-Djava.class.path=.;Sample.jar;Notepad.jar";
	memset(&vm_args, 0, sizeof(vm_args));
	vm_args.version = JNI_VERSION_1_2;
	vm_args.nOptions = 1;
	vm_args.options = options;
	status = JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);

	if (status != JNI_ERR)
	{
		//cls = env->FindClass("sample/Sample2");
		cls = env->FindClass("Notepad");
		if(cls !=0)
		{   
			char * args[1];			
			args[0] = "arg";			
			jobjectArray mainArgs = NewPlatformStringArray(env, args, 1); 
			mid = env->GetStaticMethodID(cls, "main",
				       "([Ljava/lang/String;)V"); 
			if(mid !=0)
			{	
				env->CallStaticVoidMethod(cls, mid, mainArgs);				
			}
			
			/*
			mid = env->GetStaticMethodID(cls, "intMethod", "(I)I");
			if(mid !=0)
			{	
				square = env->CallStaticIntMethod(cls, mid, 5);
				printf("Result of intMethod: %d\n", square);
			}
			/*
			mid = env->GetStaticMethodID(cls, "booleanMethod", "(Z)Z");
			if(mid !=0)
			{					
				not = env->CallStaticBooleanMethod(cls, mid, 1);
				printf("Result of booleanMethod: %d\n", not);
			}
			*/
		} else {
			std::cout << "Class not found " << std::cout;
		}

		jvm->DestroyJavaVM();	
	}
	else {
		std::cout << "JVM error" << std::endl;			
	}
}

Salomon::~Salomon(void)
{
	std::cout << "Destruktor" << std::endl;
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
