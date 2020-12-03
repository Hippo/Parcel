package rip.hippo.parcel;

import org.bukkit.plugin.java.JavaPlugin;
import rip.hippo.parcel.event.ParcelEvent;
import rip.hippo.parcel.packet.inject.PlayerChannelInjector;
import rip.hippo.parcel.wrapper.PlayerWrapper;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public interface Parcel {
    void subscribe(JavaPlugin javaPlugin);
    void post(ParcelEvent parcelEvent);
    void unsubscribe(JavaPlugin javaPlugin);
    PlayerWrapper getPlayerWrapper();
    PlayerChannelInjector getPlayerChannelInjector();
}
