package com.example.al73r.fbsearch2;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by al73r on 4/23/2017.
 */

public class ItemLoader extends AsyncTask<String,Integer,String> {
    private Context context;
    private View rootView;
    String id;
    String name;
    String url;
    int tab;

    public ItemLoader(int tab, String id,String name,String url,View rootView, Context context){
        this.rootView = rootView;
        this.context = context;
        this.id = id;
        this.name = name;
        this.url = url;
        this.tab = tab;
    }
    @Override
    protected String doInBackground(String... params) {
        StringBuffer chaine = new StringBuffer("");
        try{

            URL url = new URL("http://lowcost-env.ymirwi8zdf.us-west-2.elasticbeanstalk.com//aap.php?id="+ URLEncoder.encode(id,"UTF-8"));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }
        }
        catch (IOException e) {
            // Writing exception to log
            e.printStackTrace();
        }





        return chaine.toString();
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        ArrayList<Album> AlbumList = new ArrayList<>();
        ArrayList<Post> PostList = new ArrayList<>();
        //JSONObject res = null;
//        try{
//            JSONObject res  = new JSONObject(result);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        ListView li = (ListView) rootView.findViewById(R.id.postlist);
        ExpandableListView el = (ExpandableListView) rootView.findViewById(R.id.albumlist);
        if(tab==1){
            //albums
            try{
                TextView errortext = (TextView) rootView.findViewById(R.id.noFound);
                errortext.setText("");
                JSONObject res  = new JSONObject(result);
                JSONArray jarray = res.getJSONObject("albums").getJSONArray("data");
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject row = jarray.getJSONObject(i);
                    String Jname = row.getString("name");
                    ArrayList<String> tempUrls = new ArrayList<>();

                    //get photos
                    JSONArray jkarray =row.getJSONObject("photos").getJSONArray("data");
                    for(int k = 0; k < jkarray.length() ; k++){
                        tempUrls.add(jkarray.getJSONObject(k).getJSONArray("images").getJSONObject(1).getString("source"));
                    }
                    Album tempAlbum = new Album(Jname,tempUrls);

                    AlbumList.add(tempAlbum);
                }
            }catch (org.json.JSONException e){
                TextView errortext = (TextView) rootView.findViewById(R.id.noFound);
                errortext.setText("No Result Found");
                e.printStackTrace();}
            li.setVisibility(View.GONE);
            el.setVisibility(View.VISIBLE);
            AlbumAdaptor aa = new AlbumAdaptor(context,AlbumList);
            el.setAdapter(aa);
            el.requestFocus();
        }
        if(tab==2){
            //posts
            try{
                TextView errortext = (TextView) rootView.findViewById(R.id.noFound);
                errortext.setText("");
                JSONObject res  = new JSONObject(result);
                JSONArray jarray2 = res.getJSONObject("posts").getJSONArray("data");
                for (int ii = 0; ii < jarray2.length(); ii++) {
                    JSONObject row2 = jarray2.getJSONObject(ii);
                    Post tempPost = new Post(name,row2.getString("created_time"),row2.getString("message"),this.url);
                    PostList.add(tempPost);
                }

            }
            catch (Exception e){
                TextView errortext = (TextView) rootView.findViewById(R.id.noFound);
                errortext.setText("No Result Found");
                e.printStackTrace();}

            el.setVisibility(View.GONE);
            li.setVisibility(View.VISIBLE);
            postAdaptor adpt = new postAdaptor(context,PostList);
            li.setAdapter(adpt);
            li.requestFocus();
        }


    }
}