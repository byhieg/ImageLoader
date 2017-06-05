package cn.byhieg.imageloader.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by byhieg on 17/6/5.
 * Contact with byhieg@gmail.com
 */

public class CloseUtils {

    private CloseUtils(){

    }

    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
