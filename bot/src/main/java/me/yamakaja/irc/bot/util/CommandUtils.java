package me.yamakaja.irc.bot.util;

import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.util.NameUtils;

/**
 * Created by Yamakaja on 12.02.17.
 */
public class CommandUtils {

    private CommandUtils() {
    }

    public static void sendCommandResponse(IRCBot bot, ChatChannel channel, String sender, String message) {
        if(channel != null) {
            channel.sendMessage("(" + NameUtils.getNick(sender) + ") " + message);
        } else {
            bot.getClient().sendMessage(sender, message);
        }
    }

}
