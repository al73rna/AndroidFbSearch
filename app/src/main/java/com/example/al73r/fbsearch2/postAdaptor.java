package com.example.al73r.fbsearch2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by al73r on 4/24/2017.
 */


public class postAdaptor extends ArrayAdapter<Post> {
    private final Context context;
    private final List list;

    public postAdaptor(Context context, ArrayList<Post> values) {
        super(context, -1, values);
        this.context = context;
        this.list = values;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mylist2, parent, false);
        String tempDate = ((Post)list.get(position)).time ;
        TextView textname = (TextView) rowView.findViewById(R.id.nametext);
        TextView textdate = (TextView) rowView.findViewById(R.id.timetext);
        TextView textmsg = (TextView) rowView.findViewById(R.id.msgtext);

        ImageView imageprofile = (ImageView) rowView.findViewById(R.id.profpic);

        textmsg.setText(((Post)list.get(position)).message);
        textname.setText(((Post)list.get(position)).name);
        textdate.setText(tempDate.substring(0,10)+"  "+tempDate.substring(12,19));
        try {
            Picasso.with(context).load(((Post)list.get(position)).pic).into(imageprofile);
        }
        catch (Exception e){

        }
        return rowView;
    }


}