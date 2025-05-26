package com.ssafy.live.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom.cookie")
public class CookieProperties {
    private boolean secure;
    private String sameSite;
}
