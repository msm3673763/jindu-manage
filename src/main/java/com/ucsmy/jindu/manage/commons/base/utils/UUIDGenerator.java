package com.ucsmy.jindu.manage.commons.base.utils;
import java.net.InetAddress;
/**
 * Created by ucs_panwenbo on 2017/4/14.
 * @author  ucs_panwenbo on 2017/4/14.
 */
public class UUIDGenerator {



    private static final int IP;

    public static int iptoInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + bytes[i];
        }
        return result;
    }

    static {
        int ipadd;
        try {
            ipadd = iptoInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }
    private static short COUNTER = (short) 0;
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

    /**
     * Unique across JVMs on this machine (unless they load this class in the
     * same quater second - very unlikely)
     */
    protected static int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there are >
     * Short.MAX_VALUE instances created in a millisecond)
     */
    protected static short getCount() {
        synchronized (UUIDGenerator.class) {
            if (COUNTER < 0) {
                COUNTER = 0;
            }
            return COUNTER++;
        }
    }

    /**
     * Unique in a local network
     */
    protected static int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected static short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected static int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    private final static String SEP = "-";

    protected static String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected static String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    public static String generate() {
        return new StringBuffer(36).append(format(getIP())).append(SEP).append(
                format(getJVM())).append(SEP).append(format(getHiTime()))
                .append(SEP).append(format(getLoTime())).append(SEP).append(
                        format(getCount())).toString();
    }

    /**
     * 截取生成的id，同时防止最后一个字符为分隔符sep
     * @param capacity
     * @return
     */
    public static String generate(int capacity) {
        String uuid = generate();
        uuid = uuid.substring(0, capacity - 1);
        if (SEP.equals(uuid.substring(uuid.length() - 2, uuid.length() - 1))) {
            uuid = uuid.substring(0, uuid.length() - 2);
        }
        return uuid;
    }

    /**
     * 截取生成的id，截取的是末尾的位数，同时防止第一个字符为分隔符sep
     * @param capacity
     * @return
     */
    public static String generateFromEnd(int capacity) {
        String uuid = generate();
        uuid = uuid.substring(uuid.length()-capacity, uuid.length());
        if (SEP.equals(uuid.substring(0, 1))) {
            uuid = uuid.substring(1, uuid.length());
        }
        return uuid;
    }
}
