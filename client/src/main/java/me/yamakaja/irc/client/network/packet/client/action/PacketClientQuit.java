package me.yamakaja.irc.client.network.packet.client.action;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class PacketClientQuit extends PacketAction {

    private String user;
    private String reason;

    @Override
    public void read(String data) {
        this.user = data.substring(1, data.indexOf(' '));
        this.reason = data.substring(1, data.indexOf(':', 1));
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.QUIT;
    }

    public String getUser() {
        return user;
    }

    public String getReason() {
        return reason;
    }

}
