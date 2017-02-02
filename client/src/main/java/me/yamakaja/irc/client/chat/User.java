package me.yamakaja.irc.client.chat;

/**
 * Created by Yamakaja on 02.02.17.
 */
public class User {

    private String nick;
    private String ident;
    private String hostname;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
