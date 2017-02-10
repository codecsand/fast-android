package com.mahmz.android.Activities;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.mahmz.android.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.picasso.Picasso;

public class PostActivity extends Activity {
    private LinearLayout pop;
    private LinearLayout circle;
    private LinearLayout circle2;
    private CardView cv;
    private TextView ptitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setupViews();
        setupAnimation();
    }

    @Override
    public void onBackPressed() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(pop, "translationY", -100f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(pop, "alpha", 1f, 0f);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(anim, anim2);
        animSetXY.start();
        ObjectAnimator ac = ObjectAnimator.ofFloat(circle, "scaleX", 0f);
        ObjectAnimator ac2 = ObjectAnimator.ofFloat(circle, "scaleY", 0f);
        ObjectAnimator ac3 = ObjectAnimator.ofFloat(circle, "alpha", 1f, 0f);
        AnimatorSet acanim = new AnimatorSet();
        acanim.playTogether(ac3, ac, ac2);
        acanim.setDuration(300);
        acanim.start();
        ObjectAnimator ac_ = ObjectAnimator.ofFloat(circle2, "scaleX", 0f);
        ObjectAnimator ac2_ = ObjectAnimator.ofFloat(circle2, "scaleY", 0f);
        ObjectAnimator ac3_ = ObjectAnimator.ofFloat(circle2, "alpha", 1f, 0f);
        AnimatorSet acanim_ = new AnimatorSet();
        acanim_.playTogether(ac3_, ac_, ac2_);
        acanim_.setDuration(300);
        acanim_.start();
        animSetXY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupViews() {
        Bundle b = getIntent().getExtras();
        String title = b.getString("title");
        String content = b.getString("content");
        int res = b.getInt("res");
        ptitle = (TextView) findViewById(R.id.ptitle);
        TextView tvc = (TextView) findViewById(R.id.pcontent);
        ImageView iv = (ImageView) findViewById(R.id.pimage);
        pop = (LinearLayout) findViewById(R.id.pop);
        cv = (CardView) findViewById(R.id.row_my_card);
        circle = (LinearLayout) findViewById(R.id.circle);
        circle2 = (LinearLayout) findViewById(R.id.circle2);
        ptitle.setText(title);
        tvc.setText(content);
        Glide.with(this).load(res).into(iv);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
    }

    private void setupAnimation() {
        ObjectAnimator ac = ObjectAnimator.ofFloat(circle, "scaleX", 25.5f);
        ObjectAnimator ac2 = ObjectAnimator.ofFloat(circle, "scaleY", 40.5f);
        ObjectAnimator ac3 = ObjectAnimator.ofFloat(circle, "alpha", 0f, 1f);
        ObjectAnimator ac4 = ObjectAnimator.ofFloat(circle, "translationY", 10f);
        AnimatorSet acanim = new AnimatorSet();
        acanim.playTogether(ac, ac2, ac3, ac4);
        acanim.setDuration(400);
        acanim.start();
        ObjectAnimator ac_ = ObjectAnimator.ofFloat(circle2, "scaleX", 25.5f);
        ObjectAnimator ac2_ = ObjectAnimator.ofFloat(circle2, "scaleY", 25.5f);
        ObjectAnimator ac3_ = ObjectAnimator.ofFloat(circle2, "alpha", 0f, 1f);
        ObjectAnimator ac4_ = ObjectAnimator.ofFloat(circle2, "translationY", 10f);
        AnimatorSet acanim_ = new AnimatorSet();
        acanim_.playTogether(ac_, ac2_, ac3_, ac4_);
        acanim_.setDuration(400);
        acanim_.start();
        ObjectAnimator anim = ObjectAnimator.ofFloat(pop, "translationY", -80f, 20f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(pop, "alpha", 0f, 1f);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(anim, anim2);
        animSetXY.setDuration(550);
        animSetXY.start();
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(cv, "translationX", 100f, -10f);
        ObjectAnimator anim2_ = ObjectAnimator.ofFloat(cv, "alpha", 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(anim3, anim2_);
        animSet.setDuration(800);
        animSet.start();
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(ptitle, "translationX", -200f, 10f);
        AnimatorSet animSet2 = new AnimatorSet();
        animSet.playTogether(anim4);
        animSet.setDuration(450);
        animSet.start();
    }
}
