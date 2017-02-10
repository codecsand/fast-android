package com.mahmz.android.MainFragments;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mahmz.android.Adapters.PostsFragmentRecyclerAdapter;
import com.mahmz.android.Classes.Tasks;
import com.mahmz.android.DataClasses.Post;
import com.mahmz.android.R;
import com.mahmz.android.Settings.Config;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {
    private ProgressWheel wheel;
    private Button disableView;
    private ArrayList<Post> posts;
    private PostsFragmentRecyclerAdapter adapter;
    private ScaleInAnimationAdapter animAdapter;
    public static boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private int CountPerPage = 10;
    private int CurrentPage = 1;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_posts, container, false);
        //load progress
        wheel = (ProgressWheel) root.findViewById(R.id.progress_wheel);
        //load disable view
        disableView = (Button) root.findViewById(R.id.disableView);
        // setup posts
        JSONObject values = new JSONObject();
        try {
            values.put("param", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // initialize posts
        posts = new ArrayList<Post>();
        // Initialize recyclerview
        recyclerView = (RecyclerView) root.findViewById(R.id.posts_recycler_view);
        // set layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //StaggeredGridLayoutManager sgl = new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);
        // add space between items
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(20);
        recyclerView.addItemDecoration(itemDecoration);
        // init adapter
        adapter = new PostsFragmentRecyclerAdapter(getActivity(), posts);
        animAdapter = new ScaleInAnimationAdapter(adapter, 0.8f);
        animAdapter.setDuration(500);
        if (Config.testDemo) {
            //create demo posts
            // Add item to posts
            int c = 1;
            for (int p = 1; p <= 50; p++) {
                if (c == 21) {
                    c = 1;
                }
                ;
                Post post = new Post();
                post.PostTitle = "Post Title " + c;
                post.PostText = "This is a Post Text This is a Post Text This is a Post Text" + c;
                post.PostAuthor = "Author Name " + c;
                post.PostCategory = "Category " + c;
                post.ImageName = "img" + String.valueOf(c);
                posts.add(post);
                c++;
            }
        } else {
            // load posts
            new Tasks.postsTask(values, wheel, disableView, posts, animAdapter).execute(Config.postsUrl);
        }
        // set recyclerView adapter
        recyclerView.setAdapter(animAdapter);
        // load more
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    // check if load more
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            // add footer progress
                            posts.add(null);
                            animAdapter.notifyItemInserted(posts.size() - 1);
                            // set values
                            JSONObject values = new JSONObject();
                            try {
                                values.put("param", null);
                                CurrentPage++;
                                values.put("CurrentPage", CurrentPage);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // load more posts
                            if (Config.testDemo) {
                            } else {
                                new Tasks.postsMoreTask(values, posts, animAdapter).execute(Config.postsUrl);
                            }
                        }
                    }
                }
            }
        });
        return root;
    }

    // item space class
    private class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
}
