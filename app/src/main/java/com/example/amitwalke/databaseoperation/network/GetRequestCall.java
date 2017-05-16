package com.example.amitwalke.databaseoperation.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetRequestCall extends AsyncTask<String,String,String> {

    Context mContext;
    private static final String TAG = "GetRequestCall";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

   public GetRequestCall(Context mContext){
        this.mContext=mContext;
    }


    @Override
    protected String doInBackground(String... params)  {
        String link = "https://doxper.com/api/v2/profile_relation/";
        String finalData="",data="";
        URL url;
        try {
              url = new URL(link);
            // replace with your url
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
                   conn.connect();
            InputStream is=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));

            while((data=br.readLine())!=null){
                finalData+=data+"\n";
            }


        }catch (Exception e){
            Log.i(TAG, "doInBackground: Exception==>"+e.getMessage());
        }

        return finalData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i(TAG, "onPostExecute: "+s);
    }

}
