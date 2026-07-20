#pragma once

#include <string>
#include <vector>


namespace LTE
{

struct CPUInfo
{
    std::string name;
    unsigned int cores;
};


struct GPUInfo
{
    std::string name;
    bool vulkanSupported;
};


class HardwareManager
{

public:

    bool Initialize();

    const CPUInfo& GetCPU() const;

    const std::vector<GPUInfo>& GetGPUs() const;


private:

    CPUInfo cpu;
    std::vector<GPUInfo> gpus;

};

}
