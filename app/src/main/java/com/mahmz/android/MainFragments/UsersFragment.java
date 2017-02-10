package com.mahmz.android.MainFragments;
import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mahmz.android.Adapters.UsersFragmentRecyclerAdapter;
import com.mahmz.android.Classes.HttpPost;
import com.mahmz.android.DataClasses.User;
import com.mahmz.android.R;
import com.mahmz.android.Settings.Config;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
    private ProgressWheel wheel;
    private Button disableView;
    private ArrayList<User> users;
    private UsersFragmentRecyclerAdapter adapter;
    private ScaleInAnimationAdapter animAdapter;
    public static boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RecyclerView recyclerView;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_users, container, false);
        //load progress
        wheel = (ProgressWheel) root.findViewById(R.id.progress_wheel);
        //load disable view
        disableView = (Button) root.findViewById(R.id.disableView);
        // setup posts
        JSONObject values = new JSONObject();
        try {
            values.put("CategoryGuid", null);
            values.put("Keyword", null);
            values.put("UserGuid", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // initialize users
        users = new ArrayList<User>();
        // Initialize recyclerview
        recyclerView = (RecyclerView) root.findViewById(R.id.users_recycler_view);
        // set layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //gridLayoutManager = new GridLayoutManager(getActivity(),2);
        //staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(layoutManager);
        // add space between items
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(40);
        recyclerView.addItemDecoration(itemDecoration);
        // init adapter
        adapter = new UsersFragmentRecyclerAdapter(getActivity(), users);
        animAdapter = new ScaleInAnimationAdapter(adapter, 0.8f);
        animAdapter.setDuration(500);
        if (Config.testDemo) {
            // Add users
            int c = 1;
            for (int p = 1; p <= 50; p++) {
                if (c == 21) {
                    c = 1;
                }
                ;
                User user = new User();
                user.UserName = "Username " + c;
                user.ImageName = "user" + String.valueOf(c);
                users.add(user);
                c++;
            }
        } else {
            // load users
            new usersTask(values).execute(Config.usersUrl);
        }
        // set recyclerView adapter
        recyclerView.setAdapter(animAdapter);
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

    // load users as task
    private class usersTask extends AsyncTask<String, Void, String> {
        private JSONObject values;
        private JSONObject res;

        public usersTask(JSONObject valuesp) {
            values = valuesp;
        }

        @Override
        protected void onPreExecute() {
            wheel.setVisibility(View.VISIBLE);
            disableView.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            res = HttpPost.getJsonByPost(params[0], values);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            wheel.setVisibility(View.GONE);
            disableView.setVisibility(View.GONE);
            if (res == null || res.length() == 0) {
                return;
            }
            try {
                if (res.getInt("MessageType") == 1) {
                    // do users job
                    JSONArray usersJs = res.getJSONArray("Users");
                    for (int b = 0; b < usersJs.length(); b++) {
                        User user = new User();
                        // write your code
                        // for example: user = Tasks.getUser(usersJs[b]);
                        users.add(user);
                        animAdapter.notifyItemInserted(users.size());
                    }
                    animAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}


