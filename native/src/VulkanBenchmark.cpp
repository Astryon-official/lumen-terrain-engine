#include "VulkanBenchmark.h"
#include "Logger.h"

#include <vulkan/vulkan.h>

#include <vector>
#include <fstream>
#include <string>
#include <chrono>


namespace LTE
{


static std::vector<char> ReadFile(const char* filename)
{
    std::ifstream file(
        filename,
        std::ios::ate |
        std::ios::binary
    );


    if(!file)
        return {};


    size_t size = file.tellg();

    std::vector<char> buffer(size);

    file.seekg(0);

    file.read(
        buffer.data(),
        size
    );


    return buffer;
}



static int FindMemoryType(
    VkPhysicalDevice gpu,
    uint32_t typeFilter,
    VkMemoryPropertyFlags properties
)
{
    VkPhysicalDeviceMemoryProperties mem{};


    vkGetPhysicalDeviceMemoryProperties(
        gpu,
        &mem
    );


    for(uint32_t i = 0; i < mem.memoryTypeCount; i++)
    {
        if(
            (typeFilter & (1 << i)) &&
            (mem.memoryTypes[i].propertyFlags & properties)
            == properties
        )
        {
            return i;
        }
    }


    return -1;
}



double VulkanBenchmark::Run(
    const std::string& gpuName
)
{
    Log("Starting Vulkan GPU benchmark");


    VkInstance instance{};


    VkApplicationInfo app{};

    app.sType =
        VK_STRUCTURE_TYPE_APPLICATION_INFO;

    app.apiVersion =
        VK_API_VERSION_1_2;



    VkInstanceCreateInfo ci{};

    ci.sType =
        VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;

    ci.pApplicationInfo =
        &app;



    if(vkCreateInstance(
        &ci,
        nullptr,
        &instance) != VK_SUCCESS)
    {
        return 0;
    }



    uint32_t gpuCount = 0;


    vkEnumeratePhysicalDevices(
        instance,
        &gpuCount,
        nullptr
    );


    std::vector<VkPhysicalDevice> gpus(gpuCount);


    vkEnumeratePhysicalDevices(
        instance,
        &gpuCount,
        gpus.data()
    );



    VkPhysicalDevice gpu =
        VK_NULL_HANDLE;



    for(auto device : gpus)
    {
        VkPhysicalDeviceProperties props{};


        vkGetPhysicalDeviceProperties(
            device,
            &props
        );


        if(std::string(props.deviceName) == gpuName)
        {
            gpu = device;
            break;
        }
    }



    if(!gpu)
    {
        Log("GPU not found");
        return 0;
    }



    uint32_t queueCount = 0;


    vkGetPhysicalDeviceQueueFamilyProperties(
        gpu,
        &queueCount,
        nullptr
    );


    std::vector<VkQueueFamilyProperties> queues(queueCount);


    vkGetPhysicalDeviceQueueFamilyProperties(
        gpu,
        &queueCount,
        queues.data()
    );



    uint32_t computeQueue = UINT32_MAX;


    for(uint32_t i = 0; i < queueCount; i++)
    {
        if(queues[i].queueFlags & VK_QUEUE_COMPUTE_BIT)
        {
            computeQueue = i;
            break;
        }
    }



    float priority = 1.0f;


    VkDeviceQueueCreateInfo q{};

    q.sType =
        VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO;

    q.queueFamilyIndex =
        computeQueue;

    q.queueCount = 1;

    q.pQueuePriorities =
        &priority;



    VkDeviceCreateInfo dc{};

    dc.sType =
        VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO;

    dc.queueCreateInfoCount = 1;

    dc.pQueueCreateInfos =
        &q;



    VkDevice device{};


    if(vkCreateDevice(
        gpu,
        &dc,
        nullptr,
        &device) != VK_SUCCESS)
    {
        return 0;
    }



    VkQueue queue{};


    vkGetDeviceQueue(
        device,
        computeQueue,
        0,
        &queue
    );


    Log("Compute queue ready");



    const uint32_t count = 1024 * 1024;



    VkBufferCreateInfo bc{};

    bc.sType =
        VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO;

    bc.size =
        count * sizeof(float);

    bc.usage =
        VK_BUFFER_USAGE_STORAGE_BUFFER_BIT;



    VkBuffer buffer{};


    vkCreateBuffer(
        device,
        &bc,
        nullptr,
        &buffer
    );



    VkMemoryRequirements req{};


    vkGetBufferMemoryRequirements(
        device,
        buffer,
        &req
    );



    VkMemoryAllocateInfo ai{};

    ai.sType =
        VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO;

    ai.allocationSize =
        req.size;

    ai.memoryTypeIndex =
        FindMemoryType(
            gpu,
            req.memoryTypeBits,
            VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT |
            VK_MEMORY_PROPERTY_HOST_COHERENT_BIT
        );



    VkDeviceMemory memory{};


    vkAllocateMemory(
        device,
        &ai,
        nullptr,
        &memory
    );


    vkBindBufferMemory(
        device,
        buffer,
        memory,
        0
    );



    float* data;


    vkMapMemory(
        device,
        memory,
        0,
        VK_WHOLE_SIZE,
        0,
        (void**)&data
    );


    for(uint32_t i = 0; i < count; i++)
        data[i] = 1.0f;


    vkUnmapMemory(
        device,
        memory
    );


    Log("Benchmark buffer created");



    auto shaderCode =
        ReadFile("../shaders/shader.spv");



    VkShaderModuleCreateInfo sm{};

    sm.sType =
        VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO;

    sm.codeSize =
        shaderCode.size();

    sm.pCode =
        reinterpret_cast<const uint32_t*>(
            shaderCode.data()
        );



    VkShaderModule shader{};


    vkCreateShaderModule(
        device,
        &sm,
        nullptr,
        &shader
    );


    Log("Shader loaded");



    // Pipeline

    VkPipelineShaderStageCreateInfo stage{};

    stage.sType =
        VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO;

    stage.stage =
        VK_SHADER_STAGE_COMPUTE_BIT;

    stage.module =
        shader;

    stage.pName =
        "main";



VkDescriptorSetLayoutBinding storageBinding{};

storageBinding.binding = 0;
storageBinding.descriptorType =
    VK_DESCRIPTOR_TYPE_STORAGE_BUFFER;
storageBinding.descriptorCount = 1;
storageBinding.stageFlags =
    VK_SHADER_STAGE_COMPUTE_BIT;


VkDescriptorSetLayoutCreateInfo dl{};

dl.sType =
    VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO;

dl.bindingCount = 1;
dl.pBindings = &storageBinding;


VkDescriptorSetLayout descriptorLayout;


if(vkCreateDescriptorSetLayout(
    device,
    &dl,
    nullptr,
    &descriptorLayout
) != VK_SUCCESS)
{
    Log("Descriptor layout failed");
    return 0;
}



VkPipelineLayout layout{};


VkPipelineLayoutCreateInfo lc{};

lc.sType =
    VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO;

lc.setLayoutCount = 1;

lc.pSetLayouts =
    &descriptorLayout;



if(vkCreatePipelineLayout(
    device,
    &lc,
    nullptr,
    &layout
) != VK_SUCCESS)
{
    Log("Pipeline layout failed");
    return 0;
}

VkComputePipelineCreateInfo pc{};

pc.sType =
    VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO;

pc.stage =
    stage;

pc.layout =
    layout;

    VkPipeline pipeline{};

	Log("Creating compute pipeline");


	if(vkCreateComputePipelines(
	        device,
	        VK_NULL_HANDLE,
	        1,
	        &pc,
	        nullptr,
	        &pipeline
	    ) != VK_SUCCESS)
	{
	    Log("Compute pipeline creation failed");
	    return 0;
	}

    Log("Compute pipeline created");

    VkDescriptorPoolSize poolSize{};

    poolSize.type =
        VK_DESCRIPTOR_TYPE_STORAGE_BUFFER;

    poolSize.descriptorCount = 1;



    VkDescriptorPoolCreateInfo dp{};

    dp.sType =
        VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO;

    dp.poolSizeCount = 1;

    dp.pPoolSizes =
        &poolSize;

    dp.maxSets = 1;



    VkDescriptorPool descriptorPool{};


    if(vkCreateDescriptorPool(
        device,
        &dp,
        nullptr,
        &descriptorPool
    ) != VK_SUCCESS)
    {
        Log("Descriptor pool failed");
        return 0;
    }



    VkDescriptorSetAllocateInfo da{};

    da.sType =
        VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO;

    da.descriptorPool =
        descriptorPool;

    da.descriptorSetCount = 1;

    da.pSetLayouts =
        &descriptorLayout;



    VkDescriptorSet descriptorSet{};


    if(vkAllocateDescriptorSets(
        device,
        &da,
        &descriptorSet
    ) != VK_SUCCESS)
    {
        Log("Descriptor allocation failed");
        return 0;
    }



    VkDescriptorBufferInfo bufferInfo{};

    bufferInfo.buffer =
        buffer;

    bufferInfo.offset = 0;

    bufferInfo.range =
        VK_WHOLE_SIZE;



    VkWriteDescriptorSet write{};

    write.sType =
        VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET;

    write.dstSet =
        descriptorSet;

    write.dstBinding = 0;

    write.descriptorCount = 1;

    write.descriptorType =
        VK_DESCRIPTOR_TYPE_STORAGE_BUFFER;

    write.pBufferInfo =
        &bufferInfo;



    vkUpdateDescriptorSets(
        device,
        1,
        &write,
        0,
        nullptr
    );


    Log("Descriptor set ready");


    VkCommandPool pool{};


    VkCommandPoolCreateInfo cp{};

    cp.sType =
        VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO;

    cp.queueFamilyIndex =
        computeQueue;

    cp.flags =
        VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT;

    vkCreateCommandPool(
        device,
        &cp,
        nullptr,
        &pool
    );



    VkCommandBuffer cmd{};


    VkCommandBufferAllocateInfo ca{};

    ca.sType =
        VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO;

    ca.commandPool =
        pool;

    ca.level =
        VK_COMMAND_BUFFER_LEVEL_PRIMARY;

    ca.commandBufferCount = 1;



    vkAllocateCommandBuffers(
        device,
        &ca,
        &cmd
    );



    VkCommandBufferBeginInfo bi{};

    bi.sType =
        VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO;



	if(vkBeginCommandBuffer(
	    cmd,
	    &bi
	) != VK_SUCCESS)
	{
	    Log("Command buffer begin failed");
	    return 0;
	}


    vkCmdBindPipeline(
        cmd,
        VK_PIPELINE_BIND_POINT_COMPUTE,
        pipeline
    );

    vkCmdBindDescriptorSets(
        cmd,
        VK_PIPELINE_BIND_POINT_COMPUTE,
        layout,
        0,
        1,
        &descriptorSet,
        0,
        nullptr
    );

    auto start =
        std::chrono::high_resolution_clock::now();



    vkCmdDispatch(
        cmd,
        4096,
        1,
        1
    );



	if(vkEndCommandBuffer(cmd) != VK_SUCCESS)
	{
	    Log("Command buffer end failed");
	    return 0;
	}


    VkSubmitInfo submit{};


    submit.sType =
        VK_STRUCTURE_TYPE_SUBMIT_INFO;

    submit.commandBufferCount = 1;

    submit.pCommandBuffers =
        &cmd;


VkResult submitResult = vkQueueSubmit(
    queue,
    1,
    &submit,
    VK_NULL_HANDLE
);

Log(("Queue submit result: " + std::to_string(submitResult)).c_str());

if(submitResult != VK_SUCCESS)
{
    Log("Queue submit failed");
    return 0;
}

if(vkQueueWaitIdle(queue) != VK_SUCCESS)
{
    Log("Queue wait failed");
    return 0;
}

    Log("GPU benchmark complete");

    auto end =
        std::chrono::high_resolution_clock::now();



    double time =
        std::chrono::duration<double>(
            end - start
        ).count();



    double score =
        100000000.0 / time;


vkDeviceWaitIdle(device);

// Command buffer cleanup
/*
vkFreeCommandBuffers(
    device,
    pool,
    1,
    &cmd
);
*/

vkDestroyCommandPool(
    device,
    pool,
    nullptr
);

// Descriptor cleanup
vkDestroyDescriptorPool(
    device,
    descriptorPool,
    nullptr
);
vkDestroyPipeline(
    device,
    pipeline,
    nullptr
);

vkDestroyPipelineLayout(
    device,
    layout,
    nullptr
);

vkDestroyDescriptorSetLayout(
    device,
    descriptorLayout,
    nullptr
);

vkDestroyShaderModule(
    device,
    shader,
    nullptr
);

vkDestroyBuffer(
    device,
    buffer,
    nullptr
);

vkFreeMemory(
    device,
    memory,
    nullptr
);

vkDestroyDevice(
    device,
    nullptr
);

vkDestroyInstance(
    instance,
    nullptr
);

return score;
}

}

