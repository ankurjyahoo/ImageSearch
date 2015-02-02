package com.yahoo.learn.android.imagesearch.models;

import com.yahoo.learn.android.imagesearch.activities.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ankurj on 1/31/2015.
 */
public class ImageResult {
    private static ArrayList<ImageResult>   mImages = new ArrayList<ImageResult>();

    public String  title;
    public String  tbUrl;
    public String  url;
    public int     width;
    public int     height;
    public int     tbWidth;
    public int     tbHeight;

    public ImageResult(JSONObject result) throws JSONException {
        title = result.getString("title");
        tbUrl = result.getString("tbUrl");
        url = result.getString("url");
        width = result.getInt("width");
        height = result.getInt("height");
        tbWidth = result.getInt("tbWidth");
        tbHeight = result.getInt("tbHeight");
    }


    @Override
    public String toString() {
        return title;
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray imageResults) throws JSONException {
        mImages.clear();
        for (int i=0, len=imageResults.length(); i<len; i++) {
            mImages.add(new ImageResult(imageResults.getJSONObject(i)));
        }

        return mImages;
    }

}
