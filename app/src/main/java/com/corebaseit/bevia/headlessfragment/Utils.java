package com.corebaseit.bevia.headlessfragment;

/**
 * Created by vbevia on 10/03/16.
 */
public class Utils {

    public static void sleepForInSecs(int secs) {
        try {
            Thread.sleep(secs * 10);
        } catch (InterruptedException x) {
            throw new RuntimeException("interrupted", x);
        }
    }
}
