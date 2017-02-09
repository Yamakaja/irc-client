package me.yamakaja.irc.client.network.packet.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacket;
import me.yamakaja.irc.client.network.packet.client.ClientboundPacketType;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PacketDecoder extends ByteToMessageDecoder {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static ClientboundPacketType parseMessagePacket(String[] message) {
        try {
            return ClientboundPacketType.getForId(Integer.parseInt(message[1]));
        } catch (IllegalArgumentException ignore) {
            try {
                return ClientboundPacketType.valueOf(message[1]);
            } catch (IllegalArgumentException e) {
                return ClientboundPacketType.SERVERMESSAGE;
            }
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        String message = byteBuf.readBytes(byteBuf.readableBytes()).toString(UTF_8);
        String[] splitMessage = message.split(" ");

        ClientboundPacketType type = message.startsWith(":") ? parseMessagePacket(splitMessage) : ClientboundPacketType.valueOf(splitMessage[0]);
        ClientboundPacket packet = type.getPacketClass().newInstance();
        packet.read(message);
        list.add(packet);
    }

}
