package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 07.02.17.
 */
public class PacketClientServerInfo extends PacketCommandResponse {

    private String name;
    private String version;
    private String userModes;
    private String channelModes;
    private String extraModes;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        name = split[3];
        version = split[4];
        userModes = split[5];
        channelModes = split[6];
        extraModes = split[7];
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_MYINFO;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getUserModes() {
        return userModes;
    }

    public String getChannelModes() {
        return channelModes;
    }

    public String getExtraModes() {
        return extraModes;
    }

}
