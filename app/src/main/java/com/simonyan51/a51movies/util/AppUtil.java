package com.simonyan51.a51movies.util;

/**
 * Created by simonyan51 on 07.08.2017.
 */

public class AppUtil {

    public static int booleanToInt(boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static boolean intToBoolean(int b) {
        if (b == 1) {
            return true;
        } else {
            return false;
        }
    }
}