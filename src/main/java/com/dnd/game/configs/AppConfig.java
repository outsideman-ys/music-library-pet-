package com.dnd.game.configs;

import com.dnd.game.entities.UserAccount;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("app.config")
public record AppConfig(String header, String intro, List<UserAccount> users) {



}
