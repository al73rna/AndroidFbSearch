package com.example.al73r.fbsearch2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.al73r.fbsearch2.R.id.imageView;

/**
 * Created by al73r on 4/19/2017.
 */

public class mylistadaptor extends ArrayAdapter<ListItem>{
    private final Context context;
    private final List list;


    private final String type;
    int isfav;
    public mylistadaptor(int isfav,Context context, ArrayList<ListItem> values, String type) {
        super(context, -1, values);
        this.context = context;
        this.list = values;
        this.type = type;
        this.isfav = isfav;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mylist, parent, false);

        final TextView textView1 = (TextView) rowView.findViewById(R.id.field);
        ImageView imageView1 = (ImageView) rowView.findViewById(R.id.icon1);
        final ImageButton imageButton1 = (ImageButton) rowView.findViewById(R.id.imageButton);
        final ImageButton imageButton2 = (ImageButton) rowView.findViewById(R.id.imageButton2);
        textView1.setText(((ListItem)list.get(position)).name);

        if(!FavList.getInstance().get().containsKey( ((ListItem)list.get(position)).id )){
            imageButton1.setImageResource(R.drawable.ic_fav_off);
        }else {
            imageButton1.setImageResource(R.drawable.ic_fav_on);
        }

        try {
            Picasso.with(context).load(((ListItem)list.get(position)).url).into(imageView1);
        }
        catch (Exception e){

        }
        final ArrayAdapter<ListItem> t = this;
        imageButton1.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    String tempid = ((ListItem)list.get(position)).id;
                    if(!FavList.getInstance().get().containsKey(tempid)){
                        imageButton1.setImageResource(R.drawable.ic_fav_on);
                        tag tempTag = new tag();
                        tempTag.name = ((ListItem)list.get(position)).name ;
                        tempTag.type = type;
                        FavList.getInstance().get().put(tempid,tempTag);
                    }else {
                        imageButton1.setImageResource(R.drawable.ic_fav_off);
                        FavList.getInstance().get().remove(tempid);
                        if(isfav==1) {
                            list.remove(position);
                            t.notifyDataSetChanged();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    //int st = position;
                    Intent intent = new Intent(context, ItemActivity.class);
                    intent.putExtra("url", ((ListItem)list.get(position)).url);
                    intent.putExtra("id", ((ListItem)list.get(position)).id);
                    intent.putExtra("name", ((ListItem)list.get(position)).name);
                    intent.putExtra("type", type);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // change the icon for Windows and iPhone

//        String s = values[position];
//        if (s.startsWith("iPhone")) {
//            imageView.setImageResource(R.drawable.no);
//        } else {
//            imageView.setImageResource(R.drawable.ok);
//        }

        return rowView;
    }


}
