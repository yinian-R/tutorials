package com.wymm.greeter.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "wymm.greeter")
public class GreeterProperties {
    private String userName;
    private String morningMessage;
    private String afternoonMessage;
    private String eveningMessage;
    private String nightMessage;
}
