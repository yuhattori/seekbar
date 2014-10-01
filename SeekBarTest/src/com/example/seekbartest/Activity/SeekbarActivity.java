package com.example.seekbartest.Activity;

import com.example.seekbartest.R;
import com.example.seekbartest.Fragment.QuestionTextFragment;
import com.example.seekbartest.Fragment.SeekbarFragment;

import android.app.Activity;
import android.content.Intent;
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
	
	private Activity mAct;
	private String mId;// 現在の回答者ID
	private LinearLayout mTextLayout;
	private LinearLayout mSeekbarLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mAct = this;
		setContentView(R.layout.act_seekbar);// ビューを設定

		Intent intent = getIntent();
		String clickedBtn = intent.getStringExtra("id");

		init();
	}

	private void init() {
		mSeekbarLayout = (LinearLayout) mAct.findViewById(R.id.seekbar_layout);
		mTextLayout = (LinearLayout) mAct.findViewById(R.id.text_layout);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		//TODO レイアウトの大きさを修正
		/* レイアウトの大きさはonCreateではレンダリングが終わっていないため取得できない */
		
		FragmentManager manager = getSupportFragmentManager();
		// 追加や削除などを1つの処理としてまとめるためのトランザクションクラスを取得
		FragmentTransaction ft = manager.beginTransaction();
		
		/*QuestionTextFragmentを設定*/
		QuestionTextFragment qtf = new QuestionTextFragment();
		Bundle qtBundle = new Bundle();
		//TODO きちんと値を取得する事
		qtBundle.putString("question", "test");
		qtf.setArguments(qtBundle);
		
		/*SeekbarFragmentを設定*/
		SeekbarFragment sbf = new SeekbarFragment();
		Bundle sbBundle = new Bundle();
		//TODO きちんと値を取得する事
		sbBundle.putInt("seekbarValue", 100);
		sbf.setArguments(sbBundle);
		
		// Fragment をスタックに追加する
		ft.add(mTextLayout.getId(), qtf, QUESTION_FRAGMENT);
		ft.add(mSeekbarLayout.getId(), sbf, SEEKBAR_FRAGMENT);
		ft.commit();
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
	
	public void nextQuestion(){
		/*QuestionTextFragmentを設定*/
		QuestionTextFragment qtf = new QuestionTextFragment();
		Bundle qtBundle = new Bundle();
		//TODO きちんと値を取得する事
		qtBundle.putString("question", "testtest");
		qtf.setArguments(qtBundle);
		replaceFragmentToStack(qtf);
		
		/*SeekbarFragmentを設定*/
		SeekbarFragment sbf = new SeekbarFragment();
		Bundle sbBundle = new Bundle();
		//TODO きちんと値を取得する事
		sbBundle.putInt("seekbarValue", 0);
		sbf.setArguments(sbBundle);
		replaceFragmentToStack(sbf);
	}
}
