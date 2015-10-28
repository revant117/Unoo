package com.example.rev.unoo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }
       String items;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


//        String[] listitem = {
//                "today",
//                "tomoro",
//                "haha",
//                "mofo"
//        };
//        List<String> listview = new ArrayList<String>(Arrays.asList(listitem));


        ListView listView = (ListView) rootView.findViewById(
                R.id.fragmentlist  );
        final ArrayList<File> songs = findSongs(Environment.getExternalStorageDirectory());
        String[] items = new String[songs.size()];
        for(int i=0;i<songs.size();i++){
            items[i]=songs.get(i).getName().toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list,
                R.id.textist,
                items
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               startActivity(new Intent(getActivity(),Player.class).putExtra("pos",i).putExtra("songs",songs));
            }
        });



        return rootView;
    }



public ArrayList<File> findSongs (File root){
ArrayList<File> al = new ArrayList<File>();
    File[] files = root.listFiles();
    for(File singleFile : files) {
        if (singleFile.isDirectory() && !singleFile.isHidden()) {
            al.addAll(findSongs(singleFile));
        }
        else
        {
            if(singleFile.getName().endsWith(".mp3")){

                al.add(singleFile);
            }
        }
        }
    return al;
    }

}
//    public class Fetch extends AsyncTask<Void ,Void ,Void >{
//        private final String LOG_TAG = Fetch.class.getSimpleName();
//        @Override
//
//        protected Void doInBackground(Void... params){
//            // These two need to be declared outside the try/catch
//// so that they can be closed in the finally block.
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//// Will contain the raw JSON response as a string.
//            String forecastJsonStr = null;
//
//            try {
//                // Construct the URL for the OpenWeatherMap query
//                // Possible parameters are avaiable at OWM's forecast API page, at
//                // http://openweathermap.org/API#forecast
////                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
//                URL url = new URL("http://stats.grok.se/json/en/201509/cortana");
//
//                // Create the request to OpenWeatherMap, and open the connection
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    forecastJsonStr = null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                    // But it does make debugging a *lot* easier if you print out the completed
//                    // buffer for debugging.
//                    buffer.append(line + "\n");
//                }
//
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    forecastJsonStr = null;
//                }
//                forecastJsonStr = buffer.toString();
//            } catch (IOException e) {
//                Log.e(LOG_TAG, "Error ", e);
//                // If the code didn't successfully get the weather data, there's no point in attemping
//                // to parse it.
//                forecastJsonStr = null;
//            } finally{
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e(LOG_TAG, "Error closing stream", e);
//                    }
//                }
//            }
//            return null;
//        }
//    }
//}
