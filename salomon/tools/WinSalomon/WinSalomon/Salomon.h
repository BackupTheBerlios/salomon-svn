#pragma once

#ifdef SALOMON_DLL
#define DLL_SHARE __declspec( dllimport )
#else
#define DLL_SHARE  __declspec( dllexport )
#endif


DLL_SHARE class Salomon
{

public:
	DLL_SHARE explicit Salomon();
	DLL_SHARE virtual ~Salomon(void);
	DLL_SHARE void __stdcall void runTask();

};


