package rip.hippo.parcel.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import rip.hippo.lwjeb.bus.PubSub;
import rip.hippo.parcel.Parcel;
import rip.hippo.parcel.event.ParcelEvent;
import rip.hippo.parcel.wrapper.PlayerWrapper;
import rip.hippo.parcel.wrapper.PlayerWrapperFactory;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public final class ParcelPlugin extends JavaPlugin implements Parcel {

    private final PubSub<ParcelEvent> pubSub;
    private final PlayerWrapper playerWrapper;

    public ParcelPlugin() {
        this.pubSub = new PubSub<>();
        this.playerWrapper = PlayerWrapperFactory.createPlayerWrapper();
    }

    @Override
    public void subscribe(JavaPlugin javaPlugin) {
        pubSub.subscribe(javaPlugin);
    }

    @Override
    public void unsubscribe(JavaPlugin javaPlugin) {
        pubSub.subscribe(javaPlugin);
    }

    @Override
    public PlayerWrapper getPlayerWrapper() {
        return playerWrapper;
    }
}
