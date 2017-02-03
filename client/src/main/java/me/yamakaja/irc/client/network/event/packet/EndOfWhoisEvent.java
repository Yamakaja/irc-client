package me.yamakaja.irc.client.network.event.packet;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.command.whois.PacketClientWhoisEnd;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class EndOfWhoisEvent extends PacketEvent {

    private PacketClientWhoisEnd packet;

    @Override
    public void read(ClientboundPacket packet) {
        this.packet = (PacketClientWhoisEnd) packet;
    }

    @Override
    public ClientboundPacket getPacket() {
        return packet;
    }

    public String getNick() {
        return packet.getNick();
    }

}
