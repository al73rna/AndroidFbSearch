package com.example.al73r.fbsearch2;

/**
 * Created by al73r on 4/19/2017.
 */

public class ListItem {
    public String name;
    public String id;
    public String url;
    //public String isfav;
    public ListItem(String name , String id){
        this.name = name;
        this.id = id;
        this.url = "https://graph.facebook.com/"+this.id+"/picture?type=square";

    }
}
