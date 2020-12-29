package rip.hippo.parcel.packet.impl.out;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.EnumWrapper;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.enums.EnumPlayerInfoAction;
import rip.hippo.parcel.packet.type.FieldType;

import java.util.List;

/**
 * @author Hippo
 * @version 1.0.0, 12/29/20
 * @since 1.0.0
 */
public interface PacketPlayOutPlayerInfo extends Packet {

    @GetterField(name = "a", type = FieldType.OBJECT)
    @EnumWrapper(EnumPlayerInfoAction.class)
    EnumPlayerInfoAction getPlayerInfoAction();

    @SetterField(name = "a", type = FieldType.OBJECT)
    @EnumWrapper(EnumPlayerInfoAction.class)
    void setPlayerInfoAction(EnumPlayerInfoAction playerInfoAction);

    @GetterField(name = "b", type = FieldType.OBJECT)
    List<Object> getPlayerInfoData();

    @SetterField(name = "b", type = FieldType.OBJECT)
    void setPlayerInfoData(List<Object> playerInfoData);
}
