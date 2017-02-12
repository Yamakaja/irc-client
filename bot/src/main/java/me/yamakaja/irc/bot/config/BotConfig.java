package me.yamakaja.irc.bot.config;

import java.util.Map;

/**
 * Created by Yamakaja on 12.02.17.
 */
public class BotConfig {

    private Map<String, CommandConfig> commands;

    public Map<String, CommandConfig> getCommandConfigs() {
        return commands;
    }
}
