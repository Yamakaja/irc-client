package me.yamakaja.irc.bot.command;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.util.ChatUtils;

/**
 * Created by Yamakaja on 12.02.17.
 */
public class CommandKill extends Command {

    public CommandKill() {
        super("kill", "Kill somebody", "<target>");
    }

    @Inject
    private IRCBot bot;

    @Override
    protected boolean onCommand(ChatChannel originChannel, String sender, String[] args) {
        if(args.length == 0)
            return true;

        if(originChannel != null)
            originChannel.sendMessage(ChatUtils.getAction("throws " + args[0] + " into a corium puddle"));
        else {
            if(args[0].equalsIgnoreCase("yourself")) {

                if(bot.hasAdminPrivileges(sender)) {
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

}
