package rip.hippo.parcel.packet.impl.in;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.EnumWrapper;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.enums.EnumDirection;
import rip.hippo.parcel.packet.enums.EnumPlayerDigType;
import rip.hippo.parcel.packet.type.FieldType;

/**
 * @author Hippo
 * @version 1.0.0, 12/3/20
 * @since 1.0.0
 */
public interface PacketPlayInBlockDig extends Packet {

    @GetterField(name = "a", type = FieldType.OBJECT)
    Object getBlockPosition();

    @GetterField(name = "b", type = FieldType.OBJECT)
    @EnumWrapper(EnumDirection.class)
    EnumDirection getDirection();

    @SetterField(name = "b", type = FieldType.OBJECT)
    @EnumWrapper(EnumDirection.class)
    void setDirection(EnumDirection enumDirection);

    @GetterField(name = "c", type = FieldType.OBJECT)
    @EnumWrapper(EnumPlayerDigType.class)
    EnumPlayerDigType getDigType();

    @SetterField(name = "c", type = FieldType.OBJECT)
    @EnumWrapper(EnumPlayerDigType.class)
    void setDigType(EnumPlayerDigType enumDirection);
}
