package com.example.todd.connector;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;

import com.example.todd.adapter.EmojisAdapter;
import com.example.todd.emojis.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by john on 11/11/15.
 * Simple AsyncTask to load emojis names and image url's into a hashmap
 */
public class EmojisConnector extends AsyncTask<String, Void, HashMap<String,String>> {

    // Set up our simple objects and variables
    private final String LOG = this.getClass().getName();
    final String EMO_URL = "https://api.github.com/emojis";
    URL emoURL;
    Activity activity;
    EmojisAdapter emojisAdapter;

    public EmojisConnector(Activity activity){
        this.activity = activity;
    }

    // doInBackground makes the https call, loads the hashmap and passes off to onPostExecute
    @Override
    protected HashMap<String,String> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream;

        try {
            emoURL = new URL(EMO_URL);
            urlConnection = (HttpURLConnection) emoURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            JsonReader reader;

            if(inputStream == null){
                return null;
            }

            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

            return getEmojis(reader);

        }catch (IOException e){
            Log.e(LOG, e.getMessage());
            return null;
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
    }

    // onPostExecute sets up the listview, creates the emojisAdapter and sets the adapter to the listview
    @Override
    protected void onPostExecute(HashMap<String,String> hashMap) {
        super.onPostExecute(hashMap);
        ListView listView = (ListView) activity.findViewById(R.id.list_view);
        emojisAdapter = new EmojisAdapter(activity,hashMap);
        listView.setAdapter(emojisAdapter);
    }

    // getEmojis takes the returned json and makes a hashmap from it
    public HashMap<String, String> getEmojis(JsonReader jsonReader) throws IOException {
        HashMap<String,String> emoMap = new HashMap<>();
        String name = "";
        String url = null;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            name = jsonReader.nextName();
            url = jsonReader.nextString();
            emoMap.put(name,url);
        }
        return emoMap;
    }
}
