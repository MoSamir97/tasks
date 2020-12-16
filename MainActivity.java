package com.example.jasondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1) {
                    char curret = (char) data;
                    result += curret;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();;
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                String jsonString = s;

                //JSONObject jsonObject = new JSONObject(jsonString);
                //String todoInfo = jsonObject.getString(null);

                JSONArray todos = new JSONArray(jsonString);//jsonObject.getJSONArray("");
                Log.i("Todos", todos.toString());
                for (int i = 0; i < 5; i++) {
                    JSONObject jsonPart = todos.getJSONObject(i);
                    Log.i("Title", jsonPart.getString("title") + ", Completed: " + jsonPart.getString("completed"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        //task.execute("http://www.json-generator.com/api/json/get/cfXcjGDDhe?indent=2");
        task.execute("https://jsonplaceholder.typicode.com/todos");
    }
}