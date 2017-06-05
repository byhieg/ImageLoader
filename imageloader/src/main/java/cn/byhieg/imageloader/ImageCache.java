package cn.byhieg.imageloader;

import android.graphics.Bitmap;

/**
 * Created by byhieg on 17/6/5.
 * Contact with byhieg@gmail.com
 */

public interface ImageCache {

    public Bitmap get(String url);

    public void put(String url, Bitmap bmp);
}
