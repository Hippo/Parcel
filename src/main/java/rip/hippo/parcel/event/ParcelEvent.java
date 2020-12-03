package rip.hippo.parcel.event;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public interface ParcelEvent {
    void setCancelled(boolean cancel);
    boolean isCancelled();
}
