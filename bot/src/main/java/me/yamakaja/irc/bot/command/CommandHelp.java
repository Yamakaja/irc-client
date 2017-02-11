package me.yamakaja.irc.bot.command;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.util.NameUtils;

/**
 * Created by Yamakaja on 11.02.17.
 */
public class CommandHelp extends Command {

    @Inject
    private IRCBot bot;

    public CommandHelp() {
        super("help", "Gives a list of commands", "");
    }

    @Override
    protected boolean onCommand(ChatChannel originChannel, String sender, String[] args) {
        sendMessage(sender, "List of available commands:");
        bot.getCommandManager().getCommandMap().values().forEach(cmd -> sendMessage(sender, String.format("(.)%1$s - %2$s", cmd.getName(), cmd.getDescription())));
        return false;
    }

    private void sendMessage(String target, String message) {
        bot.getClient().sendMessage(NameUtils.getNick(target), message);
    }

}
