package com.mahmz.android.DataClasses;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mahmz.android.R;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by User on 12/18/2015.
 */
public class FooterProgressViewHolder extends RecyclerView.ViewHolder {
    private ProgressWheel wheel;

    public FooterProgressViewHolder(View view) {
        super(view);
        // initialize elements
        wheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);
    }
}
