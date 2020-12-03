package rip.hippo.parcel.packet.impl.in;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.InConstructor;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public abstract class PacketPlayInChat implements Packet {


    private Object raw;

    @InConstructor
    public PacketPlayInChat(Object raw) {
        this.raw = raw;
    }
}
