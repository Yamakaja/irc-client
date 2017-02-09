package me.yamakaja.irc.client.network.event.server;

import me.yamakaja.irc.client.network.event.packet.PacketEvent;
import me.yamakaja.irc.client.network.packet.client.command.server.PacketClientLocalServerConnectionInfo;

/**
 * Called upon reception of a 255
 * <p>
 * Created by Yamakaja on 09.02.17.
 */
public class ServerUsersInfoEvent extends PacketEvent<PacketClientLocalServerConnectionInfo> {
}
