package me.yamakaja.irc.bot.command;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.network.event.channel.ChannelMessageEvent;
import me.yamakaja.irc.client.network.event.user.UserMessageEvent;
import me.yamakaja.irc.client.util.NameUtils;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yamakaja on 11.02.17.
 */
public class CommandManager implements Listener {

    @Inject
    private IRCBot bot;

    private Map<String, Command> commandMap = new HashMap<>();

    @EventHandler
    public void onUserMessage(UserMessageEvent e) {
        processCommand(e.getSender(), null, e.getMessage().trim());
    }

    @EventHandler
    public void onChannelMessage(ChannelMessageEvent e) {
        if (e.getMessage().startsWith("."))
            processCommand(e.getSender(), e.getChannel(), e.getMessage().trim());
    }

    private void processCommand(String sender, ChatChannel originChannel, String message) {
        if (message.isEmpty())
            return;

        String[] split = message.split(" ");

        if (split.length < 1)
            return;

        String commandName = split[0].startsWith(".") ? split[0].substring(1) : split[0];

        Command command = commandMap.get(commandName);
        if (command == null) {
            sendMessage(sender, "Unknown command! Try " + (split[0].charAt(0) == '.' ? "." : "") + "HELP for a list of commands!");
            return;
        }

        String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, args.length);

        if (command.onCommand(originChannel, sender, args)) {
            sendMessage(sender, "Invalid syntax! Usage: " + split[0] + " " + command.getUsage());
        }

    }

    private void sendMessage(String target, String message) {
        bot.getClient().sendMessage(NameUtils.getNick(target), message);
    }

    public void registerCommand(Command command) {
        command.initialize();
        commandMap.put(command.getName(), command);
    }

    Map<String, Command> getCommandMap() {
        return commandMap;
    }
}
