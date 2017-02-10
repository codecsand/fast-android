package com.mahmz.android.Activities;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mahmz.android.Adapters.IntroPagerAdapter;
import com.mahmz.android.Classes.Func;
import com.mahmz.android.R;
import com.mahmz.android.Settings.Config;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.yqritc.scalablevideoview.ScalableType;
import com.yqritc.scalablevideoview.ScalableVideoView;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

public class IntroActivity extends AppCompatActivity {
    private RelativeLayout intro;
    private ViewPager viewPager;
    private IntroPagerAdapter mAdapter;
    private ScalableVideoView mVideoView;
    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout pager_indicator;
    private int pagetext = 0;
    private Handler handler;
    private Runnable runnable;
    private Button loginbt;
    private Button registerbt;
    private static IntroActivity IA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        IA = this;
        intro = (RelativeLayout) findViewById(R.id.intro);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        // animation intro
        AnimatorSet anim = new AnimatorSet();
        anim.playTogether(
                Glider.glide(Skill.QuintEaseIn, 200, ObjectAnimator.ofFloat(intro, "alpha", 0f, 1f))
        );
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(400);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    Boolean issplash = extras.getBoolean("fromsplash");
                    if (issplash) {
                        SplashActivity.splashA.finish();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        // intro pager
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        setUiPageViewController();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(Func.getDrawableImage(IntroActivity.this, R.drawable.nonselecteditem_dot));
                }
                dots[position].setImageDrawable(Func.getDrawableImage(IntroActivity.this, R.drawable.selecteditem_dot));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        // buttons login - register
        loginbt = (Button) findViewById(R.id.login);
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        registerbt = (Button) findViewById(R.id.register);
        registerbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(pagetext);
                pagetext++;
                /** Do something **/
                handler.postDelayed(runnable, Config.introPageingTime);
                if (pagetext == 4) handler.removeCallbacks(runnable);
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, 0);
        //video intro
        if (Config.enableVideoIntro) {
            mVideoView = (ScalableVideoView) findViewById(R.id.videoview);
            try {
                mVideoView.setRawData(R.raw.bmw);
                mVideoView.setVolume(0, 0);
                mVideoView.setLooping(true);
                mVideoView.setScalableType(ScalableType.CENTER_TOP_CROP);
                mVideoView.invalidate();
                mVideoView.prepare(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mVideoView.start();
                    }
                });
            } catch (IOException ioe) {
            }
        }
    }

    private void setUiPageViewController() {
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(Func.getDrawableImage(IntroActivity.this, R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(9, 0, 9, 0);
            pager_indicator.addView(dots[i], params);
        }
        dots[0].setImageDrawable(Func.getDrawableImage(IntroActivity.this, R.drawable.selecteditem_dot));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.invalidate();
            mVideoView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    moveTaskToBack(true);
                    return true;
                // finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
