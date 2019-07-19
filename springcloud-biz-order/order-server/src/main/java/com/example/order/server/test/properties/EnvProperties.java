package com.example.order.server.test.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "env")
@RefreshScope
@Data
public class EnvProperties {
    private String profile;
    private String label;
    private String version;
}
