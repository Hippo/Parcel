package rip.hippo.parcel.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rip.hippo.lwjeb.annotation.Handler;
import rip.hippo.lwjeb.bus.PubSub;
import rip.hippo.parcel.Parcel;
import rip.hippo.parcel.event.ParcelEvent;
import rip.hippo.parcel.event.impl.InboundPacketEvent;
import rip.hippo.parcel.listener.PlayerConnectionListener;
import rip.hippo.parcel.packet.impl.in.PacketPlayInBlockDig;
import rip.hippo.parcel.packet.inject.PlayerChannelInjector;
import rip.hippo.parcel.packet.inject.impl.StandardChannelInjector;
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
    private final PlayerChannelInjector playerChannelInjector;

    public ParcelPlugin() {
        this.pubSub = new PubSub<>();
        this.playerWrapper = PlayerWrapperFactory.createPlayerWrapper();
        this.playerChannelInjector = new StandardChannelInjector(this);
    }


    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(this), this);
    }

    @Override
    public void subscribe(JavaPlugin javaPlugin) {
        pubSub.subscribe(javaPlugin);
    }

    @Override
    public void post(ParcelEvent parcelEvent) {
        pubSub.post(parcelEvent).dispatch();
    }

    @Override
    public void unsubscribe(JavaPlugin javaPlugin) {
        pubSub.subscribe(javaPlugin);
    }

    @Override
    public PlayerWrapper getPlayerWrapper() {
        return playerWrapper;
    }

    @Override
    public PlayerChannelInjector getPlayerChannelInjector() {
        return playerChannelInjector;
    }
}
