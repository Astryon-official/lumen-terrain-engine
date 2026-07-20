cmake_minimum_required(VERSION 3.20)

project(LumenTerrainEngine)

set(CMAKE_CXX_STANDARD 20)


find_package(Vulkan REQUIRED)


add_library(LumenTerrainEngine SHARED

    src/LTE.cpp
    src/Logger.cpp
    src/HardwareManager.cpp
    src/VulkanDetector.cpp
    src/BenchmarkManager.cpp
    src/VulkanBenchmark.cpp

)


target_include_directories(
    LumenTerrainEngine
    PUBLIC
    include
)


target_link_libraries(
    LumenTerrainEngine
    Vulkan::Vulkan
)

add_executable(LTE_Test
    src/test.cpp
)

target_link_libraries(
    LTE_Test
    LumenTerrainEngine
)
