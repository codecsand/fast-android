package com.mahmz.android.DataClasses;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mahmz.android.R;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by User on 12/18/2015.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {
    public ImageView postImage;
    public TextView categoryName;
    public TextView postTitle;
    public TextView postContent;
    public ImageView authorImage;
    public TextView postAuthor;
    public ToggleButton likepost;
    public ToggleButton repostpost;
    public ToggleButton wishpost;
    public TextView likecount;
    public TextView repostcount;
    public TextView wishcount;
    public ProgressWheel imageProgressWheel;
    public ImageView postPreviewImage;
    //layout postion
    private LinearLayout categoryNameLinear;
    public LinearLayout tags;
    private RelativeLayout categoryNameRelative;
    private int imageHight = 0;

    public PostViewHolder(View view) {
        super(view);
        // initialize elements
        postImage = (ImageView) view.findViewById(R.id.postImage);
        categoryName = (TextView) view.findViewById(R.id.categoryName);
        postTitle = (TextView) view.findViewById(R.id.postTitle);
        postContent = (TextView) view.findViewById(R.id.postContent);
        postAuthor = (TextView) view.findViewById(R.id.postAuthor);
        likecount = (TextView) view.findViewById(R.id.likecount);
        repostcount = (TextView) view.findViewById(R.id.repostcount);
        wishcount = (TextView) view.findViewById(R.id.wishcount);
        categoryNameLinear = (LinearLayout) view.findViewById(R.id.categoryNameLinear);
        tags = (LinearLayout) view.findViewById(R.id.tags);
        categoryNameRelative = (RelativeLayout) view.findViewById(R.id.categoryNameRelative);
        imageProgressWheel = (ProgressWheel) view.findViewById(R.id.image_progress_wheel);
        postPreviewImage = (ImageView) view.findViewById(R.id.postPreviewImage);
        //setup category name position
        ViewTreeObserver vto = postImage.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                // Remove after the first run so it doesn't fire forever
                //postImage.getViewTreeObserver().removeOnPreDrawListener(this);
                imageHight = postImage.getMeasuredHeight();
                RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                llp.setMargins(0, imageHight - (categoryName.getHeight() / 2), 0, 0); // llp.setMargins(left, top, right, bottom);
                categoryNameRelative.setLayoutParams(llp);
                return true;
            }
        });
    }

    public TextView TagView(Context context) {
        TextView tv = new TextView(context);
        tv.setBackgroundResource(R.color.tagBgColor);
        tv.setTextColor(context.getResources().getColor(R.color.tagTextColor));
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(12, 2, 0, 0);
        tv.setLayoutParams(llp);
        tv.setPadding(10, 10, 10, 10);
        return tv;
    }
}
