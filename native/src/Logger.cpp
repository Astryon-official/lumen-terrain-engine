#include "Logger.h"

#include <iostream>


namespace LTE
{

void Log(const char* message)
{
    std::cout
        << "[LTE] "
        << message
        << std::endl;
}

}