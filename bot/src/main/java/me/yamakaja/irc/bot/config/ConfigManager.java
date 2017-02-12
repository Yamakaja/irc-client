package me.yamakaja.irc.bot.config;

import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yamakaja.irc.bot.IRCBot;

import java.io.*;

/**
 * Created by Yamakaja on 12.02.17.
 */
@Singleton
public class ConfigManager {

    @Inject
    private IRCBot bot;

    private BotConfig config;

    private GsonBuilder gsonBuilder;

    public ConfigManager() {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CommandConfig.class, new CommandConfigDeserializer());
    }

    public void initialize() {
        File file = new File("config.json");
        if (!file.exists()) {
            FileOutputStream writer = null;
            InputStream reader = null;
            try {
                writer = new FileOutputStream(file);
                reader = ConfigManager.class.getResource("/config.json").openStream();

                byte[] buffer = new byte[1024];
                int count;

                while((count = reader.read(buffer)) > 0) {
                    writer.write(buffer, 0, count);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("An error occurred while closing an existing writer in the finally block!");
                    e.printStackTrace();
                }
                if (reader != null) try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("An error occurred while closing an existing reader in the finally block!");
                    e.printStackTrace();
                }
            }
        }

        FileReader reader = null;
        try {
            reader = new FileReader(file);
            config = gsonBuilder.create().fromJson(reader, BotConfig.class);
        } catch (FileNotFoundException e) {
            System.err.println("Config doesn't exist although it should?!");
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("An error occurred while closing an existing reader in the finally block!");
                    e.printStackTrace();
                }
            }
        }
    }

    public BotConfig getConfig() {
        return config;
    }
}
