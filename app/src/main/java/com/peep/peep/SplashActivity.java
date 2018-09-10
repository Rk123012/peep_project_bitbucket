package com.peep.peep;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.peep.peep.Utility.KeyString;
import com.peep.peep.Utility.SharedPreferenceManager;
import com.peep.peep.home.Home;
import com.peep.peep.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private String value;

    @BindView(R.id.lottie_animation_view)
    LottieAnimationView mLottieAnimationView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferenceManager manager = new SharedPreferenceManager(this, KeyString.PREFERENCE_NAME);
        value = manager.getValue(KeyString.KEY_TOKEN, "Null");
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        int secondsDelayed = 1;
        changeAnim();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (value.contains("Null")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, Home.class));
                }
                finish();
            }
        }, secondsDelayed * 5000);

    }

    private void changeAnim() {
        new Handler().post(() -> {
            LottieComposition.Factory.fromAssetFileName(this, "peep.json", composition -> {
                mLottieAnimationView.setComposition(composition);
                mLottieAnimationView.playAnimation();
            });
        });
    }

    @Override
    protected void onDestroy() {
        mLottieAnimationView.cancelAnimation();
        mLottieAnimationView = null;
        super.onDestroy();
    }

}
