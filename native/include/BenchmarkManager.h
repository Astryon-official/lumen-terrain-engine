#pragma once

#include "HardwareManager.h"

#include <string>
#include <vector>


namespace LTE
{

struct BenchmarkResult
{
    std::string deviceName;
    std::string type;

    double score;
    double time;
};


class BenchmarkManager
{

public:

    void Run(HardwareManager& hardware);

    const std::vector<BenchmarkResult>& GetResults() const;


private:

    std::vector<BenchmarkResult> results;

};


}
