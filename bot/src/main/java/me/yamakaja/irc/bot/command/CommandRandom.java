package me.yamakaja.irc.bot.command;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.bot.util.CommandUtils;
import me.yamakaja.irc.client.chat.ChatChannel;

/**
 * Created by Yamakaja on 11.02.17.
 */
public class CommandRandom extends Command {

    @Inject
    private IRCBot bot;

    public CommandRandom() {
        super("random", "Get a random number", "<min> <max>");
    }

    @Override
    protected boolean onCommand(ChatChannel origin, String sender, String[] args) {
        if (args.length != 2)
            return true;

        try {
            int min = Integer.parseInt(args[0]);
            int max = Integer.parseInt(args[1]) + 1; //For an inclusive max

            if (max <= min)
                return true;

            String result = "Result: " + ((int) ((Math.random() * (max - min)) + min));

            CommandUtils.sendCommandResponse(bot, origin, sender, result);

        } catch (IllegalArgumentException e) {
            return true;
        }

        return false;
    }

}
