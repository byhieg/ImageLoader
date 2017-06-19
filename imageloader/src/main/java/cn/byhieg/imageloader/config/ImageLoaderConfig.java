package cn.byhieg.imageloader.config;

import cn.byhieg.imageloader.cache.MemoryCache;

/**
 * Created by byhieg on 17/6/19.
 * Contact with byhieg@gmail.com
 */

public class ImageLoaderConfig {

    BitmapCache bitmapCache = new MemoryCache();

    DisplayConfig displayConfig = new DisplayConfig();

    LoadPolicy loadPolicy = new SerialPolicy();

    int threadCount = Runtime.getRuntime().availableProcessors() + 1;

    private ImageLoaderConfig(){

    }


    public static class Builder{

        BitmapCache bitmapCache = new MemoryCache();

        DisplayConfig displayConfig = new DisplayConfig();

        LoadPolicy loadPolicy = new SerialPolicy();

        int threadCount = Runtime.getRuntime().availableProcessors() + 1;

        public Builder setThreadCount(int count) {
            threadCount = Math.max(1, count);
            return this;
        }

        public Builder setCache(BitmapCache cache) {
            bitmapCache = cache;
            return this;
        }

        public Builder setLoadingPlaceholder(int resId) {
            displayConfig.loadingResId = resId;
            return this;
        }

        public Builder setNotFoundPlaceholder(int resId) {
            displayConfig.failedResId = resId;
            return this;
        }

        public Builder setLoadPolicy(LoadPolicy policy) {
            if (policy == null) {
                loadPolicy = policy;
            }
            return this;
        }

        public ImageLoaderConfig create(){
            ImageLoaderConfig config = new ImageLoaderConfig();
            applyConfig(config);
            return config;
        }

        private void applyConfig(ImageLoaderConfig config) {
            config.bitmapCache = bitmapCache;
            config.displayConfig = displayConfig;
            config.loadPolicy = loadPolicy;
            config.threadCount = threadCount;
        }


    }
}
