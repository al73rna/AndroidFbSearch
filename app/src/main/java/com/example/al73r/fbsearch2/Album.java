package com.example.al73r.fbsearch2;

import java.util.ArrayList;

/**
 * Created by al73r on 4/23/2017.
 */

public class Album {
    public String name;
    public ArrayList<String> urls;
    Album(String name,ArrayList<String> urls){
        this.name = name;
        this.urls = urls;
    }
}
