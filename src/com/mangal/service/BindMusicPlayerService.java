package com.mangal.service;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.mangal.mediaplayer.R;
import com.mangal.module.LrcInfo;
import com.mangal.module.MP3InfoSimple;
import com.mangal.util.UpdateLrcCallBack;
import com.mangal.util.UpdateSeekBarCallBack;
import com.mangal.util.Utils;

public class BindMusicPlayerService extends Service {
	private MediaPlayer mediaPlayer;
	private MP3InfoSimple mp3InfoSimple;
	private String playingName;

	Activity mActivity;
	private Handler handler = new Handler();
	private UpdateSeekBarCallBack updateSeekBar;
	private int MaxSeekBar = 0;
	private UpdateTimeCallback updateTimeCallback = null;
	private long index = 0;
	private long currentTimeMill = 0;
	private String message = null;

	private boolean isCallBackSeekBar = false;
	private boolean isCallBackLrc = false;

	private final IBinder binder = new MyBinder();

	public class MyBinder extends Binder {

		public BindMusicPlayerService getService() {
			Log.v("BindMusicPlayerService",
					"in  BindMusicPlayerService getService()");
			return BindMusicPlayerService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.v("BindMusicPlayerService", "in  onBind(Intent intent)");
		return binder;
	}

	@Override
	protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
		Log.v("BindMusicPlayerService",
				"in dump(FileDescriptor fd, PrintWriter writer, String[] args)");
		super.dump(fd, writer, args);
	}

	@Override
	protected void finalize() throws Throwable {
		Log.v("BindMusicPlayerService", "in finalize() ");
		super.finalize();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.v("BindMusicPlayerService",
				"in onConfigurationChanged(Configuration newConfig) ");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		Log.v("BindMusicPlayerService", "in onLowMemory() ");
		super.onLowMemory();
	}

	@Override
	public void onRebind(Intent intent) {
		Log.v("BindMusicPlayerService", "in onRebind(Intent intent) ");
		super.onRebind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.v("BindMusicPlayerService",
				"in onStart(Intent intent, int startId) ");
		super.onStartCommand(intent, 0, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.v("BindMusicPlayerService", "in onUnbind(Intent intent) ");
		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
		Log.v("BindMusicPlayerService", "in   onCreate()");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.v("BindMusicPlayerService", "in   onDestroy()");
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	public void setContent(MP3InfoSimple mp3InfoSimple) {
		this.mp3InfoSimple = mp3InfoSimple;
	}

	public void play() {
		Log.v("BindMusicPlayerService", "in  play()");

		if (mp3InfoSimple != null) {
			if (mediaPlayer == null) {
				play(Uri.parse(mp3InfoSimple.getUrl_MP3()));
				playingName = mp3InfoSimple.getName();

			} else {
				if (playingName != mp3InfoSimple.getName()) {
					mediaPlayer.stop();
					play(Uri.parse(mp3InfoSimple.getUrl_MP3()));
					playingName = mp3InfoSimple.getName();
				}
			}
		} else {
			play(R.raw.tmp);
			playingName = "";
		}
		if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
			updateTimeCallback = new UpdateTimeCallback(null, updateSeekBar);
			handler.post(updateTimeCallback);
		}
	}

	private void play(Uri mediaPath) {
		// TODO Auto-generated method stub
		if (mediaPath != null) {
			Log.v("BindMusicPlayerService", "in  play()");
			mediaPlayer = MediaPlayer.create(this, mediaPath);
			mediaPlayer.setLooping(false);
		}
	}

	private void play(int resid) {
		// TODO Auto-generated method stub
		if (resid != 0) {
			Log.v("BindMusicPlayerService", "in  play()");
			mediaPlayer = MediaPlayer.create(this, resid);
			mediaPlayer.setLooping(false);
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			}
		}
	}

	public void puase() {
		Log.v("BindMusicPlayerService", "in puase()");
		if (mediaPlayer.isPlaying() && mediaPlayer != null) {
			mediaPlayer.pause();
			handler.removeCallbacks(updateTimeCallback);
		}
	}

	public void stop() {
		Log.v("BindMusicPlayerService", "in stop()");
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			handler.removeCallbacks(updateTimeCallback);
			try {
				mediaPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void seekTo(int msce) {
		mediaPlayer.seekTo(mediaPlayer.getDuration() * msce / MaxSeekBar);
	}

	public boolean isPlaying() {
		if (mediaPlayer != null) {
			return mediaPlayer.isPlaying();
		}
		return false;
	}

	public void setUpdateSeekBar(UpdateSeekBarCallBack updateSeekBar) {
		this.updateSeekBar = updateSeekBar;
		isCallBackSeekBar = true;
	}

	public void setActivity(Activity activity) {
		this.mActivity = activity;
	}

	/**
	 * 显示进度条进度标记
	 * 
	 * @param flag
	 */
	public void setCallBackSeekBarFlag(boolean flag) {
		isCallBackSeekBar = flag;
	}

	/**
	 * 显示歌词标记
	 * 
	 * @param flag
	 */
	public void setCallBackLrcFlag(boolean flag) {
		isCallBackLrc = flag;
	}

	class UpdateTimeCallback implements Runnable {

		LrcInfo lrcInfo = null;
		UpdateSeekBarCallBack updateSeekBar;
		UpdateLrcCallBack updateLrc;

		private UpdateTimeCallback(UpdateLrcCallBack updateLrc,
				UpdateSeekBarCallBack updateSeekBar) {
			this.updateSeekBar = updateSeekBar;
			this.updateLrc = updateLrc;
		}

		// UpdateTimeCallback createUpdateTimeCallback(){
		// if(updateTimeCallback != null){
		// return updateTimeCallback;
		// }
		// updateTimeCallback = new UpdateTimeCallback();
		// return updateTimeCallback;
		// }

		public UpdateTimeCallback(LrcInfo lrcInfo) {
			this.lrcInfo = lrcInfo;
		}

		public void run() {
			// TODO Auto-generated method stub
			if (MaxSeekBar == 0) {
				MaxSeekBar = Utils.getWindowPixelWidth(mActivity);
			}

			if (mediaPlayer.isPlaying()) {
				// 控制显示歌词
				if (isCallBackLrc) {
					index = mediaPlayer.getCurrentPosition();
					if (lrcInfo != null) {
						System.out.println("------------lrc-" + index);
						for (int i = 0; i < lrcInfo.getTimeMillsSize() - 1; i++) {
							if (index >= lrcInfo.getTimeMills(i)) {
								if (index < lrcInfo.getTimeMills(i + 1)) {
									// message = lrcInfo.getMessages(i);
									// Intent lrcIntent = new Intent();
									// lrcIntent.setAction(AppConstant.LRC_MESSAGE_ACTION);
									// lrcIntent.putExtra("lrcMessage",
									// message);
									// sendBroadcast(lrcIntent);
									// updateLrc;
								}

							}
						}
					}
				}

				// 控制显示进度条进度
				if (isCallBackSeekBar) {
					updateSeekBar.commitSeekBar(mediaPlayer
							.getCurrentPosition()
							* MaxSeekBar
							/ mediaPlayer.getDuration());
					currentTimeMill = currentTimeMill + 5;
				}

			}

			handler.postDelayed(updateTimeCallback, 5);

		}
	}

}
