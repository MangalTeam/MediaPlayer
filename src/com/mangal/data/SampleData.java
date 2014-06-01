package com.mangal.data;

import java.util.ArrayList;

import com.mangal.module.MP3InfoSimple;
import com.mangal.util.Utils;

public class SampleData {

	private static String[] jpgFiles;
	private static String[] mp3Files;
	private static String[] lrcFiles;
	private static MP3InfoSimple mp3InfoSimple;

	public static ArrayList<MP3InfoSimple> generateSampleData() {
		mp3Files = Utils.getFilesfromPath(Utils.getMediaPlayerPath(), ".mp3");
		jpgFiles = Utils.getFilesfromPath(Utils.getMediaPlayerPath(), ".jpg");
		lrcFiles = Utils.getFilesfromPath(Utils.getMediaPlayerPath(), ".lrc");
		final ArrayList<MP3InfoSimple> data = new ArrayList<MP3InfoSimple>(
				mp3Files.length);

		for (int i = 0; i < mp3Files.length; i++) {
			mp3InfoSimple = new MP3InfoSimple();
			mp3InfoSimple.setName(mp3Files[i].replace(".mp3", ""));
			mp3InfoSimple.setUrl_MP3(Utils.getMediaPlayerPath() + "/"
					+ mp3Files[i]);
			for (int j = 0; j < jpgFiles.length; j++) {
				String posterName = mp3Files[i].replace(".mp3", ".jpg");
				if (jpgFiles[j].contains(posterName)) {
					mp3InfoSimple.setUrl_PosterMin(Utils.getMediaPlayerPath()
							+ "/" + posterName);
				}
			}
			for (int k = 0; k < lrcFiles.length; k++) {
				String lrcName = mp3Files[i].replace(".mp3", ".lrc");
				if (lrcFiles[k].contains(lrcName)) {
					mp3InfoSimple.setUrl_Lyric(Utils.getMediaPlayerPath() + "/"
							+ lrcName);
				}
			}
			data.add(mp3InfoSimple);
		}

		return data;
	}

}
