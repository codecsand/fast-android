package com.mahmz.android.DataClasses;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
public class UserViewHolder extends RecyclerView.ViewHolder {
    public ImageView userImage;
    public TextView userName;
    public TextView likesCount;
    public ToggleButton isMuted;
    public ToggleButton isLiked;
    public ImageView isFavorit;
    public ProgressWheel imageProgressWheel;
    //layout postion
    private LinearLayout categoryNameLinear;
    public LinearLayout tags;
    private RelativeLayout categoryNameRelative;
    private int imageHight = 0;

    public UserViewHolder(View view) {
        super(view);
        // initialize elements
        userImage = (ImageView) view.findViewById(R.id.userImage);
        userName = (TextView) view.findViewById(R.id.userName);
        likesCount = (TextView) view.findViewById(R.id.likesCount);
        isMuted = (ToggleButton) view.findViewById(R.id.ismuted);
        isLiked = (ToggleButton) view.findViewById(R.id.isliked);
        isFavorit = (ImageView) view.findViewById(R.id.isfavorit);
        imageProgressWheel = (ProgressWheel) view.findViewById(R.id.image_progress_wheel);
        // empty views
        userName.setText("");
        likesCount.setText("");
        isFavorit.setVisibility(View.INVISIBLE);
    }
}
