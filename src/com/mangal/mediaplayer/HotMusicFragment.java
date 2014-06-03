package com.mangal.mediaplayer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mangal.adapter.HotMusicAdapter;
import com.mangal.adapter.NetMusicAdapter;
import com.mangal.data.SampleData;
import com.mangal.module.MP3InfoSimple;

public class HotMusicFragment extends Fragment implements
		AbsListView.OnScrollListener, AbsListView.OnItemClickListener {

	@ViewInject(R.id.grid_view)
	private StaggeredGridView mGridView;
	private boolean mHasRequestedMore;
	private HotMusicAdapter mAdapter;

	private ArrayList<MP3InfoSimple> mData;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
   
	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_sgv_hot, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState == null) {
			final LayoutInflater layoutInflater = getActivity()
					.getLayoutInflater();
		}

		if (mAdapter == null) {
			mAdapter = new HotMusicAdapter(getActivity());
		}

		if (mData == null) {
			mData = SampleData.generateSampleData();
//			for (MP3InfoSimple mp3InfoSimple : mData) {
//				LogUtils.i("----------" + mp3InfoSimple.getName());
//				LogUtils.i("------------------"
//						+ mp3InfoSimple.getUrl_PosterMin());
//				LogUtils.i("----------------------------"
//						+ mp3InfoSimple.getUrl_Lyric());
//			}
		}

		// 滑动时加载图片，快速滑动时不加载图片
		mGridView.setOnScrollListener(new PauseOnScrollListener(mAdapter
				.getBitmapUtils(), false, true));

		mAdapter.addMp3InfoSimples(mData);

		mGridView.setAdapter(mAdapter);
		mGridView.setOnScrollListener(this);
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void onScrollStateChanged(final AbsListView view,
			final int scrollState) {
		// Log.d(TAG, "onScrollStateChanged:" + scrollState);
	}

	@Override
	public void onScroll(final AbsListView view, final int firstVisibleItem,
			final int visibleItemCount, final int totalItemCount) {
		// Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem
		// + " visibleItemCount:" + visibleItemCount + " totalItemCount:"
		// + totalItemCount);
		// our handling
		if (!mHasRequestedMore) {
			int lastInScreen = firstVisibleItem + visibleItemCount;
			if (lastInScreen >= totalItemCount) {
				// Log.d(TAG, "onScroll lastInScreen - so load more");
				mHasRequestedMore = true;
				onLoadMoreItems();
			}
		}
	}

	private void onLoadMoreItems() {
		// final ArrayList<MP3InfoSimple> sampleData =
		// SampleData.generateSampleData();
		// for (String data : sampleData) {
		// mAdapter.add(data);
		// }
		// stash all the data in our backing store
		// mAdapter.addMp3InfoSimples(sampleData);
		// notify the adapter that we can update now
		// mAdapter.notifyDataSetChanged();
		// mHasRequestedMore = false;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Intent intent = new Intent(getActivity(), MusicDetails.class);
		intent.putExtra("mp3_id", position);
		startActivity(intent);

	}
}