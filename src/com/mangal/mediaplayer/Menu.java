package com.mangal.mediaplayer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mangal.data.SampleData;
import com.mangal.module.MP3InfoSimple;
import com.meg7.widget.CircleImageView;
import com.mobeta.android.dslv.DragSortListView;

public class Menu extends Fragment {
	private static Menu menu;

	private Activity mActivity;
	private MP3Adapter adapter;
	private static ArrayList<MP3InfoSimple> infos;

	@ViewInject(R.id.profileImage)
	public static CircleImageView profileImage;
	@ViewInject(R.id.user_name)
	public static TextView userName;
	@ViewInject(R.id.user_reg_log)
	public static Button user_reg_log;
	public static TextView userInputName;
	@ViewInject(R.id.menu_mp3_list)
	ListView menu_mp3_list;

	public static Menu instance() {
		if (menu != null) {
			return menu;
		}
		menu = new Menu();
		return menu;
	}

	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			MP3InfoSimple item = adapter.getItem(from);

			adapter.remove(item);
			adapter.insert(item, to);
		}
	};

	private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which) {
			adapter.remove(adapter.getItem(which));
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu, null);
		ViewUtils.inject(this, view); // 注入view和事件
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtils.i(String.valueOf(menu_mp3_list));
		DragSortListView lv = (DragSortListView) menu_mp3_list;

		lv.setDropListener(onDrop);
		lv.setRemoveListener(onRemove);

		// lv.setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> arg0, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// ImageView imageView = (ImageView)
		// view.findViewById(R.id.click_remove);
		// imageView.setVisibility(View.VISIBLE);
		// return false;
		// }
		//
		// });

		infos = new ArrayList<MP3InfoSimple>();
		infos = SampleData.generateSampleData();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				MP3InfoSimple mp3InfoSimple = infos.get(arg2);
				// Bundle bundle = new Bundle();
				// bundle.putSerializable("mp3InfoSimple", mp3InfoSimple);
				// MainActivity.mContent.setArguments(bundle);
				PlayControlFragment playControlFragment = PlayControlFragment
						.instance();
				playControlFragment.setContent(mp3InfoSimple);
				playControlFragment.play();
				playControlFragment.reflushView();
				switchFragment(MainActivity.mContent);
			}
		});

		adapter = new MP3Adapter(infos);

		lv.setAdapter(adapter);
	}

	@OnClick(R.id.user_reg_log)
	public void showRegistration(View view) {
		switchFragment(MainActivity.mContent);
		showRegistration(mActivity);
	}

	private void showRegistration(Context context) {
		LayoutInflater inflater = LayoutInflater.from(mActivity);
		final View registration = inflater.inflate(R.layout.registration, null);
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(getResources().getString(R.string.user_reg_log));
		builder.setView(registration);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle(edtInput.getText());
				userInputName = (TextView) registration.findViewById(R.id.userInputUserName);
				userName.setText(userInputName.getText());
				user_reg_log.setVisibility(View.INVISIBLE);
				profileImage.setImageResource(R.drawable.profile_image);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("");
			}
		});
		builder.show();
	}

	// 切换Fragment视图内ring
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment);
		}
	}

	public class ViewHolder {
		@ViewInject(R.id.menu_list_mp3_name)
		TextView ListMP3Name;
		@ViewInject(R.id.menu_list_play_sign)
		ImageView playSign;
		@ViewInject(R.id.click_remove)
		public ImageView clickRemove;
	}
	
	public static ArrayList<MP3InfoSimple> getMusicList(){
		return infos;
	}

	private class MP3Adapter extends ArrayAdapter<MP3InfoSimple> {

		public MP3Adapter(List<MP3InfoSimple> infos) {
			super(mActivity, R.layout.menu_mp3_list_item,
					R.id.menu_list_mp3_name, infos);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
			final ViewHolder holder;
			if (v != convertView && v != null) {
				holder = new ViewHolder();
				ViewUtils.inject(holder, v);

				v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}

			MP3InfoSimple infoSimple = infos.get(position);
			holder.ListMP3Name.setText(infoSimple.getName());

			if (position % 2 == 0) {
				v.setBackgroundResource(R.drawable.btn_menu_mp3_list_item2);
				holder.playSign.setImageLevel(2);
			} else {
				v.setBackgroundResource(R.drawable.btn_menu_mp3_list_item);
				holder.playSign.setImageLevel(1);
			}

			v.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					// if (event.getHistoricalX() - event.getX()) {
					// ImageView imageView = (ImageView) v
					// .findViewById(R.id.click_remove);
					// imageView.setVisibility(View.VISIBLE);
					// }
					float DownX = 0;
					float DownY = 0;
					long currentMS = 0;

					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						DownX = event.getX();// float DownX
						DownY = event.getY();// float DownY
						currentMS = System.currentTimeMillis();// long currentMS
																// 获取系统时间
						break;
					case MotionEvent.ACTION_MOVE:
						float moveX = event.getX() - DownX;// X轴距离
						float moveY = event.getY() - DownY;// y轴距离
						long moveTime = System.currentTimeMillis() - currentMS;// 移动时间
						if (moveX > 1 || moveX < -1) {
							ImageView imageView = (ImageView) v
									.findViewById(R.id.click_remove);
							imageView.setVisibility(View.VISIBLE);
						}
						break;
					case MotionEvent.ACTION_UP:
						break;
					}
					return false;
				}
			});

			return v;
		}

	}
}
