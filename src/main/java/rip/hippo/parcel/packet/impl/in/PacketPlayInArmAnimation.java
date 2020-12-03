package rip.hippo.parcel.packet.impl.in;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.type.FieldType;

/**
 * @author Hippo
 * @version 1.0.0, 12/3/20
 * @since 1.0.0
 */
public interface PacketPlayInArmAnimation extends Packet {

    @GetterField(name = "timestamp", type = FieldType.LONG)
    long getTimeStamp();

    @SetterField(name = "timestamp", type = FieldType.LONG)
    void setTimeStamp(long timeStamp);
}
