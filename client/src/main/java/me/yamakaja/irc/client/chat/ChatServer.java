package me.yamakaja.irc.client.chat;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class ChatServer {

    private String host;
    private String motd;

    public ChatServer(String host, String motd) {
        this.host = host;
        this.motd = motd;
    }

    public String getHost() {
        return host;
    }

    public String getMotd() {
        return motd;
    }

    @Override
    public String toString() {
        return "ChatServer{" +
                "host='" + host + '\'' +
                ", motd='" + motd + '\'' +
                '}';
    }
}
