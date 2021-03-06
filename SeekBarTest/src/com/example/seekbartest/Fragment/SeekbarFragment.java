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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekbarFragment extends Fragment {
	private static final String TAG = SeekbarFragment.class.getSimpleName();
	private static int MAX_SEEKBAR_VAL = 100;
	private Activity mAct;
	private String mId;
	private View mView;
	private String mText;
	private Button mNextBtn;
	private Button mResultBtn;
	private SeekBar mSeekbar;
	private int mSeekBarValue;
	private Button mBackBtn;
	private long mStartTime;
	private long mEndTime;
//	private boolean mChangeFlag;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAct = getActivity();
		mView = inflater.inflate(R.layout.frg_seekbar_layout, container, false);
		init();

		// Bundleから情報を取得する
		mSeekBarValue = getArguments().getInt("seekbarValue");
		mId = getArguments().getString("ID");

		// resultBtnの設定
		mResultBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "on click resultBtn");
				ResultDialogFragment resultDialog = new ResultDialogFragment();
				Bundle bundle = new Bundle();
				bundle.putString("ID", mId);
				resultDialog.setArguments(bundle);
				resultDialog.show(getFragmentManager(), "ResultDialogFragment");
			}
		});

		// nextBtnの設定
		mNextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "on click nextBtn");
				((SeekbarActivity) mAct).nextQuestion();
			}
		});

		// backBtnの設定
		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "on click backBtn");
				((SeekbarActivity) mAct).beforeQuestion();
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
//				mChangeFlag = true;
				mSeekBarValue = seekBar.getProgress();
				mEndTime = System.currentTimeMillis();
				int time = (int) ((mEndTime - mStartTime) / 1000);// 経過時間を秒で取得
				Log.d(TAG, "onStopTrackingTouch" + mSeekBarValue);
				((SeekbarActivity) mAct).saveData(mSeekBarValue, time);
			}
		});

		return mView;
	}

	private void init() {
		mNextBtn = (Button) mView.findViewById(R.id.seekbar_nextbtn);
		mResultBtn = (Button) mView.findViewById(R.id.seekbar_resultbtn);
		mBackBtn = (Button) mView.findViewById(R.id.seekbar_backbtn);
		mSeekbar = (SeekBar) mView.findViewById(R.id.seekBar);
		mStartTime = System.currentTimeMillis();
//		mChangeFlag=false;
	}
	
	@Override
	public void onDestroyView(){
//		if(!mChangeFlag&&mSeekBarValue==0){
//			
//			((SeekbarActivity) mAct).saveData(mSeekBarValue, 0);//未回答であるため経過時間は０
//		}
	}
}
