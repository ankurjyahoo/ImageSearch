package com.yahoo.learn.android.imagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.learn.android.imagesearch.activities.R;
import com.yahoo.learn.android.imagesearch.models.ImageResult;

import java.util.List;

/**
 * Created by ankurj on 1/31/2015.
 */
public class ImageResultsAdapter extends ArrayAdapter <ImageResult> {

//    ViewHolder viewHolder = new Vi

    public ImageResultsAdapter(Context context, List<ImageResult> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult image = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivResult);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        tvTitle.setText(Html.fromHtml(image.title));

        ivImage.setImageResource(0);
        Picasso.with(getContext()).load(image.tbUrl).into(ivImage);

        return convertView;
    }
}
