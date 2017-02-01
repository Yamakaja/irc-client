package me.yamakaja.irc.client;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class Launcher {

    public static void main(String[] args) {
        int port = 6667;
        switch (args.length) {
            case 0:
                System.out.println("Usage: java -jar client.jar [host] [<port, defaults to 6667>]");
                break;
            case 2:
                port = Integer.parseInt(args[1]);
            case 1:
                new CommandLineClient(args[0], port).launch();
        }
    }

}
