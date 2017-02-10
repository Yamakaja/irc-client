package me.yamakaja.irc.client.network.packet.server;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class PacketServerWhois extends ServerboundPacket {

    private String nick;

    public PacketServerWhois(String nick) {
        this.nick = nick;
    }

    @Override
    public String getEncoded() {
        return "WHOIS " + nick;
    }

}
