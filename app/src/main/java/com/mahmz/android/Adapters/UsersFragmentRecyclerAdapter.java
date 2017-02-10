package com.mahmz.android.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.mahmz.android.DataClasses.User;
import com.mahmz.android.DataClasses.UserViewHolder;
import com.mahmz.android.DataClasses.FooterProgressViewHolder;
import com.mahmz.android.MainActivity;
import com.mahmz.android.R;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 12/18/2015.
 */
public class UsersFragmentRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<User> users;
    private Context mContext;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public UsersFragmentRecyclerAdapter(Context context, ArrayList<User> users) {
        this.users = users;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, null);
            viewHolder = new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_progress_item, null);
            viewHolder = new FooterProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof UserViewHolder) {
            final UserViewHolder vh = (UserViewHolder) viewHolder;
            final int position = viewHolder.getAdapterPosition();
            final User user = users.get(position);
            // setup single views with data
            vh.userName.setText(user.UserName);
            vh.likesCount.setText(String.valueOf(user.LikeCounts) + " Likes");
            if (user.IsFavorit == 1) vh.isFavorit.setVisibility(View.VISIBLE);
            if (user.IsLiked == 1) vh.isLiked.setChecked(true);
            if (user.IsMuted == 1) vh.isMuted.setChecked(true);
            // load demo users images
            String uri = "@drawable/" + user.ImageName;  // where myresource.png is the file
            int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
            user.ImageRes = imageResource;
            Picasso.with(mContext).load(imageResource)
                    //.load(user.ImageName).into(vh.userImage, new com.squareup.picasso.Callback() {
                    .into(vh.userImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (!MainActivity.users.containsKey(user.id)) {
                                RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                vh.userImage.setLayoutParams(llp);
                                MainActivity.users.put(user.id, true);
                                vh.imageProgressWheel.setVisibility(View.GONE);
                                final AnimatorSet anim = new AnimatorSet();
                                anim.playTogether(
                                        Glider.glide(Skill.QuintEaseIn, 500, ObjectAnimator.ofFloat(vh.userImage, "alpha", 0.7f, 1f))
                                );
                                anim.setInterpolator(new LinearInterpolator());
                                anim.setDuration(500);
                                anim.start();
                            }
                        }

                        @Override
                        public void onError() {
                            vh.imageProgressWheel.setVisibility(View.GONE);
                        }
                    });
            // check if image already loaded
            if (MainActivity.users.containsKey(user.id)) {
                Boolean value = MainActivity.users.get(user.id);
                if (value == true) {
                    RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    vh.userImage.setLayoutParams(llp);
                    vh.imageProgressWheel.setVisibility(View.GONE);
                }
            }
        } else {
        }
    }

    @Override
    public int getItemCount() {
        if (users != null) {
            return users.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return users.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }
}
