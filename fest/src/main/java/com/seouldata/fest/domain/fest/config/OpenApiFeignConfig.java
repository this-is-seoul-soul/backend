package com.seouldata.fest.domain.fest.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class OpenApiFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
