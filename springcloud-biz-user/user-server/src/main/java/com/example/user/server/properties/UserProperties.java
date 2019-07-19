package com.example.user.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user")
@RefreshScope
@Data
public class UserProperties {
    private String tokenKey;
    private Integer expire;
}
