package rip.hippo.parcel.event.impl;

import org.bukkit.entity.Player;
import rip.hippo.parcel.event.ParcelEvent;
import rip.hippo.parcel.packet.Packet;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public final class OutboundPacketEvent implements ParcelEvent {

    private final Player player;
    private final Packet packet;

    private boolean cancel;

    public OutboundPacketEvent(Player player, Packet packet) {
        this.player = player;
        this.packet = packet;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public Player getPlayer() {
        return player;
    }

    public Packet getPacket() {
        return packet;
    }
}
