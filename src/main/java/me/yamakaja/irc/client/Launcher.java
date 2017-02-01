package me.yamakaja.irc.client;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class Launcher {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        CommandLineClient commandLineClient = injector.getInstance(CommandLineClient.class);
        commandLineClient.launch();
    }

}
