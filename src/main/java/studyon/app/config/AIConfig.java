package studyon.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AIConfig {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.chat.options.model}")
    private String chatModel;

    @Bean
    public OpenAiChatModel openAiChatModel() {

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel(chatModel)
                .build();

        return new OpenAiChatModel(new OpenAiApi(apiKey), options);
    }
}


