package me.yamakaja.irc.client.network.packet.client.command.server;

import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;
import me.yamakaja.irc.client.network.packet.client.command.PacketCommandResponse;

/**
 * Created by Yamakaja on 09.02.17.
 */
public class PacketClientUsers extends PacketCommandResponse {

    private int users;
    private int others;
    private int servers;
    private String othersType;

    @Override
    public void read(String data) {
        String[] split = data.split(" ");
        this.users = Integer.parseInt(split[5]);
        this.others = Integer.parseInt(split[8]);
        this.servers = Integer.parseInt(split[11]);
        this.othersType = split[9];
    }

    @Override
    public ClientboundPacketType getPacketType() {
        return ClientboundPacketType.RPL_LUSERCLIENT;
    }

    /**
     * @return The amount of visible users currently connected
     */
    public int getUsers() {
        return users;
    }

    /**
     * @return The amount of "others"
     */
    public int getOthers() {
        return others;
    }

    /**
     * @return The amount of servers connected to the network
     */
    public int getServers() {
        return servers;
    }

    /**
     * @return The type of users returned by getUsers. This will be "invisible" for InspIRCd while it would be "services" for daemons following the spec
     */
    public String getOthersType() {
        return othersType;
    }

}
