package com.example.seekbartest.Fragment;

import java.util.List;
import java.util.Map;

import com.example.seekbartest.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class ResultDialogFragment extends DialogFragment {
	private static final String TAG = ResultDialogFragment.class
			.getSimpleName();
	private String mId;
	private Dialog mDialog;

	private Button mExitBtn;
	private Button mBackBtn;

	public Dialog onCreateDialog(Bundle bundle) {
		mDialog = new Dialog(getActivity());
		mId = getArguments().getString("ID");

		mDialog.setContentView(R.layout.frg_result_dialog_layout);

		init();

		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "on click");

			}
		});

		mExitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "on click");

			}
		});

		return mDialog;
	}

	private void init() {
		mExitBtn = (Button) mDialog.findViewById(R.id.result_exitbtn);
		mBackBtn = (Button) mDialog.findViewById(R.id.result_backbtn);
	}

	private class MySimpleAdapter extends SimpleAdapter {

		public MySimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);

		}
	}
}
