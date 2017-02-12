package me.yamakaja.irc.bot.command;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.bot.config.CommandConfig;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.util.ChatUtils;

import java.util.Map;

/**
 * Created by Yamakaja on 12.02.17.
 */
public class CommandKill extends Command<CommandKill.CommandKillConfig> {

    @Inject
    private IRCBot bot;

    public CommandKill() {
        super("kill", "Kill somebody", "<target>");
    }

    @Override
    protected boolean onCommand(ChatChannel originChannel, String sender, String[] args) {
        if (args.length == 0)
            return true;

        if (originChannel != null)
            originChannel.sendMessage(ChatUtils.getAction(getKillMessage(args[0])));
        else {
            if (args[0].equalsIgnoreCase("yourself")) {

                if (bot.hasAdminPrivileges(sender)) {
                    bot.getClient().sendMessage(sender, "Bye D:");
                    bot.getClient().shutdown();
                } else {
                    bot.getClient().sendMessage(sender, "How dare you?");
                }

            } else {
                bot.getClient().sendMessage(sender, "What's the point of killing somebody in here?");
            }
        }

        return false;
    }

    private String getKillMessage(String target) {
        String message = getConfig().messages[(int) (Math.random() * getConfig().messages.length)];

        message = message.replace("{user}", target);

        for (Map.Entry<String, String[]> entry : getConfig().parts.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue()[(int) (entry.getValue().length * Math.random())]);
        }
        return message;
    }

    public static class CommandKillConfig extends CommandConfig {

        private String[] messages;

        private Map<String, String[]> parts;

    }

}
