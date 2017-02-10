package com.mahmz.android.Adapters;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mahmz.android.IntroFragments.FragmentPage1;
import com.mahmz.android.IntroFragments.FragmentPage2;
import com.mahmz.android.IntroFragments.FragmentPage3;
import com.mahmz.android.IntroFragments.FragmentPage4;

public class IntroPagerAdapter extends FragmentPagerAdapter {
    public IntroPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new FragmentPage1();
            case 1:
                // Games fragment activity
                return new FragmentPage2();
            case 2:
                // Movies fragment activity
                return new FragmentPage3();
            case 3:
                // Movies fragment activity
                return new FragmentPage4();
        }
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
}
