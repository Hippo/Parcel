package rip.hippo.parcel.packet.impl.out;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.EnumWrapper;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.enums.EnumScoreboardAction;
import rip.hippo.parcel.packet.type.FieldType;

/**
 * @author Hippo
 * @version 1.0.0, 12/28/20
 * @since 1.0.0
 */
public interface PacketPlayOutScoreboardScore extends Packet {

    @GetterField(name = "a", type = FieldType.OBJECT)
    String getScoreName();

    @SetterField(name = "a", type = FieldType.OBJECT)
    void setScoreName(String scoreName);

    @GetterField(name = "d", type = FieldType.OBJECT)
    @EnumWrapper(EnumScoreboardAction.class)
    EnumScoreboardAction getAction();
    
    @SetterField(name = "d", type = FieldType.OBJECT)
    @EnumWrapper(EnumScoreboardAction.class)
    void setAction(EnumScoreboardAction action);

    @GetterField(name = "b", type = FieldType.OBJECT)
    String getObjectiveName();

    @SetterField(name = "b", type = FieldType.OBJECT)
    void setObjectiveName(String objectiveName);

    @GetterField(name = "c", type = FieldType.INT)
    int getValue();

    @SetterField(name = "c", type = FieldType.INT)
    void setValue(int value);
}
