package rip.hippo.parcel.packet.impl.out;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.EnumWrapper;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.enums.EnumScoreboardHealthDisplay;
import rip.hippo.parcel.packet.type.FieldType;

/**
 * @author Hippo
 * @version 1.0.0, 12/28/20
 * @since 1.0.0
 */
public interface PacketPlayOutScoreboardObjective extends Packet {

    int CREATE = 0;
    int REMOVE = 1;
    int UPDATE = 2;

    @GetterField(name = "a", type = FieldType.OBJECT)
    String getScoreName();

    @SetterField(name = "a", type = FieldType.OBJECT)
    void setScoreName(String scoreName);

    @GetterField(name = "d", type = FieldType.INT)
    int getMode();

    @SetterField(name = "d", type = FieldType.INT)
    void setMode(int mode);

    @GetterField(name = "b", type = FieldType.OBJECT)
    String getObjectiveValue();

    @SetterField(name = "b", type = FieldType.OBJECT)
    void setObjectiveValue(String objectiveValue);

    @GetterField(name = "c", type = FieldType.OBJECT)
    @EnumWrapper(EnumScoreboardHealthDisplay.class)
    EnumScoreboardHealthDisplay getType();

    @SetterField(name = "c", type = FieldType.OBJECT)
    @EnumWrapper(EnumScoreboardHealthDisplay.class)
    void setType(EnumScoreboardHealthDisplay enumScoreboardHealthDisplay);


}
