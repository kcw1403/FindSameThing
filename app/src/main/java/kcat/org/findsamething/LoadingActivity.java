package kcat.org.findsamething;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_loading);
		Handler hd = new Handler();
		hd.postDelayed(new splashhandler(), 2000); // 3초 후에 hd Handler 실행
	}

	private class splashhandler implements Runnable {
		public void run() {
			startActivity(new Intent(getApplication(), MainActivity.class)); // 로딩이
																				// 끝난후
																				// 이동할
																				// Activity
			LoadingActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
		}
	}
}
