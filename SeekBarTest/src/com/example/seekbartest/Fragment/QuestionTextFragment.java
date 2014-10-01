package com.example.seekbartest.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seekbartest.R;

public class QuestionTextFragment extends Fragment {
	private static final String TAG = QuestionTextFragment.class
			.getSimpleName();
	private Activity mAct;
	private View mView;
	private String mText;
	private TextView mQuestion;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAct=getActivity();
		mView = inflater.inflate(R.layout.frg_question_text,
				container, false);
		init();
		// Bundleから情報を取得する
		mText=getArguments().getString("question");
		
		mQuestion.setText(mText);
		
		return mView;
	}

	private void init() {
		mQuestion = (TextView) mView.findViewById(R.id.question);
	}

}
