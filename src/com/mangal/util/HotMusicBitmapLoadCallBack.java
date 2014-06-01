package com.mangal.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.mangal.adapter.HotMusicAdapter;
import com.mangal.adapter.HotMusicAdapter.ViewHolder;
import com.mangal.mediaplayer.R;

public class HotMusicBitmapLoadCallBack extends
		DefaultBitmapLoadCallBack<ImageView> {

	HotMusicAdapter.ViewHolder viewHolder;

	public HotMusicBitmapLoadCallBack(ViewHolder viewHolder) {
		super();
		this.viewHolder = viewHolder;
	}

	@Override
	public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap,
			BitmapDisplayConfig config, BitmapLoadFrom from) {
		// TODO Auto-generated method stub
		((DynamicHeightImageView) container).setWH2Scale(bitmap.getWidth(),
				bitmap.getHeight());
		viewHolder.onPoster.setImageResource(R.drawable.hot_music);
		super.onLoadCompleted(container, uri, bitmap, config, from);
	}

	@Override
	public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
		// TODO Auto-generated method stub
		super.onLoadFailed(container, uri, drawable);
	}

}