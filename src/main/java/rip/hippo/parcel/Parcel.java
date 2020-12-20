package rip.hippo.parcel;

import rip.hippo.parcel.event.ParcelEvent;
import rip.hippo.parcel.packet.inject.PlayerChannelInjector;
import rip.hippo.parcel.wrapper.PlayerWrapper;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public interface Parcel {
    void subscribe(Object parent);
    void post(ParcelEvent parcelEvent);
    void unsubscribe(Object parent);
    PlayerWrapper getPlayerWrapper();
    PlayerChannelInjector getPlayerChannelInjector();
}
