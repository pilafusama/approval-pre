package com.tenpay.wxwork.approval.presvr.sender.config;

import com.tenpay.bap.client.config.BapHttpConfig;
import com.tenpay.bap.client.config.BapRelayConfig;
import com.tenpay.bap.server.config.BapServerConfig;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Sean Lei on 25/11/2016.
 */
@Configuration
@PropertySources(value = {@PropertySource(value = "classpath:common.properties", encoding = "UTF-8")})
@Import(value = {BapServerConfig.class, BapHttpConfig.class, BapRelayConfig.class})
public class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        properties.setLocations(new ClassPathResource("common.properties"));
        return properties;
    }
}
