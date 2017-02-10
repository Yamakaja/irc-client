package me.yamakaja.irc.bot;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by Yamakaja on 10.02.17.
 */
public class Launcher {

    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Usage: java -jar Bot.jar [host] [port] [nick] [nickServPassword] <Channels ...>");
            return;
        }

        Injector injector = Guice.createInjector();
        IRCBot bot = injector.getInstance(IRCBot.class);

        bot.setRemote(args[0], Integer.parseInt(args[1]));
        bot.setNick(args[2], args[3]);

        bot.launch();

        for (int i = 4; i < args.length; i++) {
            bot.addChannel(args[i]);
        }

        bot.getCloseFuture().syncUninterruptibly();
        System.out.println("Shutting down!");
    }

}
