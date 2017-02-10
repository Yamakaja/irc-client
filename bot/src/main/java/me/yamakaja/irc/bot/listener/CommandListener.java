package me.yamakaja.irc.bot.listener;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.client.network.event.user.UserMessageEvent;
import me.yamakaja.irc.client.util.NameUtils;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class CommandListener implements Listener {

    @Inject
    private IRCBot bot;

    @EventHandler
    public void onMessage(UserMessageEvent e) {
        if (bot.hasAdminPrivileges(e.getSender())) {
            String sender = NameUtils.getNick(e.getSender());
            String[] command = e.getMessage().split(" ");
            switch (command[0].toLowerCase()) {
                case "shutdown":
                case "exit":
                case "quit":
                    bot.getClient().shutdown();
                    break;
                case "help":
                    bot.getClient().message(sender, "QUIT/SHUTDOWN/EXIT - Exit the bot");
                    break;
                default:
                    bot.getClient().message(sender, "Unknown command! Try HELP for a list of commands!");
                    break;
            }
        }
    }

}
