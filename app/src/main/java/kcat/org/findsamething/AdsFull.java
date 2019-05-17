package kcat.org.findsamething;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AdsFull {

    private static InterstitialAd adFull;

    private static AdsFull instance = null;
    private static Context context;

    public AdsFull(Context context) {
        this.context = context;
    }

    public static AdsFull getInstance(Context context) {

        if (instance == null) {
            MobileAds.initialize(context, context.getResources().getString(R.string.admob_app_id));
            instance = new AdsFull(context);
            adFull = new InterstitialAd(context);
            setAds();
        }

        return instance;
    }


    private static void setAds() {


        AdRequest adRequest = new AdRequest.Builder().build(); //새 광고요청
        adFull = new InterstitialAd(context);
        adFull.setAdUnitId(context.getResources().getString(R.string.reward_ad_id));
        adFull.loadAd(adRequest); //요청한 광고를 load 합니다.

        adFull.setAdListener(new AdListener() { //전면 광고의 상태를 확인하는 리스너 등록

            @Override
            public void onAdClosed() { //전면 광고가 열린 뒤에 닫혔을 때
                AdRequest adRequest = new AdRequest.Builder().build(); //새 광고요청
                adFull.loadAd(adRequest); //요청한 광고를 load 합니다.
            }
        });
    }


    public void show() {


                adFull.show();



    }


}


