package me.yamakaja.irc.bot.command;

import com.google.inject.Inject;
import me.yamakaja.irc.bot.IRCBot;
import me.yamakaja.irc.bot.config.CommandConfig;
import me.yamakaja.irc.bot.util.CommandUtils;
import me.yamakaja.irc.client.chat.ChatChannel;
import me.yamakaja.irc.client.util.StringUtils;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Elements;
import nu.xom.ParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Yamakaja on 12.02.17.
 */
public class CommandWolframAlpha extends Command<CommandWolframAlpha.CommandWolframAlphaConfig> {

    @Inject
    private IRCBot bot;

    public CommandWolframAlpha() {
        super("wa", "Quickly look something up on wolframalpha.com", "<query>");
    }

    @Override
    protected boolean onCommand(ChatChannel originChannel, String sender, String[] args) {
        if (args.length == 0)
            return true;

        if (getConfig().appid.equals("XXXXX")) {
            CommandUtils.sendCommandResponse(bot, originChannel, sender, "The WolframAlpha Application ID hasn't been set yet!");
            return false;
        }

        try {
            URLConnection connection = new URL("https", "api.wolframalpha.com", 443,
                    "/v2/query?appid=" + URLEncoder.encode(getConfig().appid, "UTF-8") + "&input=" +
                            URLEncoder.encode(StringUtils.join(args, " ", 0, args.length - 1), "UTF-8")).openConnection();
            InputStream input = connection.getInputStream();

            Builder parser = new Builder();
            Document document = parser.build(input);
            input.close();

            if (!document.getRootElement().getAttribute("success").getValue().equals("true")) {
                CommandUtils.sendCommandResponse(bot, originChannel, sender, "Request failed!");
                return false;
            }

            Elements children = document.getRootElement().getChildElements("pod");

            for (int i = 0; i < children.size(); i++) {
                switch (children.get(i).getAttribute("title").getValue()) {
                    case "Decimal approximation":
                        CommandUtils.sendCommandResponse(bot, originChannel, sender, "Decimal approximation: " + children.get(i).getChild(1).getChild(3).getValue());
                        return false;
                    case "Result":
                    case "Results":
                    case "Definite integral":
                    case "Message digest":
                        CommandUtils.sendCommandResponse(bot, originChannel, sender, "Result: " + children.get(i).getChild(1).getChild(3).getValue());
                        return false;
                }
            }

        } catch (IOException | ParsingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static class CommandWolframAlphaConfig extends CommandConfig {

        private String appid;

    }

}
