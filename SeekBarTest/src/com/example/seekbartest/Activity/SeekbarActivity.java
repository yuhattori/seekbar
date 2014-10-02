package com.example.seekbartest.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.example.seekbartest.R;
import com.example.seekbartest.Fragment.QuestionTextFragment;
import com.example.seekbartest.Fragment.SeekbarFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SeekbarActivity extends FragmentActivity {

	private static final String TAG = SeekbarActivity.class.getSimpleName();
	private static final String QUESTION_FRAGMENT = "question_frangment";
	private static final String SEEKBAR_FRAGMENT = "seekbar_frangment";
	private static final String CSV_FILE_NAME = "questions.csv";// CSVファイル名

	private Activity mAct;
	private String mId;// 現在の回答者ID
	private LinearLayout mTextLayout;// 設問のフラグメントをのせるためのレイアウト
	private LinearLayout mSeekbarLayout;// シークバーのフラグメントをのせるためのレイアウト
	private static int mNow;// 縦横をかえられても値が残るように
	private ArrayList<String[]> mCSVdata = new ArrayList<String[]>(); // CSVファイルの二次元データ

	// CSVファイルの操作関連
	public final int ROW = 0;
	public final int QUESTION = 1;
	public final int SCALE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mAct = this;
		setContentView(R.layout.act_seekbar);// ビューを設定

		Intent intent = getIntent();
		mId = intent.getStringExtra("id");

		init();

	}

	private void init() {
		// TODO　途中からの情報なのかを判断する
		mNow = 0;

		mSeekbarLayout = (LinearLayout) mAct.findViewById(R.id.seekbar_layout);
		mTextLayout = (LinearLayout) mAct.findViewById(R.id.text_layout);
		try {
			readCSV();
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO レイアウトの大きさを修正すること
		/* レイアウトの大きさはonCreateではレンダリングが終わっていないため取得できない */

		FragmentManager manager = getSupportFragmentManager();
		// 追加や削除などを1つの処理としてまとめるためのトランザクションクラスを取得
		FragmentTransaction ft = manager.beginTransaction();

		SharedPreferences pref = mAct.getSharedPreferences(mId,
				Context.MODE_PRIVATE);
		/* QuestionTextFragmentを設定 */
		QuestionTextFragment qtf = setQuestionTextFragment();
		/* SeekbarFragmentを設定 */
		SeekbarFragment sbf = setSeekbarFragment();

		// Fragment をスタックに追加する
		ft.add(mTextLayout.getId(), qtf, QUESTION_FRAGMENT);
		ft.add(mSeekbarLayout.getId(), sbf, SEEKBAR_FRAGMENT);
		ft.commit();
	}

	/**
	 * QuestionTextFragmentを設定
	 * 
	 * @return
	 */
	private QuestionTextFragment setQuestionTextFragment() {
		QuestionTextFragment qtf = new QuestionTextFragment();
		Bundle qtBundle = new Bundle();
		qtBundle.putString("question", mCSVdata.get(mNow)[QUESTION]);
		qtf.setArguments(qtBundle);
		return qtf;
	}

	/**
	 * SeekbarFragmentを設定
	 * 
	 * @return
	 */
	private SeekbarFragment setSeekbarFragment() {
		SharedPreferences pref = mAct.getSharedPreferences(mId,
				Context.MODE_PRIVATE);
		/* SeekbarFragmentを設定 */
		SeekbarFragment sbf = new SeekbarFragment();
		Bundle sbBundle = new Bundle();
		sbBundle.putString("ID", mId);
		sbBundle.putInt("seekbarValue",
				pref.getInt("value" + String.valueOf(mNow), 0));
		sbf.setArguments(sbBundle);
		return sbf;
	}

	/**
	 * フラグメントを変更する
	 * 
	 * @param fragment
	 *            スタックへ登録するフラグメント
	 */
	public void replaceFragmentToStack(Fragment fragment) {
		// フラグメントのクラス名を取得
		String fragmentName = fragment.getClass().getSimpleName();

		// Fragmentを管理するFragmentManagerを取得
		FragmentManager manager = getSupportFragmentManager();
		// 追加や削除などを1つの処理としてまとめるためのトランザクションクラスを取得(新しく作らないと”commit already
		// called”とエラーが出る)
		FragmentTransaction ft = manager.beginTransaction();

		if (fragmentName.equals("QuestionTextFragment")) {
			/* QuestionTextFragmentの場合 */
			// Layout位置先の指定
			ft.replace(mTextLayout.getId(), fragment);
		} else if (fragmentName.equals("SeekbarFragment")) {
			/* StoryMessegeWindowFragmentの場合 */
			// Layout位置先の指定
			ft.replace(mSeekbarLayout.getId(), fragment);
		} else {
			// それ以外のフラグメントの時
			Log.e(TAG, "not exist " + fragmentName);
			Toast.makeText(mAct, "エラーが発生しました", Toast.LENGTH_SHORT).show();
			ft.commit();
			return;
		}
		// Fragmentの変化時のアニメーションを指定
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	/**
	 * 次の設問へ移動させる
	 */
	public void nextQuestion() {
		if (mNow != mCSVdata.size() - 1) {
			// 再終行以外の場合
			mNow++;

			/* QuestionTextFragmentを設定 */
			QuestionTextFragment qtf = setQuestionTextFragment();
			replaceFragmentToStack(qtf);

			/* SeekbarFragmentを設定 */
			SeekbarFragment sbf = setSeekbarFragment();
			replaceFragmentToStack(sbf);
		} else {
			// 再終行の場合
			Log.d(TAG, "last question");
		}
	}

	/**
	 * 前の設問へ移動させる
	 */
	public void beforeQuestion() {
		if (mNow != 0) {
			// 最初の行以外の場合
			mNow--;

			/* QuestionTextFragmentを設定 */
			QuestionTextFragment qtf = setQuestionTextFragment();
			replaceFragmentToStack(qtf);

			/* SeekbarFragmentを設定 */
			SeekbarFragment sbf = setSeekbarFragment();
			replaceFragmentToStack(sbf);
		} else {
			// 最初の行の場合
			Log.d(TAG, "first question");
		}
	}

	/**
	 * assetsフォルダ直下におかれたCSVファイルの読み込みを行う
	 * 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private void readCSV() throws IOException, UnsupportedEncodingException {
		AssetManager assets = getResources().getAssets();
		InputStream in = assets.open(CSV_FILE_NAME);
		BufferedReader br = new BufferedReader(
				new InputStreamReader(in, "SJIS"));// S-JISに変換
		String str;

		while ((str = br.readLine()) != null) {
			String[] array = str.split(",");
			mCSVdata.add(array);
		}
	}

	/**
	 * データを保存する
	 * 
	 * @param value
	 *            seekbarの値
	 * @param time
	 *            選択までにかかった時間
	 */
	public void saveData(int value, int time) {
		SharedPreferences pref = mAct.getSharedPreferences(mId,
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("value" + String.valueOf(mNow), value);
		editor.putInt("time" + String.valueOf(mNow), time);
		editor.putInt("length", mNow + 1);// データ数を保存
		editor.commit();

		Log.d(TAG, "saved value:" + value + "	time:" + time + "	length:" + mNow
				+ 1);
	}
}
