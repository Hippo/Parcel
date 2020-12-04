package rip.hippo.parcel.packet.impl.out;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.type.FieldType;

/**
 * @author Hippo
 * @version 1.0.0, 12/3/20
 * @since 1.0.0
 */
public interface PacketPlayOutAnimation extends Packet {

    int SWING_ARM = 0;
    int TAKE_DAMAGE = 1;
    int LEAVE_BED = 2;
    int EAT_FOOD = 3;
    int CRITICAL_EFFECT = 4;
    int MAGIC_CRITICAL_EFFECT = 5;

    @GetterField(name = "a", type = FieldType.INT)
    int getEntityId();

    @SetterField(name = "a", type = FieldType.INT)
    void setEntityId(int entityId);

    @GetterField(name = "b", type = FieldType.INT)
    int getAnimation();

    @SetterField(name = "b", type = FieldType.INT)
    void setAnimation(int animation);
}
