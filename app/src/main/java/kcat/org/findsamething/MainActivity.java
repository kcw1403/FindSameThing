package kcat.org.findsamething;

import java.util.Random;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainActivity extends Activity implements View.OnClickListener, RewardedVideoAdListener {

	private static ScoreSQLiteOpenHelper instance;
	private Handler mHandler;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private Random rand;
	private AdView mAdView;
	private RewardedVideoAd mRewardedVideoAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mHandler = new Handler();
		rand = new Random();


		findViewById(R.id.sameImage).setOnClickListener(this);
		Button finishBtn = (Button) findViewById(R.id.finishGame);
		finishBtn.setOnClickListener(this);

		findViewById(R.id.showScore).setOnClickListener(this);
		findViewById(R.id.achievements).setOnClickListener(this);

		MobileAds.initialize(this, getString(R.string.admob_app_id));

		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);


		mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
		mRewardedVideoAd.setRewardedVideoAdListener(this);
		loadRewardedVideoAd();

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.sameImage) {
			Intent intent = new Intent(this, ImageSelectNumPlayer.class);
			startActivity(intent);
			finish();
		} else if (v.getId() == R.id.finishGame) {

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {

			}
			if (mRewardedVideoAd.isLoaded()) {
				mRewardedVideoAd.show();
			}else{

			}

			finish();
		} else if (v.getId() == R.id.showScore) {
			Intent intent = new Intent(this, ShowScore.class);
			startActivity(intent);
		} else if (v.getId() == R.id.achievements) {
				Toast.makeText(this, "준비중입니다.", Toast.LENGTH_SHORT).show();
		}


	}
	private void loadRewardedVideoAd() {
		mRewardedVideoAd.loadAd(getString(R.string.reward_ad_id),
				new AdRequest.Builder().build());
	}
	@Override
	public void onRewardedVideoAdLoaded() {

	}

	@Override
	public void onRewardedVideoAdOpened() {

	}
	@Override
	public void onRewardedVideoStarted() {

	}
	@Override
	public void onRewardedVideoAdClosed() {
		loadRewardedVideoAd();
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {

	}

	@Override
	public void onRewardedVideoAdLeftApplication() {

	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {
//		Toast.makeText(this, "Failed "+i, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoCompleted() {

	}
}
