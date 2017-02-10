package com.mahmz.android.IntroFragments;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mahmz.android.R;
import com.mahmz.android.Settings.Config;

public class FragmentPage3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_desc3, container, false);
        if (!Config.enableVideoIntro) {
            rootView.setBackgroundColor(Color.parseColor("#00c100"));
        }
        if (Config.enableIntroBackgrounds) {
            ImageView iv = (ImageView) rootView.findViewById(R.id.bg);
            iv.setVisibility(View.VISIBLE);
        }
        return rootView;
    }
}
