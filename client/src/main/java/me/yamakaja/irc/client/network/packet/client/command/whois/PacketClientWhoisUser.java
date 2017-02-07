package me.yamakaja.irc.client.network.packet.client.command.whois;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;
import me.yamakaja.irc.client.network.util.StringUtils;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class PacketClientWhoisUser extends PacketCommandResponse {

    private String nick, ident, hostname, realName;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        nick = split[3];
        ident = split[4];
        hostname = split[5];
        realName = StringUtils.join(split, " ", 7, split.length - 1).substring(1);
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_WHOISUSER;
    }

    public String getNick() {
        return nick;
    }

    public String getIdent() {
        return ident;
    }

    public String getHostname() {
        return hostname;
    }

    public String getRealName() {
        return realName;
    }
}
