package rip.hippo.parcel.packet.impl.out;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.type.FieldType;

/**
 * @author Hippo
 * @version 1.0.0, 12/28/20
 * @since 1.0.0
 */
public interface PacketPlayOutScoreboardDisplayObjective extends Packet {

    int LIST = 0;
    int SIDEBAR = 1;
    int BELOW_NAME = 2;

    @GetterField(name = "a", type = FieldType.INT)
    int getPosition();

    @SetterField(name = "a", type = FieldType.INT)
    void setPosition(int position);

    @GetterField(name = "b", type = FieldType.OBJECT)
    String getScoreName();

    @SetterField(name = "b", type = FieldType.OBJECT)
    void setScoreName(String scoreName);

}
