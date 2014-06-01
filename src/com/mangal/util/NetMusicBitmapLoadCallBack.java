package com.mangal.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

public class NetMusicBitmapLoadCallBack extends
		DefaultBitmapLoadCallBack<ImageView> {

	@Override
	public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap,
			BitmapDisplayConfig config, BitmapLoadFrom from) {
		// TODO Auto-generated method stub
		((DynamicHeightImageView) container).setWH2Scale(bitmap.getWidth(),
				bitmap.getHeight());
		super.onLoadCompleted(container, uri, bitmap, config, from);
	}

	@Override
	public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
		// TODO Auto-generated method stub
		super.onLoadFailed(container, uri, drawable);
	}

}