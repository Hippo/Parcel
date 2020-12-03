package rip.hippo.parcel.packet.generate;

import rip.hippo.parcel.packet.Packet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public enum PacketFactory {
    ;

    private static final Map<String, Class<Packet>> RAW_TO_WRAPPER_MAP = Collections.unmodifiableMap(new HashMap<String, Class<Packet>>() {{

    }});


    public static Packet create(Object raw) {
        String rawClassName = raw.getClass().getName();
        Class<Packet> packetClass = RAW_TO_WRAPPER_MAP.get(rawClassName.substring(rawClassName.lastIndexOf('.') + 1));


    }

}
