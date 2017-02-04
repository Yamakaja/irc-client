package me.yamakaja.irc.client.network.packet.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.yamakaja.irc.client.network.packet.server.ServerboundPacket;

/**
 * Created by Yamakaja on 01.02.17.
 */
public class PacketEncoder extends MessageToByteEncoder<ServerboundPacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ServerboundPacket packet, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(packet.getEncoded().getBytes("UTF-8"));
        System.err.println(" --> " + packet.getEncoded());
    }

}
