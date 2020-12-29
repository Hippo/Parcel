package rip.hippo.parcel.packet.impl.out;

import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.type.FieldType;

import java.util.Collection;

/**
 * @author Hippo
 * @version 1.0.0, 12/28/20
 * @since 1.0.0
 */
public interface PacketPlayOutScoreboardTeam extends Packet {

    int TEAM_CREATE = 0;
    int TEAM_REMOVE = 1;
    int TEAM_INFO_UPDATE = 2;
    int ADD_PLAYER = 3;
    int REMOVE_PLAYER = 4;

    int FRIENDLY_FIRE_OFF = 0;
    int FRIENDLY_FIRE_ON = 1;
    int SEE_FRIENDLY_INVISIBLES = 3;

    @GetterField(name = "a", type = FieldType.OBJECT)
    String getTeamName();

    @SetterField(name = "a", type = FieldType.OBJECT)
    void setTeamName(String teamName);

    @GetterField(name = "h", type = FieldType.INT)
    int getMode();

    @SetterField(name = "h", type = FieldType.INT)
    void setMode(int mode);

    @GetterField(name = "b", type = FieldType.OBJECT)
    String getTeamDisplayName();

    @SetterField(name = "b", type = FieldType.OBJECT)
    void setTeamDisplayName(String teamDisplayName);

    @GetterField(name = "c", type = FieldType.OBJECT)
    String getTeamPrefix();

    @SetterField(name = "c", type = FieldType.OBJECT)
    void setTeamPrefix(String teamPrefix);

    @GetterField(name = "d", type = FieldType.OBJECT)
    String getTeamSuffix();

    @SetterField(name = "d", type = FieldType.OBJECT)
    void setTeamSuffix(String teamSuffix);

    @GetterField(name = "i", type = FieldType.INT)
    int getFriendlyFire();

    @SetterField(name = "i", type = FieldType.INT)
    void setFriendlyFire(int friendlyFire);

    @GetterField(name = "e", type = FieldType.OBJECT)
    String getNameTagVisibility();

    @SetterField(name = "e", type = FieldType.OBJECT)
    void setNameTagVisibility(String nameTagVisibility);

    @GetterField(name = "f", type = FieldType.INT)
    int getChatColor();

    @SetterField(name = "f", type = FieldType.INT)
    void setChatColor(int chatColor);

    @GetterField(name = "g", type = FieldType.OBJECT)
    Collection<String> getPlayers();

    @SetterField(name = "g", type = FieldType.OBJECT)
    void setPlayers(Collection<String> players);


}
