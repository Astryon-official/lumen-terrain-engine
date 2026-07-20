#include "LTE.h"


namespace LTE
{

static bool initialized = false;


bool Initialize()
{
    initialized = true;

    return true;
}


void Shutdown()
{
    initialized = false;
}


bool IsInitialized()
{
    return initialized;
}


const char* GetVersion()
{
    return "0.1.0";
}

}