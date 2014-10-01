package com.example.seekbartest.Fragment;

import com.example.seekbartest.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;



public class SeekbarFragment extends Fragment{
	private Activity mAct;
	private View mView;
	private String mText;
	private Button mNextBtn;
	private Button mResultBtn;
	private SeekBar mSeekbar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAct=getActivity();
		mView = inflater.inflate(R.layout.frg_seekbar_layout,
				container, false);
		init();

		// Bundleから情報を取得する
		return mView;

	}

	private void init() {
		mNextBtn = (Button) mView.findViewById(R.id.seekbar_nextbtn);
		mResultBtn = (Button) mView.findViewById(R.id.seekbar_resultbtn);
		mSeekbar = (SeekBar) mView.findViewById(R.id.seekBar);
	}
}
