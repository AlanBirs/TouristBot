package org.example.config;

import org.example.EvilTouristBot;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Configuration
public class AppConfig {
    //private EvilTouristBotConfig botConfig;

    public AppConfig() {
        //
    }

    /*@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }*/

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public EvilTouristBot EvilTouristBot() {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        EvilTouristBot evilTouristBot = new EvilTouristBot(options);

        return evilTouristBot;
    }
}
