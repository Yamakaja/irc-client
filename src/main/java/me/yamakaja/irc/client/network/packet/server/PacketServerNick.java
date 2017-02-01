package me.yamakaja.irc.client.network.packet.server;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PacketServerNick extends ServerboundPacket {

    private String nick;

    public PacketServerNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String getEncoded() {
        return "NICK " + nick + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacketServerNick that = (PacketServerNick) o;

        return nick != null ? nick.equals(that.nick) : that.nick == null;
    }

    @Override
    public int hashCode() {
        return nick != null ? nick.hashCode() : 0;
    }
}
