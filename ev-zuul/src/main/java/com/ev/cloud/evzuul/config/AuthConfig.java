package com.ev.cloud.evzuul.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @create 2019-05-29 下午9:16
 **/
@Component
@ConfigurationProperties(prefix = "auth")
@Data
public class AuthConfig {
    private List<String> annoymosUrls;
}
