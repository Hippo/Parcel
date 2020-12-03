package rip.hippo.parcel.packet.impl.in;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.type.FieldType;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public interface PacketPlayInChat extends Packet {

    @SetterField(name = "a", type = FieldType.OBJECT)
    void setMessage(String message);

    @GetterField(name = "a", type = FieldType.OBJECT)
    String getMessage();
}
