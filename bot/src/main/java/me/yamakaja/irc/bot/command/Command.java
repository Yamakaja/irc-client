package me.yamakaja.irc.bot.command;

import me.yamakaja.irc.client.chat.ChatChannel;

/**
 * Created by Yamakaja on 11.02.17.
 */
public abstract class Command {

    private String name;
    private String description;
    private String usage;

    public Command(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    public void init(){}

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

}
