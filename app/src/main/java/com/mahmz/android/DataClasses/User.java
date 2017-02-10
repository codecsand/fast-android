package com.mahmz.android.DataClasses;
import com.mahmz.android.Classes.Func;

import java.util.Random;

/**
 * Created by User on 12/18/2015.
 */
public class User {
    public String id;
    public String UserName = "";
    public String ImageName = "";
    public int ImageRes = 0;
    public int LikeCounts = 0;
    public int IsFavorit = 0;
    public int IsLiked = 0;
    public int IsMuted = 0;

    public User() {
        id = Func.randomKey();
        Random rn = new Random();
        LikeCounts = rn.nextInt(50 - 10 + 1) + 10;
        IsFavorit = (int) Math.round(rn.nextDouble() * .04);
        IsLiked = 1;
        IsMuted = (int) Math.round(rn.nextDouble() * .06);
    }
}

