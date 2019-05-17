package kcat.org.findsamething;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Image1PlayerHard extends Activity implements View.OnClickListener {
	private Handler mHandler;
	private Runnable mRunnable;
	private Typeface mTypeface;
	private SQLiteDatabase db;
	private ScoreSQLiteOpenHelper helper;
	private ScoreDao scoreDao;
	private int time = 0;
	private Random rand;
	private Button replayBtn;
	private Button exitBtn;
	private int randomNum;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private Runnable timeRun;
	private String friendAnimal;
	private int openedImage = 0;
	private int openedId = 0;
	private int score = 0;
	private int finishCheck = 0;
	private int combo = 0;
	private int clickCount = 0;

	private int[] resIds = { R.drawable.apple, R.drawable.banana, R.drawable.cherry, R.drawable.grape, R.drawable.kiwi,
			R.drawable.pineapple, R.drawable.zazang, R.drawable.autocycle, R.drawable.smile, R.drawable.fan,
			R.drawable.light, R.drawable.mouse, R.drawable.note1, R.drawable.pingpongracket, R.drawable.telephone,
			R.drawable.apple, R.drawable.banana, R.drawable.cherry, R.drawable.grape, R.drawable.kiwi,
			R.drawable.pineapple, R.drawable.zazang, R.drawable.autocycle, R.drawable.smile, R.drawable.fan,
			R.drawable.light, R.drawable.mouse, R.drawable.note1, R.drawable.pingpongracket, R.drawable.telephone };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		scoreDao = new ScoreDao(getApplicationContext());

		prefs = getSharedPreferences("animal", MODE_PRIVATE);
		editor = prefs.edit();
		editor.putString("imageValue", "");

		friendAnimal = prefs.getString("imageValue", "");
		rand = new Random();
		super.onCreate(savedInstanceState);

		while (true) {
			randomNum = rand.nextInt(21);

			if (friendAnimal.equals("")) {
				break;
			}
			boolean same = false;
			int check = 0;
			StringTokenizer stok = new StringTokenizer(friendAnimal, ",");
			if (stok.countTokens() == 21) {
				randomNum=22;
				break;
			}
			while (stok.hasMoreTokens()) {
				if (String.valueOf(randomNum).equals(stok.nextToken())) {
					same = true;
				}
			}

			if (same == false) {
				break;
			}
		}

		LayoutInflater inflater = getLayoutInflater();
		final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.show_message,
				(LinearLayout) findViewById(R.id.llMessage));
		final TextView message = (TextView) layout.findViewById(R.id.message);
		message.setTypeface(mTypeface);

		Bitmap bit = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
		Canvas canvas = new Canvas(bit);

		int x = randomNum % 7;
		int y = randomNum / 7;

		Rect src = new Rect(x * 100, y * 100, 100 * (x + 1), (y + 1) * 100);
		Rect dst = new Rect(0, 0, 200, 200);
		if(randomNum==22){
			src = new Rect(0,0,700,300);
			dst = new Rect(0, 0, 200, 200);
		}
		
		Paint paint = new Paint();
		canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.animal),
				700, 300, true), src, dst, paint);
	
		setContentView(R.layout.image_1player_hard);
		mTypeface = Typeface.createFromAsset(getAssets(), "ARCADE_0.TTF");



		TextView scoreTv = (TextView) findViewById(R.id.scoreTv);
		TextView scoreTvValue = (TextView) findViewById(R.id.score);

		scoreTv.setTypeface(mTypeface);
		scoreTvValue.setTypeface(mTypeface);
		ImageView image = (ImageView) findViewById(R.id.imageView);
		image.setImageBitmap(bit);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 15; i++) {
			int id = getResources().getIdentifier("card_image" + (i + 1), "drawable", this.getPackageName());

			list.add(id);
			list.add(id);
		}
		Collections.shuffle(list);
		for (int i = 0; i < list.size(); i++) {
			resIds[i] = list.get(i);
		}

		for (int i = 0; i < 30; i++) {
			int id = getResources().getIdentifier("imageBtn" + (i + 1), "id", this.getPackageName());
			ImageView imageView = (ImageView) findViewById(id);
			imageView.setImageResource(resIds[i]);

		}

		final TextView ready = (TextView) findViewById(R.id.readyText);
		mRunnable = new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 30; i++) {
					int id = getResources().getIdentifier("imageBtn" + (i + 1), "id",
							Image1PlayerHard.this.getPackageName());
					ImageView imageView = (ImageView) findViewById(id);
					imageView.setImageResource(R.drawable.card_cover);

					imageView.setOnClickListener(Image1PlayerHard.this);
					ready.setVisibility(TextView.GONE);

				}
				message.setText("START");
				final Toast t = Toast.makeText(Image1PlayerHard.this, "", Toast.LENGTH_SHORT);
				t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				t.setView(layout);
				t.show();

				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						t.cancel();
						mHandler.postDelayed(timeRun, 1000);
						for (int i = 0; i < 30; i++) {
							int id = getResources().getIdentifier("imageBtn" + (i + 1), "id",
									Image1PlayerHard.this.getPackageName());
							ImageView imageView = (ImageView) findViewById(id);
							imageView.setOnClickListener(Image1PlayerHard.this);
						}
					}
				}, 100);
			}
		};

		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, 3000);

		replayBtn = (Button) findViewById(R.id.replay);
		replayBtn.bringToFront();
		exitBtn = (Button) findViewById(R.id.exit);
		exitBtn.bringToFront();

		replayBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);

		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, 3000);

		ToggleButton callMenu = (ToggleButton) findViewById(R.id.callMenu);
		callMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub

				if (isChecked == true) {
					replayBtn.setVisibility(Button.VISIBLE);
					exitBtn.setVisibility(Button.VISIBLE);
				} else {
					replayBtn.setVisibility(Button.INVISIBLE);
					exitBtn.setVisibility(Button.INVISIBLE);
				}
			}
		});

		Random rand = new Random();
		randomNum = rand.nextInt(16) + 1;
		int backId = getResources().getIdentifier("back_image" + (randomNum + 1), "drawable", this.getPackageName());

		ImageView iv = (ImageView) findViewById(R.id.imageView);
		iv.setImageResource(backId);

		final TextView showTime = (TextView) findViewById(R.id.time);
		showTime.setTypeface(mTypeface);
		timeRun = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (finishCheck != 10) {
					time++;
					showTime.setText("" + time);
					mHandler.postDelayed(timeRun, 1000);
				}
			}
		};

		TextView timeTv = (TextView) findViewById(R.id.timeTv);
		timeTv.setTypeface(mTypeface);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.exit) {
			finish();
		} else if (v.getId() == R.id.replay) {
			Intent intent = new Intent(Image1PlayerHard.this, Image1PlayerHard.class);
			startActivity(intent);
			finish();
		} else {

			LayoutInflater inflater = getLayoutInflater();
			final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.show_message,
					(LinearLayout) findViewById(R.id.llMessage));
			final TextView message = (TextView) layout.findViewById(R.id.message);
			message.setTypeface(mTypeface);
			final ImageView image = (ImageView) findViewById(v.getId());
			for (int i = 0; i < 30; i++) {
				int id = getResources().getIdentifier("imageBtn" + (i + 1), "id", this.getPackageName());
				if (v.getId() == id) {
					image.setImageResource(resIds[i]);

					if (openedImage != 0 && openedId != v.getId()) {
						clickCount++;
						if (openedImage == resIds[i]) {
							final ImageView opened = (ImageView) findViewById(openedId);
							mRunnable = new Runnable() {
								@Override
								public void run() {
									opened.setVisibility(ImageView.INVISIBLE);
									image.setVisibility(ImageView.INVISIBLE);
								}
							};

							if (combo == 0) {
								message.setText("Correct");
								combo++;
							} else {
								message.setText("Combo " + (combo + 1));
								combo++;
							}

							mHandler = new Handler();
							mHandler.postDelayed(mRunnable, 500);

							final Toast t = Toast.makeText(this, "", Toast.LENGTH_SHORT);
							t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
							t.setView(layout);
							t.show();

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									t.cancel();
								}
							}, 300);
							score += (100 * combo);
							finishCheck++;
							openedImage = 0;
							openedId = 0;
							TextView textView = (TextView) findViewById(R.id.score);
							textView.setText(String.valueOf(score));
							if (finishCheck == 15) {
								if (randomNum != 22) {
									editor.putString("imageValue", friendAnimal + randomNum + ",");
									editor.commit();
								}
								
								

								LinearLayout dialogLayout = (LinearLayout) inflater.inflate(R.layout.result_dialog,
										(LinearLayout) this.findViewById(R.id.resultDialog));

								final TextView bestScore = (TextView) dialogLayout.findViewById(R.id.bestScore);
								bestScore.setText(String.valueOf(scoreDao.getBestScoreHard()));
								final TextView countFinish = (TextView) dialogLayout.findViewById(R.id.countFinish);
								int clickCountScore;
								clickCountScore = (clickCount - 15) * -50;
								countFinish.setText(String.valueOf(clickCount));
								final TextView myScore = (TextView) dialogLayout.findViewById(R.id.myScore);
								myScore.setText(String.valueOf(score));

								final int totalScore = clickCountScore + score;
								AlertDialog.Builder builder = new AlertDialog.Builder(this);

								builder.setView(dialogLayout);
								builder.setPositiveButton("Replay", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent intent = new Intent(Image1PlayerHard.this, Image1PlayerHard.class);
										startActivity(intent);
										scoreDao.insertHard(score, new Date().toString());
										finish();
									}
								}); // 긍정
								builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										scoreDao.insertHard(score, new Date().toString());
										finish();
									}
								}); // 부정

								builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {

										scoreDao.insertHard(score, new Date().toString());

										dialog.dismiss();

									}
								}); // 부정

								Dialog dialogBuilder = builder.create();
								// dialogBuilder.getWindow().setGravity(Gravity.CENTER);
								WindowManager.LayoutParams params = new WindowManager.LayoutParams();
								params.copyFrom(dialogBuilder.getWindow().getAttributes());
								// params.copyFrom(dialogBuilder.get)
								params.width = WindowManager.LayoutParams.MATCH_PARENT;
								params.height = WindowManager.LayoutParams.WRAP_CONTENT;
								// params.gravity= Gravity.CENTER;

								dialogBuilder.show();
								Window window = dialogBuilder.getWindow();
								window.setAttributes(params);
								window.setGravity(Gravity.CENTER);

							}
						} else {
							final ImageView opened = (ImageView) findViewById(openedId);

							mRunnable = new Runnable() {

	@Override
								public void run() {
									opened.setImageResource(R.drawable.card_cover);
									image.setImageResource(R.drawable.card_cover);
								}
							};
							mHandler = new Handler();
							mHandler.postDelayed(mRunnable, 500);
							message.setText("Wrong");
							combo = 0;
							score -= 50;
							TextView textView = (TextView) findViewById(R.id.score);
							textView.setText(String.valueOf(score));
							final Toast t = Toast.makeText(this, "", Toast.LENGTH_SHORT);
							t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
							t.setView(layout);
							t.show();

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									t.cancel();
								}
							}, 300);
							openedImage = 0;
							openedId = 0;
						}
}else{openedImage=resIds[i];openedId=id;}}}}}}
