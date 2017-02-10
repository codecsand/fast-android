package com.mahmz.android.DataClasses;
import com.mahmz.android.Classes.Func;

import org.json.JSONArray;

/**
 * Created by User on 12/18/2015.
 */
public class Post {
    public String id;
    public String PostTitle = "";
    public String PostText = "";
    public String PostAuthor = "";
    public String PostCategory = "";
    public String ImageName = "";
    public int ImageRes = 0;
    public int LikeCount = 0;
    public int ShareCount = 0;
    public int WishCount = 0;
    public JSONArray CommentCollection;
    public JSONArray TagCollection;
    public JSONArray CategoryCollection;

    public Post() {
        id = Func.randomKey();
        CommentCollection = new JSONArray();
        TagCollection = new JSONArray();
        CategoryCollection = new JSONArray();
    }
}

