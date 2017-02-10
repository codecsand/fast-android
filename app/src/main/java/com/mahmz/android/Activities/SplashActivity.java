package com.mahmz.android.Activities;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mahmz.android.R;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.mahmz.android.Settings.Config;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class SplashActivity extends AppCompatActivity {
    public static SplashActivity splashA;
    private RelativeLayout waves;
    private RelativeLayout main;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashA = this;
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        main = (RelativeLayout) findViewById(R.id.main);
        waves = (RelativeLayout) findViewById(R.id.waves);
        logo = (ImageView) findViewById(R.id.splash);
        startAnimation();
        startAnimation2();
        startAnimation3();
    }

    private void startAnimation() {
        AnimatorSet anim = new AnimatorSet();
        anim.playTogether(
                Glider.glide(Skill.QuintEaseIn, Config.splashAnimatinTime, ObjectAnimator.ofFloat(main, "alpha", 0f, 1f))
        );
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(400);
        anim.start();
    }

    private void startAnimation2() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(waves, "scaleX", 5f);
        AnimatorSet animSetXY_ = new AnimatorSet();
        animSetXY_.playTogether(anim1);
        animSetXY_.setDuration(0);
        animSetXY_.start();
        AnimatorSet anim2 = new AnimatorSet();
        anim2.playTogether(
                Glider.glide(Skill.CircEaseIn, Config.splashAnimatinTime, ObjectAnimator.ofFloat(waves, "translationX", waves.getX(), -5320))//,
                //    Glider.glide(Skill.CircEaseIn, 200, ObjectAnimator.ofFloat(waves, "alpha",0f, 1f))
        );
        anim2.setInterpolator(new LinearInterpolator());
        anim2.setDuration(1000);
        anim2.start();
    }

    private void startAnimation3() {
        AnimatorSet anim3 = new AnimatorSet();
        anim3.playTogether(
                Glider.glide(Skill.SineEaseIn, Config.splashAnimatinTime, ObjectAnimator.ofFloat(logo, "translationX", logo.getX(), -20)),
                Glider.glide(Skill.CircEaseIn, Config.splashAnimatinTime, ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f))
        );
        anim3.setInterpolator(new LinearInterpolator());
        anim3.setDuration(1400);
        anim3.addListener(new Animator.AnimatorListener() {
                              @Override
                              public void onAnimationStart(Animator animation) {
                              }

                              @Override
                              public void onAnimationEnd(Animator animation) {
                                  final Intent i = new Intent(getApplicationContext(), IntroActivity.class);
                                  final AnimatorSet set_2 = new AnimatorSet();
                                  set_2.addListener(new Animator.AnimatorListener() {
                                      @Override
                                      public void onAnimationStart(Animator animation) {
                                      }

                                      @Override
                                      public void onAnimationEnd(Animator animation) {
                                          i.putExtra("fromsplash", true);
                                          startActivity(i);
                                          // finish();
                                      }

                                      @Override
                                      public void onAnimationCancel(Animator animation) {
                                      }

                                      @Override
                                      public void onAnimationRepeat(Animator animation) {
                                      }
                                  });
                                  set_2.playTogether(
                                          Glider.glide(Skill.QuintEaseIn, Config.splashAnimatinTime, ObjectAnimator.ofFloat(main, "alpha", 1f, 1f))
                                  );
                                  set_2.setInterpolator(new LinearInterpolator());
                                  set_2.setDuration(300);
                                  new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          set_2.start();
                                      }
                                  }, Config.splashScreenEndTime);
                              }

                              @Override
                              public void onAnimationCancel(Animator animation) {
                              }

                              @Override
                              public void onAnimationRepeat(Animator animation) {
                              }
                          }
        );
        anim3.start();
    }
}
