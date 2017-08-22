package com.example.al73r.fbsearch2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by al73r on 4/24/2017.
 */




public class FavList {
    private static final FavList holder = new FavList();
    public static FavList getInstance() {return holder;}
    private HashMap<String, tag> map = new HashMap<String, tag>();

    public HashMap<String, tag> get() {return map;}
    public void set(HashMap<String, tag> data) {this.map = data;}


}