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
public interface PacketPlayInAbilities extends Packet {

    @GetterField(name = "a", type = FieldType.BOOLEAN)
    boolean isInvulnerable();

    @SetterField(name = "a", type = FieldType.BOOLEAN)
    void setInvulnerable(boolean invulnerable);

    @GetterField(name = "b", type = FieldType.BOOLEAN)
    boolean isFlying();

    @SetterField(name = "b", type = FieldType.BOOLEAN)
    void setFlying(boolean flying);

    @GetterField(name = "c", type = FieldType.BOOLEAN)
    boolean allowFlying();

    @SetterField(name = "c", type = FieldType.BOOLEAN)
    void setAllowFlying(boolean allowFlying);

    @GetterField(name = "d", type = FieldType.BOOLEAN)
    boolean isCreative();

    @SetterField(name = "d", type = FieldType.BOOLEAN)
    void setCreative(boolean creative);
}
