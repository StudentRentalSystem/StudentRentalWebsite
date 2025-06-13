package xyz.jessyu.studentrentalwebsite.config;

import io.github.studentrentalsystem.LLMConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author JessYu-1011
 * To read the configurations in application.yml and create the LLMConfig based on it.
 * */
@Configuration
public class LLMConfigBean {
    @Value("${llm.server-address}")
    private String serverAddress;

    @Value("${llm.server-port}")
    private int serverPort;

    @Bean
    public LLMConfig llmConfig() throws IllegalArgumentException{
        return new LLMConfig(LLMConfig.LLMMode.CHAT, serverAddress, serverPort, "deepseek-r1:14b", false, null);
    }

}
