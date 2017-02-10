package com.mahmz.android.Classes;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import com.mahmz.android.DataClasses.Post;
import com.mahmz.android.MainFragments.PostsFragment;
import com.mahmz.android.Settings.Config;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by User on 2/8/2017.
 */
public class Tasks {
    // load posts as task
    public static class postsTask extends AsyncTask<String, Void, String> {
        private JSONObject values;
        private JSONObject res;
        private ProgressWheel wheel;
        private Button disableView;
        private ArrayList<Post> posts;
        private ScaleInAnimationAdapter animAdapter;

        public postsTask(JSONObject valuesp, ProgressWheel wheelp,
                         Button disableViewp, ArrayList<Post> postsp,
                         ScaleInAnimationAdapter animAdapterp) {
            values = valuesp;
            wheel = wheelp;
            disableView = disableViewp;
            posts = postsp;
            animAdapter = animAdapterp;
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
                    // do posts job
                    JSONArray postsJs = res.getJSONArray("Posts");
                    for (int p = 0; p < postsJs.length(); p++) {
                        Post post = new Post();
                        // get post code
                        // write your code, for example
                        // post = Tasks.getPost(postsJs[p]);
                        posts.add(post);
                        animAdapter.notifyItemInserted(posts.size());
                    }
                    animAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    // load posts more as task
    public static class postsMoreTask extends AsyncTask<String, Void, String> {
        private JSONObject values;
        private JSONObject res;
        private ProgressWheel wheel;
        private Button disableView;
        private ArrayList<Post> posts;
        private ScaleInAnimationAdapter animAdapter;

        public postsMoreTask(JSONObject valuesp, ArrayList<Post> postsp,
                             ScaleInAnimationAdapter animAdapterp) {
            values = valuesp;
            posts = postsp;
            animAdapter = animAdapterp;
        }

        @Override
        protected void onPreExecute() {
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
            //remove footer progress
            posts.remove(posts.size() - 1);
            animAdapter.notifyItemRemoved(posts.size());
            //check response
            if (res == null || res.length() == 0) {
                return;
            }
            try {
                if (res.getInt("MessageType") == 1) {
                    // do posts job
                    JSONArray postsJs = res.getJSONArray("Posts");
                    for (int p = 0; p < postsJs.length(); p++) {
                        Post post = new Post();
                        // get post code
                        // write your code, for example
                        // post = Tasks.getPost(postsJs[p]);
                        posts.add(post);
                        animAdapter.notifyItemInserted(posts.size());
                    }
                    animAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PostsFragment.loading = true;
            super.onPostExecute(s);
        }
    }
}
