package rip.hippo.parcel.wrapper;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;


/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public interface PlayerWrapper {
    Channel getChannel(Player player);
}
