#pragma once


namespace LTE
{

bool Initialize();

void Shutdown();

bool IsInitialized();

const char* GetVersion();

}