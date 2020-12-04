package rip.hippo.parcel.packet.channel;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;
import rip.hippo.parcel.Parcel;
import rip.hippo.parcel.event.impl.InboundPacketEvent;
import rip.hippo.parcel.event.impl.OutboundPacketEvent;
import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.generate.PacketFactory;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public final class ParcelChannelDuplexHandler extends ChannelDuplexHandler {

    private final Parcel parcel;
    private final Player player;

    public ParcelChannelDuplexHandler(Parcel parcel, Player player) {
        this.parcel = parcel;
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Packet packet = PacketFactory.from(msg);
        OutboundPacketEvent outboundPacketEvent = new OutboundPacketEvent(player, packet);
        parcel.post(outboundPacketEvent);
        if (outboundPacketEvent.isCancelled()) {
            promise.cancel(false);
        } else {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = PacketFactory.from(msg);
        InboundPacketEvent inboundPacketEvent = new InboundPacketEvent(player, packet);
        parcel.post(inboundPacketEvent);
        super.channelRead(ctx, msg);
    }
}
