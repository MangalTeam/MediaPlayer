package com.mangal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import org.apache.commons.io.filefilter.SuffixFileFilter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Environment;
import android.view.Display;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.mangal.module.LrcInfo;

public class Utils {
	static Point size;

	static String SDCardPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
	static String MediaPlayerPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/SanseMedia";
	static String[] typeFiles;

	public static void parse(ResponseInfo<File> responseInfo) {

	}

	/**
	 * 加载本地图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到某个目录下所有以type结尾的文件
	 * 
	 * @param path
	 * @param type
	 * @return
	 */
	public static String[] getFilesfromPath(String path, String type) {
		File rootDir = new File(path);
		FilenameFilter fileFilter1 = new SuffixFileFilter(type);
		typeFiles = rootDir.list(fileFilter1);
		System.out.println("---------------------------rootDir"
				+ typeFiles.length);
		for (String string : typeFiles) {
			System.out.println(string);
		}
		return typeFiles;

	}

	public static String getSDCardPath() {
		return SDCardPath;
	}

	public static String getMediaPlayerPath() {
		return MediaPlayerPath;
	}

	/**
	 * 使用Bitmap加Matrix来缩放
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		Bitmap BitmapOrg = bitmap;
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);
		return resizedBitmap;
	}

	/**
	 * 使用BitmapFactory.Options的inSampleSize参数来缩放
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap resizeImage2(String path, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 不加载bitmap到内存中
		BitmapFactory.decodeFile(path, options);
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inSampleSize = 1;

		if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
			int sampleSize = (outWidth / width + outHeight / height) / 2;
			options.inSampleSize = sampleSize;
		}

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	// public static Bitmap scaleImageMatchHeight(String path, int height){
	// float scale = bitmap.getHeight() / height;
	// int bmapWidth = (int) (bitmap.getWidth() / scale);
	// return resizeImage2();
	// }

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 * 
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils(Context appContext) {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(appContext);
		}
		return bitmapUtils;
	}

	public static int getWindowPixelHeight(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		if (size == null) {
			size = new Point();
		}
		display.getSize(size);

		return size.y;
	}

	public static int getWindowPixelWidth(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		if (size == null) {
			size = new Point();
		}
		display.getSize(size);

		return size.x;
	}

	public static LrcInfo prepareLrc(String lrcPath) {
		LrcInfo lrcInfo = null;
		LrcProcessor lrcProcessor = new LrcProcessor();
		try {
			lrcInfo = lrcProcessor.parser(lrcPath);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("parser erro");
		}
		return lrcInfo;
	}

	public static void getNetMusic() {
		HttpUtils http = new HttpUtils();
		HttpHandler handler = http
				.download(
						"http://192.168.109.153:8080/MediaPlayer/musiclist.json",
						MediaPlayerPath, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
						true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
						new RequestCallBack<File>() {

							@Override
							public void onStart() {
								// testTextView.setText("conn...");
							}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								// testTextView.setText(current + "/" + total);
							}

							@Override
							public void onSuccess(
									ResponseInfo<File> responseInfo) {
								// testTextView.setText("downloaded:" +
								// responseInfo.result.getPath());
								parse(responseInfo);
							}

							@Override
							public void onFailure(HttpException error,
									String msg) {
								// testTextView.setText(msg);
							}
						});
	}
	public static void downloadFile(String name) {
		HttpUtils http = new HttpUtils();
		HttpHandler handler = http
				.download(
						"http://192.168.109.153:8080/MediaPlayer/SanseMedia/"+name,
						MediaPlayerPath, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
						true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
						new RequestCallBack<File>() {
							
							@Override
							public void onStart() {
								// testTextView.setText("conn...");
							}
							
							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								// testTextView.setText(current + "/" + total);
							}
							
							@Override
							public void onSuccess(
									ResponseInfo<File> responseInfo) {
								// testTextView.setText("downloaded:" +
								// responseInfo.result.getPath());
								parse(responseInfo);
							}
							
							@Override
							public void onFailure(HttpException error,
									String msg) {
								// testTextView.setText(msg);
							}
						});
	}

}
