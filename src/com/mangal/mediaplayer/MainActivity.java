package com.mangal.mediaplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.mangal.data.SampleData;
import com.mangal.module.MP3InfoSimple;

public class MainActivity extends SlidingFragmentActivity {

	public static Fragment mContent;
	private com.mangal.mediaplayer.Menu menu;
	private int menuId = R.menu.bar;
	public static String MENU_ID = "com.mangal.mediaplayer.MENU_RESOURCE_ID";

	// @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		setTitle("��ɫ���ֺ�");
		LogUtils.customTagPrefix = "xUtilsSample"; // �������ʱ���� adb logcat ���
		LogUtils.allowI = true; // ��/�ر� LogUtils.i(...) �� adb log ���
		Intent intent = getIntent();
		if (intent != null)
			menuId = intent.getIntExtra(MENU_ID, R.menu.bar);
		initSlidingMenu(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * ��ʼ�������˵�
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// ��������״̬��Ϊ����õ�ColorFragment������ʵ����ColorFragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			// mContent = new NetMusicFragment();
			mContent = new LocalMusicFragment();
		// ��������ͼ����
		setContentView(R.layout.frame_content);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// ���û����˵���ͼ����
		menu = new com.mangal.mediaplayer.Menu();
		setBehindContentView(R.layout.frame_menu);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, menu).commit();

		// ���û����˵�������ֵ
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);

	}

	/**
	 * �л�Fragment��Ҳ���л���ͼ������
	 */
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new com.mangal.mediaplayer.Menu()).commit();
		getSlidingMenu().showContent();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(menuId, menu);
		// inflater.inflate(R.menu.bar1, menu);
		return true;
	}

	/**
	 * �˵���ť����¼���ͨ�����ActionBar��Homeͼ�갴ť���򿪻����˵�
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.menu_home:
			switchContent(new LocalMusicFragment());
			return true;
		case R.id.menu_net_music:
			switchContent(new NetMusicFragment());
			return true;
		case R.id.menu_hot:
			switchContent(new HotMusicFragment());
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * ����Fragment��״̬
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

}
