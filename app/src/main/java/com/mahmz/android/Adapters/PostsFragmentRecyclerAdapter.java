package com.mahmz.android.Adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mahmz.android.Activities.PostActivity;
import com.mahmz.android.DataClasses.FooterProgressViewHolder;
import com.mahmz.android.DataClasses.Post;
import com.mahmz.android.DataClasses.PostViewHolder;
import com.mahmz.android.MainActivity;
import com.mahmz.android.R;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 12/18/2015.
 */
public class PostsFragmentRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<Post> posts;
    private Context mContext;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public PostsFragmentRecyclerAdapter(Context context, ArrayList<Post> posts) {
        this.posts = posts;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, null);
            viewHolder = new PostViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_progress_item, null);
            viewHolder = new FooterProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PostViewHolder) {
            final PostViewHolder vh = (PostViewHolder) viewHolder;
            final Post post = posts.get(i);
            // empty views
            vh.categoryName.setText("");
            vh.postContent.setText("");
            vh.postAuthor.setText("");
            vh.likecount.setText("");
            vh.repostcount.setText("");
            vh.wishcount.setText("");
            vh.tags.removeAllViews();
            //setup collection views with data
            try {
                //Category Collection
                for (int c = 0; c < post.CategoryCollection.length(); c++) {
                    JSONObject category = post.CategoryCollection.getJSONObject(c);
                    String separator = ", ";
                    if (c == post.CategoryCollection.length() - 1) {
                        separator = "";
                    }
                    vh.categoryName.append(category.getString("CategoryName") + separator);
                }
                //Tag Collection
                for (int t = 0; t < post.TagCollection.length(); t++) {
                    JSONObject tag = post.TagCollection.getJSONObject(t);
                    TextView tv = vh.TagView(mContext);
                    tv.setText("#" + tag.getString("TagName"));
                    vh.tags.addView(tv);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // setup single views with data
            vh.postTitle.setText(post.PostTitle);
            vh.postContent.setText(post.PostText);
            vh.postAuthor.setText(post.PostAuthor);
            vh.categoryName.setText(post.PostCategory);
            vh.likecount.setText(String.valueOf(post.LikeCount));
            vh.repostcount.setText(String.valueOf(post.ShareCount));
            vh.wishcount.setText(String.valueOf(post.WishCount));
            // load demo posts images
            String uri = "@drawable/" + post.ImageName;  // where myresource.png is the file
            int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
            post.ImageRes = imageResource;
            Picasso.with(mContext).load(imageResource)
                    // load post image
                    // Picasso.with(mContext).load(post.ImageName)
                    .into(vh.postImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (!MainActivity.postsfadein.containsKey(post.id)) {
                                RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                vh.postImage.setLayoutParams(llp);
                                MainActivity.postsfadein.put(post.id, true);
                                vh.imageProgressWheel.setVisibility(View.GONE);
                                //vh.postPreviewImage.setVisibility(View.GONE);
                                final AnimatorSet anim = new AnimatorSet();
                                anim.playTogether(
                                        Glider.glide(Skill.QuintEaseIn, 500, ObjectAnimator.ofFloat(vh.postImage, "alpha", 0.7f, 1f))
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
            if (MainActivity.postsfadein.containsKey(post.id)) {
                Boolean value = MainActivity.postsfadein.get(post.id);
                if (value == true) {
                    RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    vh.postImage.setLayoutParams(llp);
                    vh.imageProgressWheel.setVisibility(View.GONE);
                    //vh.postPreviewImage.setVisibility(View.GONE);
                }
            }
            // post image onclick event
            vh.postImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(activity, PostActivity.class);
                    ImageView iv = (ImageView) view;
                    view.setDrawingCacheEnabled(true);
                    Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                    Bundle b = new Bundle();
                    String uri = "@drawable/" + post.ImageName;  // where myresource.png is the file
                    int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
                    intent.putExtra("res", imageResource);
                    intent.putExtra("title", post.PostTitle);
                    intent.putExtra("content", post.PostText);
                    activity.startActivity(intent);
                }
            });
        } else {
        }
    }

    @Override
    public int getItemCount() {
        if (posts != null) {
            return posts.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return posts.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }
}
