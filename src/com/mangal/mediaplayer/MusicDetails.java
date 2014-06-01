package com.mangal.mediaplayer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mangal.data.SampleData;
import com.mangal.module.MP3InfoSimple;
import com.mangal.util.Utils;

public class MusicDetails extends FragmentActivity {

	private ArrayList<MP3InfoSimple> mData;

	@ViewInject(R.id.musicDetailsPoster)
	ImageView poster;
	@ViewInject(R.id.musicDetailsAR)
	TextView ar;
	@ViewInject(R.id.musicDetailsAL)
	TextView al;
	@ViewInject(R.id.musicDetailsSW)
	TextView sw;
	@ViewInject(R.id.musicDetailsSongBy)
	TextView songBy;
	@ViewInject(R.id.musicDetailsLS)
	TextView ls;
	@ViewInject(R.id.musicDetailsPS)
	TextView ps;
	@ViewInject(R.id.musicDetailsDescription)
	TextView description;
	@ViewInject(R.id.comments)
	ListView comments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_details);
		ViewUtils.inject(this);
		BitmapUtils bitmapUtils = Utils.getBitmapUtils(this);

		int mp3_id = getIntent().getExtras().getInt("mp3_id");
		LogUtils.i("" + mp3_id);

		if (mData == null) {
			mData = SampleData.generateSampleData();
		}
		MP3InfoSimple mp3InfoSimple = mData.get(mp3_id);
		setTitle(mp3InfoSimple.getName());
		if (mp3InfoSimple.getUrl_PosterMin() != null) {
			bitmapUtils.display(poster, mp3InfoSimple.getUrl_PosterMin());
		}else {
			poster.setVisibility(View.GONE);
		}
		FileReader reader = null;
		try {
			if (mp3InfoSimple.getUrl_Lyric() != null) {
				File file = new File(mp3InfoSimple.getUrl_Lyric());
				reader = new FileReader(file);
				String result = IOUtils.toString(reader);
				description.setText(result);
			}

		} catch (IOException ioe) {
			System.out.println("Unable to copy file test.dat to a String.");
		} finally {
			IOUtils.closeQuietly(reader);
		}

		PlayControlFragment playControlFragment = PlayControlFragment.instance();
		Bundle bundle = new Bundle();
		bundle.putSerializable("mp3InfoSimple", mp3InfoSimple);
		playControlFragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.details_play_control, playControlFragment)
				.commit();

	}

}
