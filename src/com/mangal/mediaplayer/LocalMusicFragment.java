package com.mangal.mediaplayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mangal.data.SampleData;
import com.mangal.module.MP3InfoSimple;

public class LocalMusicFragment extends Fragment {

	@ViewInject(R.id.localMusicCount)
	TextView localMusicCount;
	@ViewInject(R.id.menuMusicCount)
	TextView menuMusicCount;
	@ViewInject(R.id.musicListCount)
	TextView musicListCount;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// 设置主视图的控制栏
		MP3InfoSimple mp3InfoSimple = SampleData.generateSampleData().get(6);
		PlayControlFragment playControlFragment = PlayControlFragment
				.instance();
		Bundle bundle = new Bundle();
		bundle.putSerializable("mp3InfoSimple", mp3InfoSimple);
		playControlFragment.setArguments(bundle);
		getChildFragmentManager().beginTransaction()
				.replace(R.id.localmusic_play_control, playControlFragment)
				.commit();
//		localMusicCount = (TextView) getView().findViewById(R.id.localMusicCount);
//		localMusicCount.setText(Menu.getMusicList().size());
//		menuMusicCount = (TextView) getView().findViewById(R.id.menuMusicCount);
//		menuMusicCount.setText(Menu.getMusicList().size());
//		musicListCount = (TextView) getView().findViewById(R.id.musicListCount);
//		musicListCount.setText(1); 
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_loacal_music, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		// getChildFragmentManager().putFragment(outState,
		// "localmusic_play_control", playControlFragment);
	}

}
