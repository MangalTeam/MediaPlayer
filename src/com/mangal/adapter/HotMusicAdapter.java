package com.mangal.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mangal.mediaplayer.R;
import com.mangal.module.MP3InfoSimple;
import com.mangal.util.HotMusicBitmapLoadCallBack;
import com.mangal.util.DynamicHeightImageView;
import com.mangal.util.NetMusicBitmapLoadCallBack;

/***
 * ADAPTER
 */

public class HotMusicAdapter extends BaseAdapter {

	private ArrayList<MP3InfoSimple> mp3InfoSimples = new ArrayList<MP3InfoSimple>();
	private BitmapUtils bitmapUtils;

	public class ViewHolder {
		@ViewInject(R.id.poster)
		DynamicHeightImageView poster;
		@ViewInject(R.id.onPoster)
		public DynamicHeightImageView onPoster;
		@ViewInject(R.id.musicName)
		TextView musicName;
		@ViewInject(R.id.musicAL)
		TextView musicAL;
	}

	private final LayoutInflater mLayoutInflater;

	public HotMusicAdapter(final Context context) {
		super();
		mLayoutInflater = LayoutInflater.from(context);
		bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDefaultLoadingImage(R.drawable.poster_default);
		// bitmapUtils.configDefaultLoadFailedImage(R.drawable.bitmap);
		ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(600);
		bitmapUtils.configDefaultImageLoadAnimation(animation);
	}

	public BitmapUtils getBitmapUtils() {
		return bitmapUtils;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.net_music_card_view,
					parent, false);
			vh = new ViewHolder();
			ViewUtils.inject(vh, convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		vh.musicName.setText(mp3InfoSimples.get(position).getName());
		// vh.musicAL.setText(mp3InfoSimples.get(position));
		if (mp3InfoSimples.get(position).getUrl_PosterMin() != null) {
			bitmapUtils.display(vh.poster, mp3InfoSimples.get(position)
					.getUrl_PosterMin(), new HotMusicBitmapLoadCallBack(vh));
		} else {
			bitmapUtils.display(vh.poster, "assets/img/poster_default.png",
					new NetMusicBitmapLoadCallBack());
		}

		return convertView;
	}

	public void addMp3InfoSimples(ArrayList<MP3InfoSimple> mp3InfoSimples) {
		this.mp3InfoSimples.addAll(mp3InfoSimples);
	}

	public void addMp3InfoSimples(MP3InfoSimple mp3InfoSimple) {
		this.mp3InfoSimples.add(mp3InfoSimple);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mp3InfoSimples.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}