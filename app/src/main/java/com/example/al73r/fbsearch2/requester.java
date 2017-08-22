package com.example.al73r.fbsearch2;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
 * Created by al73r on 4/22/2017.
 */

public class requester extends AsyncTask<String,Integer,String> {
    private Context context;
    private View rootView;
    String value;
    String type ;
    String page ;

    public requester(String value,String type,String page,View rootView, Context context){
        this.rootView = rootView;
        this.context = context;
        this.value = value;
        this.page = page;
        this.type = type;
    }
    @Override
    protected String doInBackground(String... params) {
        StringBuffer chaine = new StringBuffer("");
        try{

            URL url = new URL("http://lowcost-env.ymirwi8zdf.us-west-2.elasticbeanstalk.com//fb.php?value="+ URLEncoder.encode(value,"UTF-8")+"&type="+URLEncoder.encode(type,"UTF-8")+"&page="+URLEncoder.encode(page,"UTF-8"));
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
        ArrayList<ListItem> searchResults = new ArrayList<>();
        final Button nextbtn = (Button) rootView.findViewById(R.id.nextBtn);
        final Button preBtn = (Button) rootView.findViewById(R.id.backBtnn);
        try{
            JSONObject jo = new JSONObject(result);
            JSONArray jarray = jo.getJSONArray("data");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject row = jarray.getJSONObject(i);
                ListItem tempItem = new ListItem(row.getString("name"),row.getString("id"));
                searchResults.add(tempItem);
            }
        if(jo.getString("hasnext").equals("true")){
            nextbtn.setEnabled(true);
        }
        else {
            nextbtn.setEnabled(false);
        }
        if(jo.getString("hasper").equals("true")){
            preBtn.setEnabled(true);
        }else {
            preBtn.setEnabled(false);
        }
        }catch (org.json.JSONException e){e.printStackTrace();}

        nextbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new requester(value, type, String.valueOf(Integer.parseInt(page)+1), rootView, context).execute();
                nextbtn.setEnabled(false);
                preBtn.setEnabled(false);

            }
        });
        preBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new requester(value, type, String.valueOf(Integer.parseInt(page)-1), rootView, context).execute();
                nextbtn.setEnabled(false);
                preBtn.setEnabled(false);
            }
        });
        ListView li = (ListView) rootView.findViewById(R.id.mylist);
        mylistadaptor adpt = new mylistadaptor(0,context,searchResults,type);
        li.setAdapter(adpt);


    }
}
