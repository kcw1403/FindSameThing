package kcat.org.findsamething;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Image2Player extends Activity implements View.OnClickListener {
	private Handler mHandler;
	private Runnable mRunnable;
	private Typeface mTypeface;
	private Animation flash;
	private TextView p1Tv;
	private TextView p2Tv;
	
	
	private int[] resIds = { R.drawable.apple, R.drawable.banana,
			R.drawable.cherry, R.drawable.grape, R.drawable.kiwi,
			R.drawable.pineapple, R.drawable.zazang, R.drawable.fan,
			R.drawable.mouse, R.drawable.note1, R.drawable.apple,
			R.drawable.banana, R.drawable.cherry, R.drawable.grape,
			R.drawable.kiwi, R.drawable.pineapple, R.drawable.zazang,
			R.drawable.fan, R.drawable.mouse, R.drawable.note1 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_2player);


		p1Tv=(TextView)findViewById(R.id.p1Tv);
		p2Tv=(TextView)findViewById(R.id.p2Tv);
		
		flash=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flash);
		
		
		p1Tv.setAnimation(flash);
		
		LayoutInflater inflater = getLayoutInflater();
		final LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.show_message,
				(LinearLayout) findViewById(R.id.llMessage));
		final TextView message = (TextView) layout.findViewById(R.id.message);
		message.setTypeface(mTypeface);

		mTypeface = Typeface.createFromAsset(getAssets(), "SAM_5C_27TRG_.TTF");

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			int id = getResources().getIdentifier("card_image" + (i + 1),
					"drawable", this.getPackageName());
			list.add(id);
			list.add(id);
		}
		Collections.shuffle(list);
		for (int i = 0; i < list.size(); i++) {
			resIds[i] = list.get(i);
		}

		for (int i = 0; i < 20; i++) {
			int id = getResources().getIdentifier("imageBtn" + (i + 1), "id",
					this.getPackageName());
			ImageView imageView = (ImageView) findViewById(id);
			imageView.setImageResource(resIds[i]);

		}

		// final TextView ready = (TextView) findViewById(R.id.readyText);
		final Toast t = Toast.makeText(Image2Player.this, "",
				Toast.LENGTH_SHORT);
		t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		message.setText("Ready");
		t.setView(layout);
		t.show();
		
		mRunnable = new Runnable() {

			@Override
			public void run() {
				t.cancel();
				for (int i = 0; i < 20; i++) {
					int id = getResources().getIdentifier("imageBtn" + (i + 1),
							"id", Image2Player.this.getPackageName());
					ImageView imageView = (ImageView) findViewById(id);
					imageView.setImageResource(R.drawable.cover);

					imageView.setOnClickListener(Image2Player.this);
					// ready.setVisibility(TextView.GONE);
					message.setText("player1 \nStart");

					t.show();

					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							t.cancel();
						}
					}, 100);

				}

			}
		};

		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, 2000);

	}

	private int openedImage = 0;
	private int openedId = 0;
	private int score = 0;
	private int p1Score = 0;
	private int p2Score = 0;

	private int finishCheck = 0;
	private int combo = 0;
	private int turnCheck = 0;
	private int clickCount = 0;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = getLayoutInflater();
		final LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.show_message,
				(LinearLayout) findViewById(R.id.llMessage));
		final TextView message = (TextView) layout.findViewById(R.id.message);
		message.setTypeface(mTypeface);
		final ImageView image = (ImageView) findViewById(v.getId());
		for (int i = 0; i < 20; i++) {
			int id = getResources().getIdentifier("imageBtn" + (i + 1), "id",
					this.getPackageName());
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

					
							message.setText("One More Time");
					
						mHandler = new Handler();
						mHandler.postDelayed(mRunnable, 500);

						final Toast t = Toast.makeText(this, "",
								Toast.LENGTH_SHORT);
						t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						t.setView(layout);
						t.show();

						mHandler.postDelayed(new Runnable() {

							@Override
							public void run() {
								t.cancel();
							}
						}, 300);
					//	score += combo;
						
						finishCheck++;
						openedImage = 0;
						openedId = 0;
						TextView p1ScoreTv = (TextView) findViewById(R.id.p1Score);
						TextView p2ScoreTv = (TextView) findViewById(R.id.p2Score);

						if (turnCheck == 0) {
							p1Score+=1;
							p1ScoreTv.setText(String.valueOf(p1Score));
						} else {
							p2Score+=1;
							p2ScoreTv.setText(String.valueOf(p2Score));
						}
						if (finishCheck == 10) {

							LinearLayout dialogLayout = (LinearLayout) inflater
									.inflate(
											R.layout.result_dialog_two,
											(LinearLayout) this
													.findViewById(R.id.resultDialog));


							AlertDialog.Builder builder = new AlertDialog.Builder(
									this);

							ImageView result = (ImageView) dialogLayout.findViewById(R.id.twoResult);
							if (p1Score > p2Score) {
								result.setImageResource(R.drawable.p1win);
							} else if(p1Score< p2Score) {
								result.setImageResource(R.drawable.p2win);
							}else{
								result.setImageResource(R.drawable.draw);
							}
							
							builder.setView(dialogLayout);
							builder.setPositiveButton("다시하기",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													Image2Player.this,
													Image2Player.class);
											startActivity(intent);
											finish();
										}
									}); // 긍정
							builder.setNegativeButton("끝내기",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											finish();
										}
									}); // 부정

							
							Dialog dialogBuilder = builder.create();
							//dialogBuilder.getWindow().setGravity(Gravity.CENTER);
							WindowManager.LayoutParams params = new WindowManager.LayoutParams();
							params.copyFrom(dialogBuilder.getWindow()
									.getAttributes());
							// params.copyFrom(dialogBuilder.get)
							params.width = WindowManager.LayoutParams.MATCH_PARENT;
							params.height = WindowManager.LayoutParams.WRAP_CONTENT;
							//params.gravity= Gravity.CENTER;
						
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
								opened.setImageResource(R.drawable.cover);
								image.setImageResource(R.drawable.cover);
							}
						};
						mHandler = new Handler();
						mHandler.postDelayed(mRunnable, 500);
						message.setText("Player Change");
						if (turnCheck == 1) {
							turnCheck = 0;
							p2Tv.clearAnimation();
							p1Tv.startAnimation(flash);
						} else {
							turnCheck = 1;
							p1Tv.clearAnimation();
							p2Tv.startAnimation(flash);
						}
						combo = 0;
						final Toast t = Toast.makeText(this, "",
								Toast.LENGTH_SHORT);
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
				} else {
					openedImage = resIds[i];
					openedId = id;
				}
			}
		}

	}

}
