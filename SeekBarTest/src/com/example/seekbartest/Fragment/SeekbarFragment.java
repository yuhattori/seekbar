package com.example.seekbartest.Fragment;

import com.example.seekbartest.R;
import com.example.seekbartest.Activity.SeekbarActivity;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekbarFragment extends Fragment {
	private static final String TAG = SeekbarFragment.class.getSimpleName();
	private static int MAX_SEEKBAR_VAL = 100;
	private Activity mAct;
	private View mView;
	private String mText;
	private Button mNextBtn;
	private Button mResultBtn;
	private SeekBar mSeekbar;
	private int mSeekBarValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAct = getActivity();
		mView = inflater.inflate(R.layout.frg_seekbar_layout, container, false);
		init();

		// Bundleから情報を取得する
		mSeekBarValue = getArguments().getInt("seekbarValue");
		
		//nextBtnの設定
		mNextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "on click startBtn");
				((SeekbarActivity) getActivity()).nextQuestion();
			}
		});

		// SeekBarの設定
		mSeekbar.setProgress(mSeekBarValue);
		mSeekbar.setMax(MAX_SEEKBAR_VAL);
		mSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// ツマミをドラッグしたときに呼ばれる
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// ツマミに触れたときに呼ばれる
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				// ツマミを離したときに呼ばれる
				Log.d(TAG, "onStopTrackingTouch" + seekBar.getProgress());
			}
		});

		return mView;
	}

	private void init() {
		mNextBtn = (Button) mView.findViewById(R.id.seekbar_nextbtn);
		mResultBtn = (Button) mView.findViewById(R.id.seekbar_resultbtn);
		mSeekbar = (SeekBar) mView.findViewById(R.id.seekBar);
	}
}
