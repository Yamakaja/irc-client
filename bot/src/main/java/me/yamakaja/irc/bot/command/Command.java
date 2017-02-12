package me.yamakaja.irc.bot.command;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.config.CommandConfig;
import me.yamakaja.irc.bot.config.ConfigManager;
import me.yamakaja.irc.client.chat.ChatChannel;

/**
 * Created by Yamakaja on 11.02.17.
 */
public abstract class Command<T extends CommandConfig> {

    private String name;
    private String description;
    private String usage;
    private T config;

    @Inject
    private ConfigManager configManager;

    public Command(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    public void initialize() {
        this.config = (T) configManager.getConfig().getCommandConfigs().get(getName());
    }

    protected abstract boolean onCommand(ChatChannel originChannel, String sender, String[] args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public T getConfig() {
        return config;
    }
}
