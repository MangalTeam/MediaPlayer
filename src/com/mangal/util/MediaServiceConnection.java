package com.mangal.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.mangal.module.MP3InfoSimple;
import com.mangal.service.BindMusicPlayerService;

public class MediaServiceConnection {

	private static ServiceConnection connection;
	private static BindMusicPlayerService bindMusicPlayerService;

	public static ServiceConnection instance(final Activity activity,
			final MP3InfoSimple mp3InfoSimple, final ConnectionCallBack connectionCallBack) {

		if (connection != null) {
			return connection;
		}

		connection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				bindMusicPlayerService = null;
				Log.v("BindMusicButton",
						"in onServiceDisconnected(ComponentName name) ");
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				Log.v("BindMusicButton",
						"in onServiceConnected(ComponentName name, IBinder service)");
				bindMusicPlayerService = ((BindMusicPlayerService.MyBinder) service)
						.getService();
				if (bindMusicPlayerService != null) {
					Log.v("BindMusicButton",
							"in if(bindMusicPlayerService!=null)");
					bindMusicPlayerService.setContent(mp3InfoSimple);
					bindMusicPlayerService.setActivity(activity);
					connectionCallBack.connectioned(bindMusicPlayerService);
				}

			}
		};

		return connection;
	}

	public static BindMusicPlayerService getBindMusicPlayerService() {
		return bindMusicPlayerService;
	}

}
