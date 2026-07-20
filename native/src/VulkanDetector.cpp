#include "VulkanDetector.h"
#include "Logger.h"

#include <vulkan/vulkan.h>
#include <string>


namespace LTE
{


void DetectVulkanGPUs(std::vector<GPUInfo>& gpus)
{

    Log("Starting Vulkan GPU detection");


    VkInstance instance;


    VkApplicationInfo app{};

    app.sType =
        VK_STRUCTURE_TYPE_APPLICATION_INFO;

    app.pApplicationName =
        "Lumen Terrain Engine";

    app.applicationVersion =
        VK_MAKE_VERSION(0,1,0);

    app.engineVersion =
        VK_MAKE_VERSION(0,1,0);

    app.apiVersion =
        VK_API_VERSION_1_3;



    VkInstanceCreateInfo create{};

    create.sType =
        VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;

    create.pApplicationInfo =
        &app;



    if(vkCreateInstance(
        &create,
        nullptr,
        &instance) != VK_SUCCESS)
    {
        Log("Vulkan initialization failed");
        return;
    }



    uint32_t count = 0;


    vkEnumeratePhysicalDevices(
        instance,
        &count,
        nullptr);



    std::vector<VkPhysicalDevice> devices(count);


    vkEnumeratePhysicalDevices(
        instance,
        &count,
        devices.data());



    for(auto device : devices)
    {

        VkPhysicalDeviceProperties props{};


        vkGetPhysicalDeviceProperties(
            device,
            &props);



        GPUInfo gpu;


        gpu.name =
            props.deviceName;


        gpu.vulkanSupported =
            true;



        if(gpu.name.find("llvmpipe") != std::string::npos)
        {
            Log("Ignoring software Vulkan device");
            continue;
        }



        gpus.push_back(gpu);


        Log(props.deviceName);

    }



    vkDestroyInstance(
        instance,
        nullptr);



    Log("Vulkan GPU detection complete");

}


}
