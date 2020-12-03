package rip.hippo.parcel.packet.generate;

import rip.hippo.parcel.packet.Packet;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
@FunctionalInterface
public interface PacketFunction {
    Packet apply(Object raw);
}
