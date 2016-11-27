package org.ojothepojo.lifx.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Util {
    public static void checkUnsigned8bit(int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Value must be an unsigned 8-bit integer");
        }
    }

    public static void checkUnsigned16bit(int value) {
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException("Value must be an unsigned 16-bit integer");
        }
    }

    public static void checkUnsigned32bit(long value) {
        if (value < 0 || value > 4294967295L) {
            throw new IllegalArgumentException("Value must be an unsigned 32-bit integer");
        }
    }

    public static void checkMacAddress(String macAddress) {
        if (!macAddress.matches("^([0-9A-Fa-f]{2}[:]){5}([0-9A-Fa-f]{2})$")) {
            throw new IllegalArgumentException("Mac Address must be in the form 00:00:00:00:00:00");
        }
    }

    public static void checkIpAddress(String ipAddress) {
        if (!ipAddress.matches("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
            throw new IllegalArgumentException("Mac Address must be in the form 255.255.255.255");
        }
    }

    public static String getIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
