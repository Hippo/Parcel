package rip.hippo.parcel.packet.channel;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;
import rip.hippo.parcel.Parcel;

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
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
