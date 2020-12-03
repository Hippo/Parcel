package rip.hippo.parcel.packet.impl.out;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.type.FieldType;

/**
 * @author Hippo
 * @version 1.0.0, 12/3/20
 * @since 1.0.0
 */
public interface PacketLoginOutDisconnect extends Packet {

    @GetterField(name = "a", type = FieldType.OBJECT)
    Object getReason();
}
