package rip.hippo.parcel.packet.inject.impl;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;
import rip.hippo.parcel.Parcel;
import rip.hippo.parcel.packet.channel.ParcelChannelDuplexHandler;
import rip.hippo.parcel.packet.inject.PlayerChannelInjector;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public final class StandardChannelInjector implements PlayerChannelInjector {

    private final Parcel parcel;

    public StandardChannelInjector(Parcel parcel) {
        this.parcel = parcel;
    }

    @Override
    public void inject(Player player) {
        Channel channel = parcel.getPlayerWrapper().getChannel(player);
        if (channel != null && channel.pipeline().get("parcel_pipeline") == null) {
            channel.pipeline().addBefore("packet_handler", "parcel_pipeline", new ParcelChannelDuplexHandler(parcel));
        }
    }

    @Override
    public void uninject(Player player) {
        Channel channel = parcel.getPlayerWrapper().getChannel(player);
        if (channel != null && channel.pipeline().get("parcel_pipeline") != null) {
            channel.pipeline().remove("parcel_pipeline");
        }
    }
}
