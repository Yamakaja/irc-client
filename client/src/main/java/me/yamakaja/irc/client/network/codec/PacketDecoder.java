package me.yamakaja.irc.client.network.codec;

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

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        String message = byteBuf.readBytes(byteBuf.readableBytes()).toString(UTF_8);

        System.out.println(" <-- " + message);

        ClientboundPacketType type = message.startsWith(":") ? ClientboundPacketType.MESSAGE : ClientboundPacketType.valueOf(message.split(" ")[0]);
        ClientboundPacket packet = type.getPacketClass().newInstance();
        packet.read(message);
        list.add(packet);
    }

}
