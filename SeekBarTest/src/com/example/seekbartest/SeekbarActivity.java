package com.example.seekbartest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;




public class SeekbarActivity extends Activity {

	private static final String TAG = SeekbarActivity.class.getSimpleName();
	private Activity mAct;
	private String mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mAct = this;
		setContentView(R.layout.activity_seekbar);// ビューを設定

		Intent intent = getIntent();
		String clickedBtn = intent.getStringExtra("id");

		init();
	}

	private void init() {

	}
}
