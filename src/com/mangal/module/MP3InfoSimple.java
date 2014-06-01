package com.mangal.module;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author mac
 *锟斤拷锟�MP3InfoComplex�����х��name
 */
public class MP3InfoSimple implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id_MP3InfoSimple;
	String name;
	String url_MP3;
	String url_Lyric;
	String url_PosterMin;
	
	public int getId_MP3InfoSimple() {
		return id_MP3InfoSimple;
	}
	public void setId_MP3InfoSimple(int id_MP3InfoSimple) {
		this.id_MP3InfoSimple = id_MP3InfoSimple;
	}
	public String getName() {
		return name;
	}	
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl_MP3() {
		return url_MP3;
	}
	public void setUrl_MP3(String url_MP3) {
		this.url_MP3 = url_MP3;
	}
	public String getUrl_Lyric() {
		return url_Lyric;
	}
	public void setUrl_Lyric(String url_Lyric) {
		this.url_Lyric = url_Lyric;
	}
	public String getUrl_PosterMin() {
		return url_PosterMin;
	}
	public void setUrl_PosterMin(String url_PosterMin) {
		this.url_PosterMin = url_PosterMin;
	}
	
}
