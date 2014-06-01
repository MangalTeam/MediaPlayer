package com.mangal.mediaplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mangal.module.MP3InfoSimple;
import com.mangal.service.BindMusicPlayerService;
import com.mangal.util.UpdateSeekBarCallBack;
import com.mangal.util.Utils;

public class PlayControlFragment extends Fragment implements
		SeekBar.OnSeekBarChangeListener, UpdateSeekBarCallBack {

	static PlayControlFragment playControlFragment;

	MP3InfoSimple mp3InfoSimple = null;

	// PlayControl
	@ViewInject(R.id.PCMusicSeekBar)
	SeekBar PCMusicSeekBar;
	@ViewInject(R.id.PCPoster)
	ImageView PCPoster;
	@ViewInject(R.id.PCMusicName)
	TextView PCMusicName;
	@ViewInject(R.id.PCAR)
	TextView PCAR;
	@ViewInject(R.id.PCSW)
	TextView PCSW;
	@ViewInject(R.id.PCCurrentTime)
	TextView PCCurrentTime;
	@ViewInject(R.id.PCEndTime)
	TextView PCEndTime;
	@ViewInject(R.id.PCLikeButton)
	ImageButton PCLikeButton;
	@ViewInject(R.id.PCHandButton)
	ImageButton PCHandButton;
	@ViewInject(R.id.PCRepeatModeButton)
	ImageButton PCRepeatModeButton;
	@ViewInject(R.id.PCPrevButton)
	ImageButton PCPrevButton;
	@ViewInject(R.id.PCPauseButton)
	ImageButton PCPauseButton;
	@ViewInject(R.id.PCPlayButton)
	ImageButton PCPlayButton;
	@ViewInject(R.id.PCNextButton)
	ImageButton PCNextButton;

	private BitmapUtils bitmapUtils;
	private Activity mActivity;
	private BindMusicPlayerService bindMusicPlayerService;
	private int seekBarMax;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			// mp3InfoSimple = (MP3InfoSimple)
			// savedInstanceState.get("com.mangal.mediaplayer.PlayControlFragment.mp3InfoSimple");
		}
		if (getArguments() != null) {
			mp3InfoSimple = (com.mangal.module.MP3InfoSimple) getArguments()
					.getSerializable("mp3InfoSimple");
		}
		bitmapUtils = new BitmapUtils(getActivity());
		mActivity = getActivity();
		connection();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.play_control, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		PCMusicSeekBar.setOnSeekBarChangeListener(this);
		reflushView();
		PCRepeatModeButton.setImageLevel(1);
		PCLikeButton.setImageLevel(1);
		PCHandButton.setImageLevel(1);
		seekBarMax = Utils.getWindowPixelWidth(mActivity);
		PCMusicSeekBar.setMax(seekBarMax);
		if (bindMusicPlayerService != null) {
			if (bindMusicPlayerService.isPlaying()) {
				PCMusicSeekBar.setVisibility(View.VISIBLE);
				PCPlayButton.setVisibility(View.GONE);
				PCPauseButton.setVisibility(View.VISIBLE);
			}
		}

	}

	public static PlayControlFragment instance() {
		if (playControlFragment != null) {
			return playControlFragment;
		}
		playControlFragment = new PlayControlFragment();
		return playControlFragment;
	}

	@OnClick(R.id.PCPauseButton)
	void PCPauseButtonClick(View view) {
		if (bindMusicPlayerService != null) {
			PCPauseButton.setVisibility(View.GONE);
			PCPlayButton.setVisibility(View.VISIBLE);
			bindMusicPlayerService.puase();
		}

	}

	@OnClick(R.id.PCPlayButton)
	void PCPlayButton(View view) {
		if (bindMusicPlayerService != null) {
			PCPlayButton.setVisibility(View.GONE);
			PCPauseButton.setVisibility(View.VISIBLE);
			PCMusicSeekBar.setVisibility(View.VISIBLE);
			bindMusicPlayerService.setUpdateSeekBar(this);
			bindMusicPlayerService.play();
		}

	}

	@OnClick(R.id.PCPrevButton)
	void PCPrevButton(View view) {
		if (mp3InfoSimple != null) {
			if (Menu.getMusicList().indexOf(mp3InfoSimple) - 1 >= 0) {
				this.mp3InfoSimple = Menu.getMusicList().get(
						Menu.getMusicList().indexOf(mp3InfoSimple) - 1);

			}
			if (mp3InfoSimple != null) {
				setContent(this.mp3InfoSimple);
				play();
				reflushView();
			}

		}

	}

	@OnClick(R.id.PCNextButton)
	void PCNextButton(View view) {
		if (mp3InfoSimple != null)
			if (Menu.getMusicList().indexOf(mp3InfoSimple) + 1 < Menu
					.getMusicList().size()) {

				this.mp3InfoSimple = Menu.getMusicList().get(
						Menu.getMusicList().indexOf(mp3InfoSimple) + 1);
				setContent(this.mp3InfoSimple);
				play();
				reflushView();
			}
	}

	@OnClick(R.id.PCLikeButton)
	void PCLikeButtonClick(View view) {
		if (PCLikeButton.getDrawable().getLevel() == 1) {
			PCLikeButton.setImageLevel(2);
			Toast.makeText(getActivity(), "已收藏", Toast.LENGTH_SHORT).show();
		} else {
			PCLikeButton.setImageLevel(1);
			Toast.makeText(getActivity(), "已取消", Toast.LENGTH_SHORT).show();
		}
	}

	@OnClick(R.id.PCHandButton)
	void PCHandButtonClick(View view) {
		if (PCHandButton.getDrawable().getLevel() == 1) {
			PCHandButton.setImageLevel(2);
			Toast.makeText(getActivity(), "赞", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity(), "已赞", Toast.LENGTH_SHORT).show();
		}

	}

	@OnClick(R.id.PCRepeatModeButton)
	void PCRepeatModeButtonClick(View view) {
		if (PCRepeatModeButton.getDrawable().getLevel() + 1 > 3) {
			PCRepeatModeButton.setImageLevel(1);
		} else {
			PCRepeatModeButton.setImageLevel(PCRepeatModeButton.getDrawable()
					.getLevel() + 1);
		}
	}

	private void connection() {
		Log.v("BindMusicButton", "in connection()");
		Intent i = new Intent("com.mangal.bindMusic");
		mActivity.bindService(i, connection, Context.BIND_AUTO_CREATE);

	}

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v("BindMusicButton",
					"in onServiceConnected(ComponentName name, IBinder service)");
			bindMusicPlayerService = ((BindMusicPlayerService.MyBinder) service)
					.getService();
			if (bindMusicPlayerService != null) {
				Log.v("BindMusicButton", "in if(bindMusicPlayerService!=null)");
				bindMusicPlayerService.setContent(mp3InfoSimple);
				bindMusicPlayerService.setActivity(mActivity);
			}

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			bindMusicPlayerService = null;
			Log.v("BindMusicButton",
					"in onServiceDisconnected(ComponentName name) ");

		}
	};

	public void reflushView() {
		if (mp3InfoSimple != null) {

			// 设置控制栏缩略图
			if (mp3InfoSimple.getUrl_PosterMin() != null) {
				Bitmap poster1 = Utils.getLoacalBitmap(mp3InfoSimple
						.getUrl_PosterMin());
				float scale = poster1.getHeight()
						/ getResources().getDimension(
								R.dimen.control_bar_height);
				int bmapWidth = (int) (poster1.getWidth() / scale);
				bitmapUtils.configDefaultBitmapMaxSize(
						bmapWidth,
						(int) getResources().getDimension(
								R.dimen.control_bar_height));
				bitmapUtils.display(PCPoster, mp3InfoSimple.getUrl_PosterMin());

			}
			PCMusicName.setText(mp3InfoSimple.getName());

		}
	}

	public void setContent(MP3InfoSimple mp3InfoSimple) {
		this.mp3InfoSimple = mp3InfoSimple;
		bindMusicPlayerService.setContent(mp3InfoSimple);
	}

	public void play() {
		if (bindMusicPlayerService != null) {
			PCPlayButton(getView());
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		if (bindMusicPlayerService != null) {
			bindMusicPlayerService.setCallBackSeekBarFlag(false);
		}
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		if (bindMusicPlayerService != null) {
			bindMusicPlayerService.setCallBackSeekBarFlag(true);
			bindMusicPlayerService.seekTo(seekBar.getProgress());
		}
	}

	@Override
	public void commitSeekBar(int currentProcess) {
		// TODO Auto-generated method stub
		// LogUtils.i("--------------------------currentProcess:"+currentProcess);
		PCMusicSeekBar.setProgress(currentProcess);
	}

}
