package rip.hippo.parcel.packet.channel;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import rip.hippo.parcel.Parcel;
import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.generate.PacketFactory;
import rip.hippo.parcel.packet.impl.in.PacketPlayInChat;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public final class ParcelChannelDuplexHandler extends ChannelDuplexHandler {

    private final Parcel parcel;

    public ParcelChannelDuplexHandler(Parcel parcel) {
        this.parcel = parcel;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
