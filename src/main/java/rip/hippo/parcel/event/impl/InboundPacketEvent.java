package rip.hippo.parcel.event.impl;

import org.bukkit.entity.Player;
import rip.hippo.parcel.event.ParcelEvent;
import rip.hippo.parcel.packet.Packet;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public final class InboundPacketEvent implements ParcelEvent {

    private final Player sender;
    private final Packet packet;

    private boolean cancel;

    public InboundPacketEvent(Player sender, Packet packet) {
        this.sender = sender;
        this.packet = packet;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    public Player getSender() {
        return sender;
    }

    public Packet getPacket() {
        return packet;
    }
}
