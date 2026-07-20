#include "HardwareManager.h"
#include "Logger.h"
#include "BenchmarkManager.h"

#include <iostream>


int main()
{
    LTE::HardwareManager hardware;


    if(!hardware.Initialize())
    {
        LTE::Log("Hardware initialization failed");
        return 1;
    }


    auto cpu = hardware.GetCPU();

    std::cout
        << "CPU: "
        << cpu.name
        << "\nCores: "
        << cpu.cores
        << std::endl;


    auto gpus = hardware.GetGPUs();


    for(auto& gpu : gpus)
    {
        std::cout
            << "GPU: "
            << gpu.name
            << "\nVulkan: "
            << gpu.vulkanSupported
            << std::endl;
    }

	LTE::BenchmarkManager benchmark;

	benchmark.Run(hardware);

    return 0;
}
