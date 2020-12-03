package rip.hippo.parcel.wrapper;

import org.bukkit.entity.Player;

import java.nio.channels.Channel;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public interface PlayerWrapper {
    Channel getChannel(Player player);
}
