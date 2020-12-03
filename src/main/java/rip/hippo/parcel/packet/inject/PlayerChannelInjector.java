package rip.hippo.parcel.packet.inject;

import org.bukkit.entity.Player;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public interface PlayerChannelInjector {
    void inject(Player player);
    void uninject(Player player);
}
