package xyz.jessyu.studentrentalwebsite.service;

import io.github.querygenerator.MiniRagApp;
import io.github.studentrentalsystem.LLMConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JessYu-1011
 * Create MiniRagApp instance based on the LLMConfig
 * */
@Getter
@Service
public class MiniRagAppService {

    private final MiniRagApp miniRagApp;

    @Autowired
    public MiniRagAppService(LLMConfig llmConfig) {
        this.miniRagApp = new MiniRagApp(llmConfig);
    }

}