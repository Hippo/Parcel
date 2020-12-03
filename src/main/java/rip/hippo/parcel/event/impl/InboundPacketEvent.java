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

    public InboundPacketEvent(Player sender, Packet packet) {
        this.sender = sender;
        this.packet = packet;
    }

    public Player getSender() {
        return sender;
    }

    public Packet getPacket() {
        return packet;
    }
}
