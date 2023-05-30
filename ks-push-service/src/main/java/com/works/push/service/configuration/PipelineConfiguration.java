package com.works.push.service.configuration;

import com.works.enums.ProcessTypeEnum;
import com.works.push.service.component.ParameterAssembler;
import com.works.push.service.component.PreParameterValidator;
import com.works.push.service.component.PushTaskExecutor;
import com.works.push.service.pipeline.ProcessChainTemplate;
import com.works.push.service.pipeline.ProcessController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DaLong
 * @date 2023/5/29 10:57
 */
@Slf4j
@Configuration
public class PipelineConfiguration {

    @Resource
    private ParameterAssembler parameterAssembler;
    @Resource
    private PreParameterValidator preParameterValidator;
    @Resource
    private PushTaskExecutor pushTaskExecutor;


    @Bean(name = "taskPushTemplate")
    public ProcessChainTemplate taskPushTemplate() {
        ProcessChainTemplate processChainTemplate = new ProcessChainTemplate();
        processChainTemplate.setProcessList(Arrays.asList(preParameterValidator, parameterAssembler, pushTaskExecutor));
        return processChainTemplate;
    }

    @Bean(name = "taskRecallTemplate")
    public ProcessChainTemplate taskRecallTemplate() {
        return new ProcessChainTemplate();
    }


    @Bean
    public ProcessController processController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessChainTemplate> templateMap = new HashMap<>(2);
        templateMap.put(ProcessTypeEnum.TASK_PUSH.getType(), taskPushTemplate());
        templateMap.put(ProcessTypeEnum.TASK_RECALL.getType(), taskRecallTemplate());
        processController.setChainTemplateMap(templateMap);
        return processController;
    }


}
