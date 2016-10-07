package org.ojothepojo.lifx.field;


import java.util.HashMap;
import java.util.Map;

public enum Type {
    GET_SERVICE(2),
    STATE_SERVICE(3),
    GET_HOST_INFO(12),
    STATE_HOST_INFO(13),
    GET_HOST_FIRMWARE(14),
    STATE_HOST_FIRMWARE(15),
    GET_WIFI_INFO(16),
    STATE_WIFI_INFO(17),
    GET_WIFI_FIRMWARE(18),
    STATE_WIFI_FIRMWARE(19),
    GET_DEVICE_POWER(20),
    SET_DEVICE_POWER(21),
    STATE_DEVICE_POWER(22),
    GET_LABEL(23),
    SET_LABEL(24),
    STATE_LABEL(25),
    GET_VERSION(32),
    STATE_VERSION(33),
    GET_INFO(34),
    STATE_INFO(35),
    ACKNOWLEDGEMENT(45),
    GET_LOCATION(48),
    STATE_LOCATION(50),
    GET_GROUP(51),
    STATE_GROUP(53),
    ECHO_REQUEST(58),
    ECHO_RESPONSE(59),
    GET(101),
    SET_COLOR(102),
    STATE(107),
    GET_LIGHT_POWER(116),
    SET_LIGHT_POWER(117),
    STATE_LIGHT_POWER(118);

    private int value;

    private static Map<Integer, Type> map = new HashMap<Integer, Type>();

    static {
        for (Type type : Type.values()) {
            map.put(type.value, type);
        }
    }

    Type(int value) {
        this.value = value;
    }

    public static Type valueOf(int i) {
        return map.get(i);
    }

}
