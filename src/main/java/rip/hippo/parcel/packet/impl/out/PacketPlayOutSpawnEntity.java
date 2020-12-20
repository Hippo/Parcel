package rip.hippo.parcel.packet.impl.out;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.type.FieldType;

/**
 * @author Hippo
 * @version 1.0.0, 12/19/20
 * @since 1.0.0
 */
public interface PacketPlayOutSpawnEntity extends Packet {

    @GetterField(name = "a", type = FieldType.INT)
    int getEntityId();

    @SetterField(name = "a", type = FieldType.INT)
    void setEntityId(int entityId);

    @GetterField(name = "j", type = FieldType.INT)
    int getType();

    @SetterField(name = "j", type = FieldType.INT)
    void setType(int type);

    @GetterField(name = "b", type = FieldType.INT)
    int getX();

    @SetterField(name = "b", type = FieldType.INT)
    void setX(int x);

    @GetterField(name = "c", type = FieldType.INT)
    int getY();

    @SetterField(name = "c", type = FieldType.INT)
    void setY(int y);

    @GetterField(name = "d", type = FieldType.INT)
    int getZ();

    @SetterField(name = "d", type = FieldType.INT)
    void getZ(int z);

    @GetterField(name = "h", type = FieldType.INT)
    int getYaw();

    @SetterField(name = "h", type = FieldType.INT)
    void setYaw(int yaw);

    @GetterField(name = "i", type = FieldType.INT)
    int getPitch();

    @SetterField(name = "i", type = FieldType.INT)
    void setPitch(int pitch);

    @GetterField(name = "k", type = FieldType.INT)
    int getHeadPitch();

    @SetterField(name = "k", type = FieldType.INT)
    void setHeadPitch(int pitch);

    @GetterField(name = "e", type = FieldType.INT)
    int getVelocityX();

    @SetterField(name = "e", type = FieldType.INT)
    void setVelocityX(int velocityX);

    @GetterField(name = "f", type = FieldType.INT)
    int getVelocityY();

    @SetterField(name = "f", type = FieldType.INT)
    void setVelocityY(int velocityY);

    @GetterField(name = "g", type = FieldType.INT)
    int getVelocityZ();

    @SetterField(name = "g", type = FieldType.INT)
    void setVelocityZ(int velocityZ);
}
