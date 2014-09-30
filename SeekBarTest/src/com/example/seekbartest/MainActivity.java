package com.example.seekbartest;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private Button mStartBtn;
	private Button mResultBtn;
	private EditText mIdEditText;
	private Activity mAct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mAct = this;
		setContentView(R.layout.activity_main);
		init();
	}

	@Override
	public void onStart() {
		super.onStart();
		mStartBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "on click startBtn");
				String id = getTextFromEditText(mIdEditText);
				if (!id.equals("")) {
					changeActivity("test");
				} else {
					Toast.makeText(mAct, "please edit id", Toast.LENGTH_SHORT)
							.show();
				}
			}

			public String getTextFromEditText(EditText edit) {
				SpannableStringBuilder sb = (SpannableStringBuilder) edit
						.getText();
				return sb.toString();
			}
		});

	}

	private void init() {
		mStartBtn = (Button) this.findViewById(R.id.top_startbtn);
		mResultBtn = (Button) this.findViewById(R.id.top_resultbtn);
		mIdEditText = (EditText) this.findViewById(R.id.top_edittext);
	}

	public void changeActivity(String id) {
		Intent intent = new Intent();
		intent.putExtra("id", id);
		intent.setClass(MainActivity.this, SeekbarActivity.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// バックボタンが押されたとき
			this.finish();
			return true;
		default:
			return false;
		}
	}
}
