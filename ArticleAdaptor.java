package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by colli on 8/24/2017.
 */

public class ArticleAdaptor  extends ArrayAdapter<Article>{

    public ArticleAdaptor(Context context, List<Article> articles) {
        super(context, 0, articles);}

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Article currentArticle = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.listTitle);
        titleView.setText(currentArticle.getmTitle());

        TextView sectionView = (TextView) listItemView.findViewById(R.id.listSection);
        sectionView.setText(currentArticle.getmSection());

        TextView authorView = (TextView) listItemView.findViewById(R.id.listAuthor);
        authorView.setText(currentArticle.getmAuthor());

        TextView dateView = (TextView) listItemView.findViewById(R.id.listdate);
        dateView.setText(currentArticle.getmDate());

        return listItemView;
    }

}
