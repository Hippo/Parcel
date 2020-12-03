package rip.hippo.parcel.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import rip.hippo.lwjeb.bus.PubSub;
import rip.hippo.parcel.Parcel;
import rip.hippo.parcel.event.ParcelEvent;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public final class ParcelPlugin extends JavaPlugin implements Parcel {

    private final PubSub<ParcelEvent> pubSub;

    public ParcelPlugin() {
        this.pubSub = new PubSub<>();
    }

    @Override
    public void subscribe(JavaPlugin javaPlugin) {
        pubSub.subscribe(javaPlugin);
    }

    @Override
    public void unsubscribe(JavaPlugin javaPlugin) {
        pubSub.subscribe(javaPlugin);
    }
}
