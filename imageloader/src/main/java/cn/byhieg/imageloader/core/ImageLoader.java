package cn.byhieg.imageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import cn.byhieg.imageloader.cache.MemoryCache;

/**
 * Created by byhieg on 17/6/5.
 * Contact with byhieg@gmail.com
 */

public final class ImageLoader {

    //ImageLoader实例
    private static ImageLoader sInstance;

    //网络请求队列
    private RequestQueue mImageQueue;

    //缓存
    private volatile BitmapCache mCache = new MemoryCache();

    private ImageLoaderConfig mConfig;

    private ImageLoader(){

    }

    public static ImageLoader getInstance(){
        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new ImageLoader();
                }
            }
        }
        return sInstance;
    }

    public void init(ImageLoaderConfig config) {
        mConfig = config;
        mCache = mConfig.bitmapCache;
        checkConfig();
        mImageQueue = new RequestQueue(mConfig.threadCount);
        mImageQueue.start();
    }

    private void checkConfig() {
        if (mConfig == null) {
            throw new RuntimeException(
                    "The config of SimpleImageLoader is Null, please call the init(ImageLoaderConfig config) method to initialize");
        }

        if (mConfig.loadPolicy == null) {
            mConfig.loadPolicy = new SerialPolicy();
        }
        if (mCache == null) {
            mCache = new MemoryCache();
        }

    }

    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null, null);
    }

    public void displayImage(ImageView imageView, String uri, DisplayConfig config) {
        displayImage(imageView, uri, config, null);
    }

    public void displayImage(ImageView imageView, String uri, ImageListener listener) {
        displayImage(imageView, uri, null, listener);
    }

    public void displayImage(final ImageView imageView, final String uri,
                             final DisplayConfig config, final ImageListener listener) {
        BitmapRequest request = new BitmapRequest(imageView, uri, config, listener);
        // 加载的配置对象,如果没有设置则使用ImageLoader的配置
        request.displayConfig = request.displayConfig != null ? request.displayConfig
                : mConfig.displayConfig;
        // 添加对队列中
        mImageQueue.addRequest(request);
    }

    public ImageLoaderConfig getConfig() {
        return mConfig;
    }

    public void stop() {
        mImageQueue.stop();
    }

    /**
     * 图片加载Listener
     *
     * @author mrsimple
     */
    public static interface ImageListener {
        public void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

    //线程池,线程数量为CPU数量
//    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//
//    ImageCache mImageCache = new MemoryCache();

    //注入缓存实现
//    public void setImageCache(ImageCache cache) {
//        mImageCache = cache;
//    }
//
//    public void displayImage(final String url, final ImageView imageView) {
//        Bitmap bitmap = mImageCache.get(url);
//        if (bitmap != null) {
//            imageView.setImageBitmap(bitmap);
//            return;
//        }
//
//        submitLoadRequest(url, imageView);
//
//    }
//
//    private void submitLoadRequest(final String url, final ImageView imageView) {
//        imageView.setTag(url);
//        mExecutorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = downloadImage(url);
//                if (bitmap == null) {
//                    return;
//                }
//                if (imageView.getTag().equals(url)) {
//                    imageView.setImageBitmap(bitmap);
//                }
//                mImageCache.put(url, bitmap);
//            }
//        });
//    }
//
//
//    public Bitmap downloadImage(String imageUrl) {
//        Bitmap bitmap = null;
//        try {
//            URL url = new URL(imageUrl);
//            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
//            conn.disconnect();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }


}
