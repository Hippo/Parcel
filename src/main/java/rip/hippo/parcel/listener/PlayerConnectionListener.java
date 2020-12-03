package rip.hippo.parcel.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import rip.hippo.parcel.Parcel;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public final class PlayerConnectionListener implements Listener {

    private final Parcel parcel;

    public PlayerConnectionListener(Parcel parcel) {
        this.parcel = parcel;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        parcel.getPlayerChannelInjector().inject(playerJoinEvent.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        parcel.getPlayerChannelInjector().uninject(playerQuitEvent.getPlayer());
    }
}
