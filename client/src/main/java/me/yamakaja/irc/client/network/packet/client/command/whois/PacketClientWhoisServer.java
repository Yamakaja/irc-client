package me.yamakaja.irc.client.network.packet.client.command.whois;

import me.yamakaja.irc.client.chat.ChatServer;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;
import me.yamakaja.irc.client.network.util.StringUtils;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class PacketClientWhoisServer extends PacketCommandResponse {

    private String nick;
    private ChatServer server;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        nick = split[3];
        server = new ChatServer(split[4], StringUtils.join(split, " ", 5, split.length - 1).substring(1));
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_WHOISSERVER;
    }

    public ChatServer getServer() {
        return server;
    }

    public String getNick() {
        return nick;
    }
}
