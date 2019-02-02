package com.varscon.apitester;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

// new since Glide v4
@GlideModule
public final class TestGlideModule extends AppGlideModule {
    // leave empty for now
    File mediaCacheDirectory;
//    SkyConstants skyConstants = new SkyConstants();
    public static final int DiskCacheInBytes = 100 * 1024 * 1024;
    public static final String DiskCacheName = "MyAppCacheDirectory";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        builder.setDiskCache(
                new ExternalCacheDiskCacheFactory(context, DiskCacheName, DiskCacheInBytes));

        builder.setMemoryCache(new LruResourceCache(DiskCacheInBytes));
        builder.setBitmapPool(new LruBitmapPool(DiskCacheInBytes));
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888));

    }

    private class DiskCacheFactory extends DiskLruCacheFactory {
        DiskCacheFactory(final Context context, final String diskCacheName, long diskCacheSize) {
            super(new CacheDirectoryGetter() {
                @Override
                public File getCacheDirectory() {
                    return mediaCacheDirectory;
                }

            }, (int) diskCacheSize);
        }
    }
}
