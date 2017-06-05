package cn.byhieg.imageloader;

import android.graphics.Bitmap;

/**
 * Created by byhieg on 17/6/5.
 * Contact with byhieg@gmail.com
 */

public class DoubleCache {
    private ImageCache mMemoryCache = new MemoryCache();
    private ImageCache mDiskCache = new DiskCache();

    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    public void put(String url, Bitmap bmp) {
        mMemoryCache.put(url, bmp);
        mDiskCache.put(url, bmp);
    }
}
